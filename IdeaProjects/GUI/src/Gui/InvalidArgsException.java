package Gui;

public class InvalidArgsException extends Exception {
    InvalidArgsException() {
    }

    public InvalidArgsException(String message) {
        super(message);
    }

    public InvalidArgsException(Throwable cause) {
        super(cause);
    }

    public InvalidArgsException(String message, Throwable cause) {
        super(message, cause);
    }
}
