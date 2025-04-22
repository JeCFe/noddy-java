package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TodoDto;
import com.example.demo.exceptions.CustomerAlreadyExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.TodoAlreadyExistsException;
import com.example.demo.exceptions.TodoNotFoundException;
import com.example.demo.models.Customer;
import com.example.demo.models.TodoItem;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.TodoRepository;

@Service
public class TodoService implements TodoServiceInterface {

    private final CustomerRepository customerRepository;
    private final TodoRepository todoRepository;

    public TodoService(CustomerRepository customerRepository, TodoRepository todoRepository) {
        this.customerRepository = customerRepository;
        this.todoRepository = todoRepository;
    }

    public void addCustomer(String refrerence, String username) throws CustomerAlreadyExistsException {
        var existingCustomer = customerRepository.findByReference(refrerence);
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(refrerence, existingCustomer.get().getId());
        }
        customerRepository.save(new Customer(refrerence, username));
    }

    public List<TodoDto> getTodoItemsForCustomer(String reference) throws CustomerNotFoundException {
        var user = customerRepository.findByReference(reference)
                .orElseThrow(() -> new CustomerNotFoundException(reference));

        return user.getTodoItems().stream().map(TodoDto::new).collect(Collectors.toList());
    }

    public void createTodo(String reference, TodoDto todo)
            throws CustomerNotFoundException, TodoAlreadyExistsException {
        Customer user = customerRepository.findByReference(reference)
                .orElseThrow(() -> new CustomerNotFoundException(reference));

        var existing = todoRepository.findByReference(reference);
        if (existing.isPresent()) {
            throw new TodoAlreadyExistsException(reference, existing.get().getId());
        }

        user.addTodo(new TodoItem(todo));
        customerRepository.save(user);
    }

    public void completeTodo(String reference) throws TodoNotFoundException {
        var existingTodo = todoRepository.findByReference(reference)
                .orElseThrow(() -> new TodoNotFoundException(reference));
        existingTodo.complete();
    }
}
