package com.GUI;

public class InvalidKValException extends Exception {
    InvalidKValException() {
    }

    public InvalidKValException(String message) {
        super(message);
    }

    public InvalidKValException(Throwable cause) {
        super(cause);
    }

    public InvalidKValException(String message, Throwable cause) {
        super(message, cause);
    }
}
