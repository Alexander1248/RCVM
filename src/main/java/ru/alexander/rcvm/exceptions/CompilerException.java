package ru.alexander.rcvm.exceptions;

public class CompilerException extends RuntimeException {
    public CompilerException() {
    }

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerException(Throwable cause) {
        super(cause);
    }
}
