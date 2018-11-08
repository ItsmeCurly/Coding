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
	KEYWORD         = "to"
	HighlightColor  = "#000000"
	BackgroundColor = "#FFFF00"
	NewFilename     = "newhtml.html"
	OldFilename     = "html.html"
	OpenBrowser     = true
	OpenFromFile    = true
)

/*
Checks for an error, generic error checker
*/
func check(e error) {
	if e != nil {
		log.Fatal(e)
	}
}

/*
Gets HTML from website specified by the DOC variable
*/
func getHTMLdoc(doc string) string {
	resp, err := http.Get(doc)
	check(err)
	defer resp.Body.Close()

	bodyBytes, err := ioutil.ReadAll(resp.Body)
	check(err)

	return string(bodyBytes)
}

/*
Gets the html file from the directory, named html.html by default.

HTML file can be specified by the OldFilename variable

*/
func getHTMLfile() string {
	dat, err := ioutil.ReadFile(OldFilename)
	check(err)

	return string(dat)
}

/*
main function - with body node will recursively travel down the tree to find the text nodes that are not scripts and
will search within the text to find the keyword. When a keyword is found, the function will separate the textnode into
two text nodes with a span node in the middle and a child textnode pointing to that. The function will work with an
arbitrary amount of occurrences within a text node.

The highlighting colors are specified through the BackgroundColor and HighlightColor
*/
func highlightKeyword(bodyNode *html.Node) {
	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.TextNode {
			if n.Parent.Data != "script" {
				text := strings.Split(n.Data, KEYWORD)
				for i := 0; i < len(text)-1; i++ {
					leftText := text[i]

					rightText := text[i+1]

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
					if i == len(text)-2 {
						n.Parent.InsertBefore(rightTextNode, n)
					}

				}
				if strings.Contains(n.Data, KEYWORD) {
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

/*
Returns the body node from the html file

Returns an error if no body node is found
*/
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

/*
Gets the string of the HTML from the node down
*/
func GetNodeString(node *html.Node) string {
	var buf bytes.Buffer
	writer := io.Writer(&buf)
	html.Render(writer, node)
	return buf.String()
}

/*
Writes the HTML to the new file, specified by the NewHTMLFile variable
*/
func WriteHtmlToFile(doc *html.Node) bool {
	newHtml := GetNodeString(doc)

	if _, err := os.Stat(NewFilename); !os.IsNotExist(err) {
		os.Remove(NewFilename)
	}

	file, err := os.OpenFile(NewFilename, os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
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

/*
Gets the file directory of the caller, for use with opening browser
*/
func getFileDirectory() string {
	_, filename, _, ok := runtime.Caller(0)
	if !ok {
		panic("No caller information")
	}
	return path.Dir(filename)
}

//
/*
from https://gist.github.com/hyg/9c4afcd91fe24316cbf0
Opens up a browser with certain url, specified by the html file
*/
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
	check(err)

}

func main() {
	var htm string
	if OpenFromFile {
		htm = getHTMLfile()
	} else {
		htm = getHTMLdoc(DOC)
	}
	document, _ := html.Parse(strings.NewReader(htm))

	bodyNode, err := getBodyNode(document)
	check(err)

	highlightKeyword(bodyNode)

	if !WriteHtmlToFile(document) {
		log.Fatal("could not write html to file")
	}
	if OpenBrowser {
		openbrowser(getFileDirectory() + "\\" + NewFilename)
	}
}
