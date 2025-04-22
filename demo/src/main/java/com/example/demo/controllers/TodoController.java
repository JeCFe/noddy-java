package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.TodoDto;
import com.example.demo.exceptions.CustomerAlreadyExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.TodoAlreadyExistsException;
import com.example.demo.exceptions.TodoNotFoundException;
import com.example.demo.services.TodoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/create-customer")
    public ResponseEntity postCreateCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        try {
            todoService.addCustomer(request.reference(), request.username());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CustomerAlreadyExistsException ex) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get-todos")
    public ResponseEntity getAllTodoItems(@RequestParam String reference) {
        try {
            var todos = todoService.getTodoItemsForCustomer(reference);
            return new ResponseEntity<List<TodoDto>>(todos, HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<String>(String.format("Customer for %s not found", reference),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-todo")
    public ResponseEntity postCreateTodo(@RequestBody @Valid CreateTodoRequest entity) {
        try {
            todoService.createTodo(entity.reference(), entity.todo());

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<String>(String.format("Customer for %s not found", entity.reference()),
                    HttpStatus.NOT_FOUND);
        } catch (TodoAlreadyExistsException ex) {
            return new ResponseEntity<String>(
                    String.format("Todo with reference %s already exists", entity.reference()),
                    HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity postCompleteTodo(@RequestParam String reference) {
        try {
            todoService.completeTodo(reference);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (TodoNotFoundException ex) {
            return new ResponseEntity<String>(String.format("Todo item for %s not found", reference),
                    HttpStatus.NOT_FOUND);
        }
    }
}
