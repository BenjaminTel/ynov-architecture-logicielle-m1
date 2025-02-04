package com.ubik.formation.bookservice.repository;

import com.ubik.formation.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
