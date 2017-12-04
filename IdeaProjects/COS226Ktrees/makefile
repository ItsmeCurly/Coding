JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        KSearchTree.java \
        main.java \
        RecordManager.java \
        TreeNode.java

MAIN = main

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class