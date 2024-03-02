package ru.alexander.rcvm.exceptions;

public class AssemblerException extends RuntimeException {
    public AssemblerException() {
    }

    public AssemblerException(String message) {
        super(message);
    }

    public AssemblerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssemblerException(Throwable cause) {
        super(cause);
    }
}
