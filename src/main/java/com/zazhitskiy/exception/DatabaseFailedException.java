package com.zazhitskiy.exception;

public class DatabaseFailedException extends RuntimeException {
    public DatabaseFailedException(String message) {
        super(message);
    }
}
