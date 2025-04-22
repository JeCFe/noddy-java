package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(

        @NotBlank(message = "Reference must not be blank") String reference,

        @NotBlank(message = "Username must not be blank") String username) {
}
