package com.library.api.exceptions;

public class BookNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 2;

    public BookNotFoundException(String message) {
        super(message);
    }
}
