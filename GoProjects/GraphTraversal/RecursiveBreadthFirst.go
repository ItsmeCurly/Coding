package main

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
