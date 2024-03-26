package com.library.management.controller;

import com.library.management.entity.Borrow;
import com.library.management.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BorrowController {
    private final BorrowService borrowService;
    @PostMapping("/user/{userId}/borrow/{bookId}")
    public ResponseEntity<Borrow> borrowBook(@PathVariable long userId, @PathVariable long bookId) {
        return ResponseEntity.ok(borrowService.borrowBook(userId, bookId));
    }

    @DeleteMapping("/user/{userId}/return/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable long userId, @PathVariable long bookId) {
        borrowService.returnBook(bookId, userId);
        return ResponseEntity.ok().build();
    }
}
