In this program, the user is to interact with a hash table by inserting
specific objects into it and having the program automatically insert the objects
into a hash table with the collision resolution technique of coalesced hashing.
The program is completely interchangeable with the textbook's version of the program,
but instead of using quadratic hashing it uses coalesced hashing with linear probing for the linked list.
This allows the program to utilize separate chaining within the program whilst also allowing it to get rid of primary and secondary clustering.
Some of the debugging lines were left in the program for testing purposes, and the testing class for this project is called TestClass.java.
Using TestClass.java will insert the 10 words from the homework into a hash table using coalesced hashing,
and if the toString is called will display the connections of the linked list as well.
*findPos in HashSet has two println's that are commented out for debugging purposes along with the toString in the TestClass. If all of these are uncommented,
it is easier to see the product of the hash table than using the iterator, as the nulls are displayed with the non-nulls in the toString