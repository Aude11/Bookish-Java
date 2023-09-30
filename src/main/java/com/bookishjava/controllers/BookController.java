package com.bookishjava.controllers;

import com.bookishjava.models.database.Book;
import com.bookishjava.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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


    @PostMapping("/new-book/")
    public ResponseEntity<Book> createNewBook(@RequestBody Book book)
    {
        Optional<Book> newBook = Optional.of(repository.save(book));
        if (!newBook .isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Book newBook2 =repository.save(book);
        ResponseEntity<Book> body = ResponseEntity.status(HttpStatus.CREATED).body(newBook2);
        return body;

    }

   /* @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book bookToUpdate)
    {
        Optional<Book> bookToUpdateRequestedByID = Optional.of(repository.getReferenceById(id));
        if (!bookToUpdateRequestedByID.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        bookToUpdate.setAuthor("NewAuthor");
        bookToUpdate.setTitle("NewTitle");
        Book udaptedBook = repository.save(bookToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(udaptedBook);
    } */

    @PutMapping("/edit-book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id)
    {
        Optional<Book> bookToUpdateRequestedByID = Optional.of(repository.getReferenceById(id));
        if (!bookToUpdateRequestedByID.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Book bookToUpdate = repository.getReferenceById(id);
        bookToUpdate.setAuthor("NewAuthor");
        bookToUpdate.setTitle("NewTitle");
        Book udaptedBook = repository.save(bookToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(udaptedBook);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") Long id)
    {
        Optional<Book> bookToDeleteRequestedByID = repository.findById(id);
        if (!bookToDeleteRequestedByID.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        return repository.findByAuthorName(author);
    }

}
