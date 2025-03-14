package com.imposto.exceptions;

public class ExistingResourceException extends RuntimeException {
    public ExistingResourceException(String message) {
        super(message);
    }
}
