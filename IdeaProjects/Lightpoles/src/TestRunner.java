import java.io.File;

/**
 * Class to be used for test cases, implements CROSS because they are cross referenced through classes
 */
public class TestRunner implements CROSS {

    public static void main(String[] args) {
        File file = new File(fileString);
        new Interpreter(file);
    }
}
