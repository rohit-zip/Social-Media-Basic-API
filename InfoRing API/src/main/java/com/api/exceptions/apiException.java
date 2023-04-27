package com.api.exceptions;

public class apiException extends RuntimeException{
    public apiException(String message) {
        super(message);
    }

    public apiException() {
    }
}
