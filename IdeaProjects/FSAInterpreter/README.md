This module will take input from stdin(active interpreter) and will convert the input into the command and what the command follows
The first class, Interpreter.java takes the input and will store variables such as "x" and the automata. The interpreter will also print the variables, define variables, and run FSA's with certain input.
The interpreter will exit when the input is "quit" or when two blank lines are placed after a call. Output is combined with input, so they are not spaced out but rather lumped together.
The Automaton.java class takes the input specified by the homework and spawns an automaton that mirrors the input.
This can be run with a certain input string that will either reject or accept the string by calling the method "run."
From the interpreter, this run command can either be called by a predefined variable stored in the LinkedHashMap or an explicitly typed string.
Appropriate method documentation is present, so any present confusion may be resolved through that as well. Any present variables in the LinkedHashMap 
may be shadowed by having the same variable name and overwriting it, and can store as many variables as memory permits. 
makefile included in package along with source code.