(HW 4)
This homework was a doozy, I'm not sure if it is up to the functionality that Chaw expects, as he is quite ambiguous on the
homework's specifications. 
Firstly, the syntax to define a regular expression is define %name% regex \n (the regular expression over x amount of lines)
I'm not sure if this is how he wants it, but I cannot think of another way to write it without changing a major part of my code.

The regular expression is both parsed into an infix regular expression and prefix regex, to allow for utilization when converting into an FSA.
This was for my use, as Thompson's algorithm functions better for infix regular expressions.
The way to access this call is define %name% regex2fsa %nameOfRegex%

GNFAs are supported through two other classes, named GNFA.java and GNFAState.java
These called by a manner syntactically identical to that specified in the homework, starting out with define %name% then what follows on the homework

I didn't get to finishing the DFA to regex section, as it was very complex to delete the states and I ran out of time doing this.
If I had time I may be able to do this, but I also may need to change the functionality of my classes so that it isn't so difficult to access the transitions of GNFAs
This will convert from dfa to gnfa: define %name% dfa2regex %nameofFSA%