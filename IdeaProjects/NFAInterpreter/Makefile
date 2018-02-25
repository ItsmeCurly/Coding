JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Automaton.java \
        Interpreter.java

MAIN = Runner

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class