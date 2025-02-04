package com.ubik.formation.bookservice.service;

import com.ubik.formation.bookservice.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks();
    Optional<BookDto> getBookById(Long id);
    BookDto saveBook(BookDto bookDTO);
    void deleteBook(Long id);
    void borrowBook(Long id);
    void returnBook(Long id);
}
