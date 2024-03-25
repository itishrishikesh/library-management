package com.library.management.entity;

import jakarta.persistence.*;

@Entity
public record Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long id,
        String name,
        String author,
        String category,
        int quantity
) {
}
