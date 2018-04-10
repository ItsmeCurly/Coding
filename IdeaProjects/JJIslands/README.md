Interpreter.java - Main running class, takes user input and generates the output specific to the homework proposal

IslandGenerator.java - Generates 100 random islands, specified by the variable RUNS. Can be very dense/sparse depending on randomization. Stores result in testInput.txt for result.

IslandGenerator2.java - Generates (currently four) islands specified by RUNS, ranging 3-RUNS+3 islands, of fully dense graphs, and stores them in input/testInput1.txt

Islands.java - Contains the algorithm and other helper methods. Takes islands in x1 x2 format and outputs the longest cycle back to the start island when given specified input.

TestInterpreter.java - Interprets test input from .txt file. Generates multiple island classes and outputs their results to testOutput.txt for simple exporting to excel for graph conversion

Makefile included

testIslands.txt - Contains the test input from the homework.

Task - When given input in the format specified by the homework, will generate the longest route, and output as per specified by the homework.

hw05Analysis.pdf - contains four images, two charts and two graphs. First graph is analysis of the sparse/dense graph input from testInput.txt. This results in a middle case analysis for the homework. The second graph is from the testInput1.txt file, which is a worst case analysis of the algorithm.
The charts are included, specifying how some of the input is laid out in the excel file.