JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        CROSS.java \
        Interpreter.java \
        RandomPoleGeneration.java \
        Poles.java \
        MainRunner.java \
        TestRunner.java

MAIN = MainRunner

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class