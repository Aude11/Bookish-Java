package com.bookishjava.repositories;

import com.bookishjava.models.database.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
    List<Book> findByAuthorName(String authorName);
}
