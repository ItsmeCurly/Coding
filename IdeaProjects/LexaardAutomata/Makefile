JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES : \
	    Automaton.java \
	    GNFA.java \
	    Interpreter.java \
	    OLDREGEX.java \
	    Regex.java \
	    CFG.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class::