package main

import (
	"bytes"
	"errors"
	"fmt"
	"golang.org/x/net/html"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"os/exec"
	"path"
	"runtime"
	"strings"
)

const (
	DOC             = "https://www.webscraper.io/test-sites/e-commerce/allinone"
	KEYWORD         = "Lenovo"
	HighlightColor  = "#FFFFFF"
	BackgroundColor = "#FFFF00"
	FILENAME        = "newhtml.html"
)

func getHTMLdoc(doc string) string {
	resp, err := http.Get(doc)
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()

	bodyBytes, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatal(err)
	}

	return string(bodyBytes)
}

func highlightKeyword(bodyNode *html.Node) (highlightedBodyText string) {

	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.TextNode {
			if n.Parent.Data != "script" {
				if strings.Contains(n.Data, KEYWORD) {
					oldText := n.Data
					text := strings.Split(oldText, KEYWORD)
					leftText := text[0]

					rightText := text[1]

					leftTextNode := &html.Node{
						Type: html.TextNode,
						Data: leftText,
					}
					spanTextNode := &html.Node{
						Type: html.ElementNode,
						Data: "span",
						Attr: []html.Attribute{{Key: "style", Val: "background-color: " + BackgroundColor + "; color: " + HighlightColor}},
					}
					textNode := &html.Node{
						Type: html.TextNode,
						Data: KEYWORD,
					}
					rightTextNode := &html.Node{
						Type: html.TextNode,
						Data: rightText,
					}
					n.Parent.InsertBefore(leftTextNode, n)
					n.Parent.InsertBefore(spanTextNode, n)
					spanTextNode.AppendChild(textNode)
					n.Parent.InsertBefore(rightTextNode, n)
					for c := n.FirstChild; c != nil; c = c.NextSibling {
						c.Parent = leftTextNode
						n.RemoveChild(c)
					}
					n.Parent.RemoveChild(n)
				}
			}
		}
		for c := n.FirstChild; c != nil; c = c.NextSibling {
			f(c)
		}
	}
	f(bodyNode)
	return
}

func getBodyNode(startNode *html.Node) (*html.Node, error) {
	var b *html.Node
	var moveDownTree func(*html.Node)
	moveDownTree = func(node *html.Node) {
		if node.Type == html.ElementNode && node.Data == "body" {
			b = node
		}
		for child := node.FirstChild; child != nil; child = child.NextSibling {
			moveDownTree(child)
		}
	}
	moveDownTree(startNode)
	if b != nil {
		return b, nil
	}
	return nil, errors.New("could not find body element")
}

func GetNodeString(node *html.Node) string {
	var buf bytes.Buffer
	writer := io.Writer(&buf)
	html.Render(writer, node)
	return buf.String()
}

func WriteHtmlToFile(doc *html.Node) bool {
	newHtml := GetNodeString(doc)

	if _, err := os.Stat(FILENAME); !os.IsNotExist(err) {
		os.Remove(FILENAME)
	}

	file, err := os.OpenFile(FILENAME, os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	defer file.Close()

	if err != nil {
		fmt.Println(err)
		return false
	}

	file.Truncate(0)
	file.Seek(0, 0)

	_, err = file.WriteString(newHtml)
	if err != nil {
		fmt.Println(err)
		return false
	}
	return true
}

func getFileDirectory() string {
	_, filename, _, ok := runtime.Caller(0)
	if !ok {
		panic("No caller information")
	}
	return path.Dir(filename)
}

//from https://gist.github.com/hyg/9c4afcd91fe24316cbf0
func openbrowser(url string) {
	var err error

	switch runtime.GOOS {
	case "linux":
		err = exec.Command("xdg-open", url).Start()
	case "windows":
		err = exec.Command("rundll32", "url.dll,FileProtocolHandler", url).Start()
	case "darwin":
		err = exec.Command("open", url).Start()
	default:
		err = fmt.Errorf("unsupported platform")
	}
	if err != nil {
		log.Fatal(err)
	}

}

func main() {
	htm := getHTMLdoc(DOC)
	document, _ := html.Parse(strings.NewReader(htm))

	bodyNode, err := getBodyNode(document)
	if err != nil {
		log.Fatal(err)
	}
	highlightKeyword(bodyNode)

	if !WriteHtmlToFile(document) {
		log.Fatal("could not write html to file")
	}
	openbrowser(getFileDirectory() + "\\" + FILENAME)
}
