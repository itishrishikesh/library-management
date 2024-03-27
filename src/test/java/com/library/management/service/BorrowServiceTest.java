package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
import com.library.management.entity.User;
import com.library.management.exception.LimitExceededException;
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
    public void shouldBeAbleToBorrowABook() {
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

    @Test
    public void shouldNotBeAbleToBorrowABookIfLimitExceeded() {
        User borrowerUser = new User(1, "username");
        Book bookToBorrow = new Book(1, "Jonathan Seagull", "John", "drama", 1);
        Borrow expectedBorrow = new Borrow(0, borrowerUser, bookToBorrow, LocalDate.now(), LocalDate.now().plusDays(15));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(borrowerUser));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(bookToBorrow));
        Mockito.when(bookRepository.save(bookToBorrow)).thenReturn(bookToBorrow);
        Mockito.when(borrowRepository.save(expectedBorrow)).thenThrow(LimitExceededException.class);

        Assertions.assertThrows(LimitExceededException.class, () -> borrowService.borrowBook(1, 1));
    }

    @Test
    public void shouldBeAbleToReturnABook() {
        User borrowerUser = new User(1, "username");
        Book bookToBorrow = new Book(1, "Jonathan Seagull", "John", "drama", 1);
        Borrow toDelete = new Borrow(0, borrowerUser, bookToBorrow, LocalDate.now(), LocalDate.now().plusDays(15));

        Mockito.when(borrowRepository.findByBookIdAndUserId(1, 1)).thenReturn(Optional.of(toDelete));
        Mockito.doNothing().when(borrowRepository).delete(toDelete);
        borrowService.returnBook(1, 1);
    }
}
