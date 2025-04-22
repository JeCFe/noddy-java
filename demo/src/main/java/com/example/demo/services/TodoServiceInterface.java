package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.TodoDto;
import com.example.demo.exceptions.CustomerAlreadyExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.TodoAlreadyExistsException;
import com.example.demo.exceptions.TodoNotFoundException;

public interface TodoServiceInterface {
    void addCustomer(String refrerence, String username) throws CustomerAlreadyExistsException;

    List<TodoDto> getTodoItemsForCustomer(String reference) throws CustomerNotFoundException;

    void createTodo(String reference, TodoDto todo) throws CustomerNotFoundException, TodoAlreadyExistsException;

    void completeTodo(String reference) throws TodoNotFoundException;
}
