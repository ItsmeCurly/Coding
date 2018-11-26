package main

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
