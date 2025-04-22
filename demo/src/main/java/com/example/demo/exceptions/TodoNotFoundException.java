package com.example.demo.exceptions;

public class TodoNotFoundException extends NotFoundException {
    public TodoNotFoundException(String reference) {
        super(String.format("No todo exists with reference %s", reference));
    }
}
