package com.ubik.formation.bookservice.service;

import com.ubik.formation.bookservice.converter.BookConverter;
import com.ubik.formation.bookservice.dto.BookDto;
import com.ubik.formation.bookservice.entity.Book;
import com.ubik.formation.bookservice.exception.ResourceNotFoundException;
import com.ubik.formation.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final KafkaProducerService kafkaProducerService;

    public BookServiceImpl(BookRepository bookRepository, KafkaProducerService kafkaProducerService) {
        this.bookRepository = bookRepository;
        this.kafkaProducerService = kafkaProducerService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(BookConverter::toDTO);
    }

    @Override
    @Transactional
    public BookDto saveBook(BookDto bookDTO) {
        return BookConverter.toDTO(bookRepository.save(BookConverter.toEntity(bookDTO)));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            kafkaProducerService.sendBookDeletedMessage(id);
        }
    }

    @Override
    @Transactional
    public void borrowBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.isAvailable()) {
                book.setAvailable(false);
                bookRepository.save(book);
            } else {
                throw new IllegalStateException("Le livre est déjà emprunté.");
            }
        } else {
            throw new ResourceNotFoundException("Le livre avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    @Transactional
    public void returnBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!book.isAvailable()) {
                book.setAvailable(true);
                bookRepository.save(book);
            } else {
                throw new IllegalStateException("Le livre n'a pas été emprunté.");
            }
        } else {
            throw new ResourceNotFoundException("Le livre avec l'ID " + id + " n'existe pas.");
        }
    }
}
