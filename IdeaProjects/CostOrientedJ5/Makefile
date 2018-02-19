JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        DATA.java \
        JJ.java \
        ArrayGen.java \
        Interpreter.java \
        SpeedAnalysis.java

MAIN = Interpreter

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class