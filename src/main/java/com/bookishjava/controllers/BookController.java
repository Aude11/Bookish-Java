package com.bookishjava.controllers;

import com.bookishjava.models.database.Book;
import com.bookishjava.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    List<Book> getBooks() {
        return repository.findAll();
    }

    @PostMapping("/new-book")
    void createNewBook() {
        // implement
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable("id") Long id) {
        Optional<Book> bookRequestedByID = repository.findById(id);
        if (!bookRequestedByID.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(bookRequestedByID.get()));
    }

    @GetMapping("books/title/{title}")
    public ResponseEntity<Optional<Book>>getBookByTitle(@PathVariable("title") String title) {
        // impelment id
        Optional <Book> bookRequestedByTitle = Optional.ofNullable(repository.findByTitle(title));
        if (!bookRequestedByTitle.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Optional.<Book>of(bookRequestedByTitle.get()));
    }

    @GetMapping("books/author/{author}")
    List<Book> getBookByAuthor(@PathVariable String author) {
        // impelment id
        return repository.findByAuthorName(author);
    }

}
