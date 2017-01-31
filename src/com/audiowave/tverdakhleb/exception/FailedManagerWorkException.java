package com.audiowave.tverdakhleb.exception;

public class FailedManagerWorkException extends Exception {
    public FailedManagerWorkException() {
        super();
    }

    public FailedManagerWorkException(String message) {
        super(message);
    }

    public FailedManagerWorkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedManagerWorkException(Throwable cause) {
        super(cause);
    }

    protected FailedManagerWorkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
