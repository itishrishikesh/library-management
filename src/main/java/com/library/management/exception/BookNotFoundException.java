package com.library.management.exception;

import com.library.management.entity.Book;

public class BookNotFoundException extends RuntimeException {
    Book book;

    public BookNotFoundException(Book book) {
        super(String.format("Book %s not found.", book.name()));
        this.book = book;
    }
}
