JJ - the algorithms and the class object
Interpreter - main function
SpeedAnalysis - Copy of Interpreter except takes input from txt files
ArrayGen - randomly generates number of strings specified from DATA with random values to run for speed analysis
DATA - holds number of strings to be made in the txt files

This project implements algorithms with additions from hw1 including coins on the ground and costs to add difficulty
to the implementation when JJ moves. This makes the algorithm calculate costs by having to iterate through all 
possible combinations. The algorithms both run in 2^n time, as shown by the pdf named Alg Analysis.pdf, which shows
both graphs runtime increase by 2^n(by a constant factor, enumeration is alot larger in time but grows at the same
rate).