package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.TodoDto;
import com.example.demo.exceptions.CustomerAlreadyExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.models.Customer;
import com.example.demo.models.TodoItem;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.TodoRepository;
import com.example.demo.services.TodoService;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTests {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    TodoRepository todoRepository;

    TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService(customerRepository, todoRepository);
    }

    @Test
    void addCustomer_withExistingCustomer_shouldThrowCustomerAlreadyExistsException() {
        var reference = "123";
        Customer existingCustomer = new Customer(reference, "jessica");
        when(customerRepository.findByReference(reference)).thenReturn(Optional.of(existingCustomer));

        var thrown = assertThrows(CustomerAlreadyExistsException.class,
                () -> todoService.addCustomer(reference, "bob"));

        assertEquals(String.format("Reference %s already has a customer record with id %s", reference,
                existingCustomer.getId()), thrown.getMessage());
    }

    @Test
    void addCustomer_withoutAnExistingCustomer_shouldSaveNewCustomer() throws Exception {
        var reference = "123";
        var username = "jessica";

        when(customerRepository.findByReference(reference)).thenReturn(Optional.empty());
        todoService.addCustomer(reference, username);

        verify(customerRepository).save(argThat(customer -> customer.getReference().equals(reference) &&
                customer.getUsername().equals(username)));
    }

    @Test
    void getTodoItemsForCustomer_withoutAnExistingCustomer_shouldThrowCustomerNotFoundException() {
        var reference = "123";
        when(customerRepository.findByReference(reference)).thenReturn(Optional.empty());

        var thrown = assertThrows(CustomerNotFoundException.class,
                () -> todoService.getTodoItemsForCustomer(reference));

        assertEquals(String.format("No customer exists with reference %s", reference), thrown.getMessage());
    }

    @Test
    void getTodoItemsForCustomer_withAnExistingCustomerButNoTodos_shouldReturnEmptyArray() throws Exception {
        var reference = "123";
        Customer existingCustomer = new Customer(reference, "jessica");

        when(customerRepository.findByReference(reference)).thenReturn(Optional.of(existingCustomer));
        var returnedTodos = todoService.getTodoItemsForCustomer(reference);
        assertTrue(returnedTodos.isEmpty());
    }

    @Test
    void getTodoItemsForCustomer_withAnExistingCustomerwithTodos_shouldReturnListOTodoDto() throws Exception {
        var reference = "123";
        var todoRef = "456";
        var description = "This is a todo";
        var complete = false;
        TodoItem todoItem = new TodoItem(description, todoRef, complete);
        Customer existingCustomer = new Customer(reference, "jessica").addTodo(todoItem);

        when(customerRepository.findByReference(reference)).thenReturn(Optional.of(existingCustomer));
        var returnedTodos = todoService.getTodoItemsForCustomer(reference);

        assertEquals(returnedTodos, List.of(new TodoDto(todoItem)));

    }
}
