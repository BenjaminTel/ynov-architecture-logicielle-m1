package com.ubik.formation.borrowingservice.repository;

import com.ubik.formation.borrowingservice.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByUserId(Long userId);
    int countByUserId(Long userId);
    void deleteByUserId(Long userId);
    void deleteByBookId(Long bookId);

    boolean existsByBookId(Long bookId);

    boolean existsByUserId(Long userId);
}
