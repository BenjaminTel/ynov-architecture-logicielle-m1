package com.ubik.formation.bookservice.converter;

import com.ubik.formation.bookservice.dto.BookDto;
import com.ubik.formation.bookservice.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public static BookDto toDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setCategory(book.getCategory());
        bookDto.setAvailable(book.isAvailable());

        return bookDto;
    }

    public static Book toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        Book book = new Book();
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .category(bookDto.getCategory())
                .available(bookDto.isAvailable())
                .build();
    }
}
