package com.library.management.controller;

import com.library.management.entity.Book;
import com.library.management.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BookControllerTest {
    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldListAllBooksInLibrary() {
        List<Book> expectedList = List.of(
                new Book(1, "Secrets from Heaven", "James Bell", "Self-Help", 2),
                new Book(1, "3 Musketeers", "Jona Lolla", "Fiction", 1),
                new Book(3, "Charger", "Nathan Cain", "Science Fiction", 1)
        );
        Mockito.when(bookService.getAllBooks()).thenReturn(expectedList);
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(expectedList, responseEntity.getBody());
    }

    @Test
    public void shouldListNoBooks() {
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of());
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(List.of(), responseEntity.getBody());
    }

    @Test
    public void shouldUpdateBook() {
        Book newBook = new Book(1, "3 Musketeers", "Jona Lolla", "Fiction", 1);
        Mockito.when(bookService.updateBook(newBook)).thenReturn(newBook);
        ResponseEntity<Book> responseEntity = bookController.updateBook(newBook);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(newBook, responseEntity.getBody());
    }
}
