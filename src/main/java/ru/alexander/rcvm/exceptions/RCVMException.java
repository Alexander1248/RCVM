package ru.alexander.rcvm.exceptions;

public class RCVMException extends RuntimeException {
    public RCVMException() {
    }

    public RCVMException(String message) {
        super(message);
    }

    public RCVMException(String message, Throwable cause) {
        super(message, cause);
    }

    public RCVMException(Throwable cause) {
        super(cause);
    }
}
