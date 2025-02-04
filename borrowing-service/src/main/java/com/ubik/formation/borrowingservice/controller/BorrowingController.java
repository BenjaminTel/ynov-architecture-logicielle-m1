package com.ubik.formation.borrowingservice.controller;


import com.ubik.formation.borrowingservice.dto.BorrowingDto;
import com.ubik.formation.borrowingservice.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowingDto>> getAllBorrowings() {
        List<BorrowingDto> borrowings = borrowingService.getAllBorrowings();
        return ResponseEntity.ok(borrowings);
    }

    @PostMapping()
    public ResponseEntity<BorrowingDto> borrowBook(@RequestBody BorrowingDto borrowingDto) {
        BorrowingDto createdBorrowing = borrowingService.borrowBook(borrowingDto);
        return ResponseEntity.ok(createdBorrowing);
    }

    @PutMapping("/return/{borrowingId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long borrowingId) {
        borrowingService.returnBook(borrowingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowingDto>> getUserBorrowings(@PathVariable Long userId) {
        List<BorrowingDto> borrowings = borrowingService.getUserBorrowings(userId);
        return ResponseEntity.ok(borrowings);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getUserBorrowingCount(@PathVariable Long userId) {
        int count = borrowingService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }
}


