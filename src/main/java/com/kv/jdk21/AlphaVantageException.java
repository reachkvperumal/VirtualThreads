package com.kv.jdk21;

public class AlphaVantageException extends RuntimeException{
    public AlphaVantageException() {
    }

    public AlphaVantageException(String message) {
        super(message);
    }

    public AlphaVantageException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlphaVantageException(Throwable cause) {
        super(cause);
    }

    public AlphaVantageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
