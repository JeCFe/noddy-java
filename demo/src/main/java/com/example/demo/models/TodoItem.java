package com.example.demo.models;

import com.example.demo.dto.TodoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String reference;

    private String description;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public TodoItem(String description, String reference, boolean completed) {
        this.description = description;
        this.reference = reference;
        this.completed = completed;
    }

    public TodoItem(TodoDto todo) {
        this.description = todo.description();
        this.reference = todo.reference();
        this.completed = todo.completed();

    }

    public TodoItem complete() {
        this.completed = true;
        return this;
    }

    public TodoItem setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}
