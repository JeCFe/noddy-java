package com.example.demo.exceptions;

public class CustomerAlreadyExistsException extends Exception {
    public CustomerAlreadyExistsException(String username, Long id) {
        super(String.format("Reference %s already has a customer record with id %s", username, id));
    }

}
