import java.io.File;

public class TestRunner implements CROSS {

    public static void main(String[] args) {
        File file = new File(fileString);
        new Interpreter(file);
    }
}
