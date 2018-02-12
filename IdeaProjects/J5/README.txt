The RunnerTester, RandomArrayGen, and DATA classes are for random running testing of the data to analyze runtimes.
Runner is the running class that takes input in the form of $DOORLENGTH {pogostick lengths}$ After taking these, it will
print the #of combos and then the combinations themselves every new line, as specified in the homework

The JJ and Runner classes are the two main classes that suffice the problem. Runner takes input and modifies it for JJ
to use it, then JJ will run the recursive algorithm to find all the combinations.

Some other files in this project are inputs.txt, which consist of randomly generated sequences created by RandomArrayGen,
times.txt, which consists of times recorded by calling System.currenTimeMillis and analyzing the runtime of a certain
sequence on the algorithm, and cos350results.pdf, which displays a table and a graph of my results, with the x being doorLength
and y being time on the graph. Along with these are the makefile and README.txt(this).