package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean borrowABook(Book book) {
        return false;
    }
}
