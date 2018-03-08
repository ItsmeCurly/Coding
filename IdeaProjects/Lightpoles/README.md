The main problem of the program consists of classes Interpreter.java, MainRunner.java, and Poles.java
These three classes handle the input, the running of the program, and the algorithm itself. 
Along with this, the project can be run with only these classes, but the other classes included were for
algorithm analysis.

To run the program, run MainRunner through make run, and the class will wait for input, which is the 
list of pole lengths. 

Algorithm analysis - The RandomPoleGeneration.java class, when run, creates an n amount of lists of pole lengths, denoted by 
the variable RUNS in CROSS.java, an interface. This RUNS variable is used in multiple classes, so it seems appropriate that 
it is used in an interface. When the lists are generated, they are placed in a file called poles.txt that is located in a 
dir called input, which is located in the main folder. After TestRunner.java is run with this, the poles.txt data is read in
and the resulting combinations, calls to the recursive method, and times will be output into a new txt file denoted times.txt,
which is located in a dir called output in the main project directory.

Makefile commands such as build, clean, and run are included, but I'm not sure of their functionality yet, as I'm still quite 
foggy on the functionality of make when I'm using a windows OS, which has a lot better integration with gradle anyways.

Included in the project is also a .pdf file that contain algorithm analysis for the run times of test cases (I kept the test cases at 150 runs)