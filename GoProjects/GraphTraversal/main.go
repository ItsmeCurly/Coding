package main

import "fmt"

type Node struct {
	Value    int
	Children []*Node
}

func iterativeBreadthFirst(root *Node, val int) (node *Node) {
	children := []*Node{root}

	for {
		if len(children) == 0 {
			return nil
		}
		for _, c := range children {
			if val == c.Value {
				node = c
				goto end
			}
		}
		newChildren := make([]*Node, 0)
		for _, c := range children {
			newChildren = append(newChildren, c.Children...)
		}
		children = newChildren
	}
end:
	return
}

func recursiveBreadthFirst(nodes []*Node, val int) *Node {
	if len(nodes) == 0 {
		return nil
	}
	for _, n := range nodes {
		if n.Value == val {
			return n
		}
	}
	newChildren := make([]*Node, 0)
	for _, n := range nodes {
		for _, child := range n.Children {
			newChildren = append(newChildren, child)
		}
	}
	return recursiveBreadthFirst(newChildren, val)
}

func main() {
	a, b, c, d := &Node{1, []*Node{}}, &Node{2, []*Node{}}, &Node{5, []*Node{}}, &Node{7, []*Node{}}
	a.Children = []*Node{b, c}
	b.Children = []*Node{d}

	fmt.Println(recursiveBreadthFirst([]*Node{a}, 7))
	fmt.Println(iterativeBreadthFirst(a, 7))
}
