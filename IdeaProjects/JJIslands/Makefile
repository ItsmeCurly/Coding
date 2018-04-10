JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
	    Interpreter.java \
	    IslandGenerator.java \
	    IslandGenerator2.java \
	    Islands.java \
	    TestInterpreter.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class