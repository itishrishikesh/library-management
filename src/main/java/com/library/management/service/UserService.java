package com.library.management.service;

import com.library.management.entity.User;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user"));
    }
}
