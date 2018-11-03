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
	"strings"
)

const (
	FILE_OUTPUT     = "C:\\Users\\Bonnie\\Coding\\GoProjects\\HTMLParsing"
	DOC             = "https://www.webscraper.io/test-sites/e-commerce/allinone"
	KEYWORD         = "Lenovo"
	HighlightColor  = "white"
	BackgroundColor = "blue"
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

func getSpanNode(startNode *html.Node) (*html.Node, error) {
	var b *html.Node
	var moveDownTree func(*html.Node)
	moveDownTree = func(node *html.Node) {
		if node.Type == html.ElementNode && node.Data == "span" {
			for _, element := range node.Attr {
				fmt.Print(element.Namespace + " ")
				fmt.Print(element.Key + " ")
				fmt.Println(element.Val)
			}
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

func getNodeString(node *html.Node) string {
	var buf bytes.Buffer
	writer := io.Writer(&buf)
	html.Render(writer, node)
	return buf.String()
}

func main() {
	htm := getHTMLdoc(DOC)
	document, _ := html.Parse(strings.NewReader(htm))

	bodyNode, err := getBodyNode(document)
	if err != nil {
		log.Fatal(err)
	}
	highlightKeyword(bodyNode)

	fmt.Println(getNodeString(document))
}
