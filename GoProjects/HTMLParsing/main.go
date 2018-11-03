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

const DOC = "https://www.webscraper.io/test-sites/e-commerce/allinone"
const KEYWORD = "Lenovo"
const SPAN_KEYWORD = <span style = "background-color: blue; color: white"> + KEYWORD + </span>

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

//
//func highlightKeyword(keyword string, bodyString string) (highlightedBodyText string){
//
//	z := html.NewTokenizer(strings.NewReader(bodyString))
//	previousStartTagToken := z.Token()
//	r:
//	for {
//		tt := z.Next()
//
//		switch tt {
//		case html.ErrorToken:
//			break r
//		case html.StartTagToken:
//			previousStartTagToken = z.Token()
//		case html.TextToken:
//			if previousStartTagToken.Data == "script" {
//				continue
//			}
//			html.TextNode.
//			z.Token().Data = ""
//			//z.Token().Data = strings.Replace(z.Token().Data, keyword, SPAN_KEYWORD, -1)
//
//			//bodyText += strings.TrimSpace(html.UnescapeString(string(z.Text())))
//		}
//	}
//	return bodyText
//}

func highlightKeyword(keyword string, bodyNode *html.Node) (highlightedBodyText string) {

	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.TextNode {
			if n.Parent.Data == "script" {
				oldText := n.Data
				text := strings.Split(oldText, keyword)
				leftText := text[0]

				rightText := text[1]

				leftTextNode := &html.Node{
					Type: html.TextNode,
					Data: leftText,
				}
				spanTextNode := &html.Node{
					Type: html.ElementNode,
					Data: "span",
				}
				rightTextNode := &html.Node{
					Type: html.TextNode,
					Data: rightText,
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


func main() {
	htm := getHTMLdoc(DOC)

}


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

const DOC = "https://www.webscraper.io/test-sites/e-commerce/allinone"
const KEYWORD = "Lenovo"
const SPAN_KEYWORD = <span style = "background-color: blue; color: white"> + KEYWORD + </span>

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

func getNodeString(node *html.Node) string {
	var buf bytes.Buffer
	writer := io.Writer(&buf)
	html.Render(writer, node)
	return buf.String()
}

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

//
//func highlightKeyword(keyword string, bodyString string) (highlightedBodyText string){
//
//	z := html.NewTokenizer(strings.NewReader(bodyString))
//	previousStartTagToken := z.Token()
//	r:
//	for {
//		tt := z.Next()
//
//		switch tt {
//		case html.ErrorToken:
//			break r
//		case html.StartTagToken:
//			previousStartTagToken = z.Token()
//		case html.TextToken:
//			if previousStartTagToken.Data == "script" {
//				continue
//			}
//			html.TextNode.
//			z.Token().Data = ""
//			//z.Token().Data = strings.Replace(z.Token().Data, keyword, SPAN_KEYWORD, -1)
//
//			//bodyText += strings.TrimSpace(html.UnescapeString(string(z.Text())))
//		}
//	}
//	return bodyText
//}

func highlightKeyword(keyword string, bodyNode *html.Node) (highlightedBodyText string) {

	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.TextNode {
			if n.Parent.Data == "script"
			for {

				oldText := n.Data
				newText := strings.Replace(n.Data, keyword, SPAN_KEYWORD, -1)
				newNode = *htmlNode()
			}
		}
		for c := n.FirstChild; c != nil; c = c.NextSibling {
			f(c)
		}
	}
	f(bodyNode)
	return
}

func main() {
	htm := getHTMLdoc(DOC)
	document, _ := html.Parse(strings.NewReader(htm))
	bodyNode, err := getBodyNode(document)
	if err != nil {
		log.Fatal(err)
	}
	//bodyString := getBodyString(bodyNode)

	//fmt.Println(highlightKeyword(KEYWORD, bodyString))
	highlightKeyword(KEYWORD, bodyNode)

	fmt.Println(getNodeString(document))

	//fmt.Println(bodyText)
}
