package com.library.management.service;

import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.library.management.entity.Book;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream().filter(book -> book.getQuantity() > 0).toList();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
