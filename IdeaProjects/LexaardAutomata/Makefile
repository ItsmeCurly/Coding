JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Automaton.java \
        Interpreter.java \
        State.java \
        Regex.java \
        Interpreter.java \
        GNFAState.java \
        GNFA.java

MAIN = Interpreter

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class