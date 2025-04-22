package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.TodoItem;

public interface TodoRepository extends CrudRepository<TodoItem, Long> {
    Optional<TodoItem> findByReference(String reference);
}
