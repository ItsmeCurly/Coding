JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        InvalidArgsException.java \
        InvalidKValException.java \
        KSearchTree.java \
        Pane.java \
        RecordManager.java \
        SwingWindow.java \
        TreeNode.java

MAIN = SwingWindow

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class