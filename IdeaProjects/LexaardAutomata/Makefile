GS = -g
JC = javac
.SUFFIXES: .java .class
	.java.class:
	        $(JC) $(JFLAGS) $*.java

CLASSES = \
	  Automaton.java \
	  GNFA.java \
	  GNFAState.java \
	  Interpreter.java \
	  OLDREGEX.java \
	  Regex.java \
	  State.java \
	  testingclass.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	        $(RM) *.class