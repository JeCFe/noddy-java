package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTodoRequest(

        @NotBlank(message = "Reference must not be blank") String reference,

        @NotNull(message = "Todo must not be blank") TodoDto todo) {
}
