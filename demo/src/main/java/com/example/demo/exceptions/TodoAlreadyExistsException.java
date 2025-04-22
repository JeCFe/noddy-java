package com.example.demo.exceptions;

public class TodoAlreadyExistsException extends Exception {
    public TodoAlreadyExistsException(String reference, Long id) {
        super(String.format("Reference %s already has a todo record with id %s", reference, id));
    }

}
