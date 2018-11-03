package main

import (
	"errors"
	"fmt"
	"golang.org/x/net/html"
	"path"
	"runtime"
)

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

func getFileDirectory() string {
	_, filename, _, err := runtime.Caller(0)
	if !err {
		panic("No caller information")
	}
	return path.Dir(filename)
}

func main() {
	_, filename, _, ok := runtime.Caller(0)
	if !ok {
		panic("No caller information")
	}
	fmt.Printf("Filename : %q, Dir : %q\n", filename, path.Dir(filename))
}
