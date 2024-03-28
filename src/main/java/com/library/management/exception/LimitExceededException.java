package com.library.management.exception;

import com.library.management.entity.Book;
import com.library.management.entity.User;
import lombok.Getter;

@Getter
public class LimitExceededException extends RuntimeException {
    User user;
    Book book;
    public LimitExceededException(User user, Book book) {
        super(String.format("User %s has reached the borrowing limit. Can't borrow %s book", user.getUsername(), book.getName()));
        this.book = book;
        this.user = user;
    }
}
