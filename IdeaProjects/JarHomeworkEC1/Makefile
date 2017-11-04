JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class

.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        AbstractClock.java \
        AnalogClock.java \
        DateAndTime.java \
        DigitalClock.java \
        Window.java \
        SliderBasedClock.java \
        ClcokGUI.java

MAIN = Window

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class