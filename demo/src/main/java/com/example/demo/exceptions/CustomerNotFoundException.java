package com.example.demo.exceptions;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(String reference) {
        super(String.format("No customer exists with reference %s", reference));
    }
}
