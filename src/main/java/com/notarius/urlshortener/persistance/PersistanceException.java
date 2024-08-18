package com.notarius.urlshortener.persistance;

public class PersistanceException extends Exception {
    public PersistanceException(String message) {
        super(message);
    }

    public PersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
