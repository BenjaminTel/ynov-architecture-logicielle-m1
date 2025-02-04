package com.ubik.formation.borrowingservice.service;

import com.ubik.formation.borrowingservice.dto.BorrowingDto;

import java.util.List;

public interface BorrowingService {
    BorrowingDto borrowBook(BorrowingDto borrowingDto);
    void returnBook(Long borrowingId);
    List<BorrowingDto> getUserBorrowings(Long userId);
    int countByUserId(Long userId);
    void deleteByBookId(Long bookId);
    void deleteByUserId(Long userId);
    List<BorrowingDto> getAllBorrowings();

}
