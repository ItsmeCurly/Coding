JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        AbstractClock.java \
        AnalogClock.java \
        ClockGUI.java \
        DateAndTime.java \
        DigitalClock.java \
        SliderBasedClock.java \
        Window.java

MAIN = Window

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
        $(RM) *.class