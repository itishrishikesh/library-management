package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllBooks() {
        List<Book> expectedList = List.of(
                new Book(1, "Hidden Castle", "Jonathan Jones", "Drama", 1),
                new Book(1, "Open Castle", "Megan Whitaker", "Action", 1)
        );
        Mockito.when(bookRepository.findAll()).thenReturn(expectedList);
        List<Book> actualList = bookService.getAllBooks();
        Assertions.assertEquals(expectedList, actualList);
    }
}
