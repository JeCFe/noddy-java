package com.example.demo.dto;

import com.example.demo.models.TodoItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TodoDto(

        @NotBlank(message = "Reference must not be blank") String reference,
        @NotBlank(message = "Description must not be blank") String description,
        @NotNull(message = "Completed is required") boolean completed) {

    public TodoDto(TodoItem todoItem) {
        this(todoItem.getReference(), todoItem.getDescription(), todoItem.isCompleted());
    }

}
