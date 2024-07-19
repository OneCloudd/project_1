package com.github.project1.service.exception;

public class InvalidValueException  extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}