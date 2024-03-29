package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
import com.library.management.entity.User;
import com.library.management.exception.LimitExceededException;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    @Value("${borrow.limit}")
    int borrowingLimit;

    @Value("${default.return.time}")
    int defaultReturnTime;

    public Borrow borrowBook(long userId, long bookId) throws LimitExceededException, ResourceNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("book", bookId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", userId));


        if (!isUserEligibleToBorrow(userId, borrowingLimit, book))
            throw new LimitExceededException(user, book);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        Borrow borrow = new Borrow(0, user, book, LocalDate.now(), LocalDate.now().plusDays(defaultReturnTime));

        return borrowRepository.save(borrow);
    }

    private boolean isUserEligibleToBorrow(long userId, int limit, Book book) {
        List<Borrow> borrowList = borrowRepository.findByUserId(userId);
        if (borrowList.size() > limit) {
            return false;
        }
        if (book.getQuantity() <= 0) {
            return false;
        }
        return borrowList.stream().noneMatch(b -> b.getBook().equals(book));
    }

    public void returnBook(long bookId, long userId) {
        Borrow borrow = borrowRepository.findByBookIdAndUserId(bookId, userId).orElseThrow(() -> new ResourceNotFoundException("book", bookId));

        borrow.getBook().setQuantity(borrow.getBook().getQuantity() + 1);
        bookRepository.save(borrow.getBook());

        borrowRepository.deleteById(borrow.getId());
    }
}
