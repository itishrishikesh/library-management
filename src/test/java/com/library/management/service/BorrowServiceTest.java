package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
import com.library.management.entity.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowServiceTest {
    @InjectMocks
    BorrowService borrowService;

    @Mock
    BorrowRepository borrowRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void borrowABook() {
        User borrowerUser = new User(1, "username");
        Book bookToBorrow = new Book(1, "Jonathan Seagull", "John", "drama", 1);
        Borrow expectedBorrow = new Borrow(0, borrowerUser, bookToBorrow, LocalDate.now(), LocalDate.now().plusDays(15));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(borrowerUser));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(bookToBorrow));
        bookToBorrow.setQuantity(bookToBorrow.getQuantity() - 1);
        Mockito.when(bookRepository.save(bookToBorrow)).thenReturn(bookToBorrow);
        Mockito.when(borrowRepository.save(expectedBorrow)).thenReturn(expectedBorrow);

        Borrow actualBorrow = borrowService.borrowBook(1, 1);

        Assertions.assertEquals(expectedBorrow, actualBorrow);
    }
}
