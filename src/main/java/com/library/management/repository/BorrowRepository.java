package com.library.management.repository;

import com.library.management.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUserId(long userId);
    Optional<Borrow> findByBookIdAndUserId(long bookId, long userId);
}
