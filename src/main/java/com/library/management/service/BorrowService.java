package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
import com.library.management.entity.User;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    public Borrow borrowBook(long userId, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("book", bookId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", userId));
        if (user.getBorrowedBooks().size() > 2) {
            throw new ResourceNotFoundException("user", userId);
        }
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        Borrow borrow = new Borrow(0, user, book, LocalDate.now(), LocalDate.now().plusDays(15));
        return borrowRepository.save(borrow);
    }


}
