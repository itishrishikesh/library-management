package com.library.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Borrow {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        long id;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        User user;
        @ManyToOne
        @JoinColumn(name = "book_id", referencedColumnName = "id")
        Book book;
        LocalDate borrowDate;
        LocalDate returnDate;
}
