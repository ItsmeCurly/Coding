package main

import "fmt"

type Node struct {
	Value    int
	Children []*Node
}

func main() {
	a, b, c, d := &Node{1, []*Node{}}, &Node{2, []*Node{}}, &Node{5, []*Node{}}, &Node{7, []*Node{}}
	a.Children = []*Node{b, c}
	b.Children = []*Node{d}

	fmt.Printf("Node value found: %d\n", recursiveBreadthFirst([]*Node{a}, 7).Value)
	fmt.Printf("Node value found: %d\n", iterativeBreadthFirst(a, 7).Value)
}
