package com.library.management.controller;

import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
import com.library.management.entity.User;
import com.library.management.service.BorrowService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public class BorrowControllerTest {
    @InjectMocks
    BorrowController borrowController;

    @Mock
    BorrowService borrowService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBeAbleToBorrowABook() {
        Borrow expectedBorrow = new Borrow(1, new User(), new Book(), LocalDate.now(), LocalDate.now().plusDays(15));

        Mockito.when(borrowService.borrowBook(0, 0)).thenReturn(expectedBorrow);

        ResponseEntity<Borrow> response = borrowController.borrowBook(expectedBorrow);

        Assertions.assertEquals(expectedBorrow, response.getBody());
    }
}
