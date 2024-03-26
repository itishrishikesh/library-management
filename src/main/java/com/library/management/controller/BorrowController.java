package com.library.management.controller;

import com.library.management.entity.Borrow;
import com.library.management.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestBody Borrow borrow) {
        return ResponseEntity.ok(borrowService.borrowBook(borrow.getUser().getId(), borrow.getBook().getId()));
    }

}
