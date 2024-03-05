package com.library.api.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    private static final long serialVersion = 1;

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
