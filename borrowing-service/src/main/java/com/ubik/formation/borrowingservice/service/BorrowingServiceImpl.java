package com.ubik.formation.borrowingservice.service;

import com.ubik.formation.borrowingservice.component.RestBook;
import com.ubik.formation.borrowingservice.component.RestUser;
import com.ubik.formation.borrowingservice.converter.BorrowingConverter;
import com.ubik.formation.borrowingservice.dto.BookDto;
import com.ubik.formation.borrowingservice.dto.BorrowingDto;
import com.ubik.formation.borrowingservice.dto.UserDto;
import com.ubik.formation.borrowingservice.entity.Borrowing;
import com.ubik.formation.borrowingservice.exception.ResourceNotFoundException;
import com.ubik.formation.borrowingservice.repository.BorrowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final KafkaProducerService kafkaProducerService;
    private final RestUser restUserClient;
    private final RestBook restBookClient;

    public BorrowingServiceImpl(
            BorrowingRepository borrowingRepository,
            RestUser restUserClient,
            RestBook restBookClient,
            KafkaProducerService kafkaProducerService) {
        this.borrowingRepository = borrowingRepository;
        this.restUserClient = restUserClient;
        this.restBookClient = restBookClient;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @Transactional
    public BorrowingDto borrowBook(BorrowingDto borrowingDto) {
        UserDto user = restUserClient.getUserById(borrowingDto.getUserId());
        if (user == null || user.isLocked()) {
            throw new IllegalStateException("User is locked or does not exist.");
        }

        BookDto book = restBookClient.getBookById(borrowingDto.getBookId());
        if (book == null || !book.isAvailable()) {
            throw new IllegalStateException("Book is not available.");
        }

        BorrowingDto borrowing = BorrowingConverter.toDto(borrowingRepository.save(BorrowingConverter.toEntity(borrowingDto)));

        kafkaProducerService.sendBookBorrowedMessage(book.getId());

        if (countByUserId(user.getId()) >= user.getNombreMaxEmprunt()) {
            kafkaProducerService.sendLockUserMessage(user.getId());
        }
        return borrowing;
    }

    @Override
    @Transactional
    public void returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing not found"));

        borrowing.setReturnDate(LocalDate.now());
        borrowingRepository.save(borrowing);
        restBookClient.returnBookById(borrowingId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingDto> getUserBorrowings(Long userId) {
        return borrowingRepository.findByUserId(userId).stream()
                .map(BorrowingConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int countByUserId(Long userId) {
        return borrowingRepository.countByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteByBookId(Long bookId) {
        if (borrowingRepository.existsByBookId(bookId)) {
            borrowingRepository.deleteByBookId(bookId);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        if (borrowingRepository.existsByUserId(userId)) {
            borrowingRepository.deleteByUserId(userId);
        }
    }

    @Override
    public List<BorrowingDto> getAllBorrowings() {
        return borrowingRepository.findAll().stream().map(BorrowingConverter::toDto).collect(Collectors.toList());
    }


}
