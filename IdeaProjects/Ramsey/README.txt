Creates a graph in the specified format ((x)(x y) (z a)), and will print out the cliques and anticliques that are among
the graph. The only issue I ran into is that if the program chooses a node x that is connected to y and z, and y z are
connected as well, the connection between y z is lost when printing out the clique.

The Graph class utilizes generics to accept graphs of any variable, but the connections only run on characters, so testing others will not work.
The Runner + Graph class accept any type of input, but will only run for characters as well, so others will throw errors.

The main objective of this is to prove Ramsey's theorem, which states that any graph contains a clique or anticlique that is >1/2log(n) of the original node size.

The included files in this are Graph.java, Runner.java, the makefile, and the README.txt(this).