package com.bookishjava.controllers;

import com.bookishjava.models.database.Book;
import com.bookishjava.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/books")
    List<Book> getBooks() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "authorName"));
    }

    @PostMapping(path = "/newbook",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createNewBook(@RequestBody Book book)
    {
        Long idTocheck;
        idTocheck = book.getId();
        if (idTocheck != null) {
            Optional<Book> bookByID = Optional.of(repository.getReferenceById(idTocheck));
            if (bookByID.isPresent()) {
                return ResponseEntity.status(HttpStatus.IM_USED).build();
            }
        }
        Book newBook = repository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PutMapping(path = "edit-book/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book)
    {
        //Optional<Book> bookRequestdById = Optional.of(repository.getReferenceById(id));
        boolean isExist = repository.existsById(id);
        /*if (!bookRequestdById.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }*/
        if (isExist == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //Book bookToUpdate = repository.getReferenceById(id);
        Book bookToUpdate = repository.findById(id).get();
        String newTitle = book.getTitle();
        String newAuthor = book.getAuthor();
        LocalDate newDate = book.getPublishedDate();
        bookToUpdate.setTitle(newTitle);
        bookToUpdate.setAuthor(newAuthor);
        bookToUpdate.setPublishedDate(newDate);
        repository.save(bookToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(bookToUpdate);

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

    @GetMapping("books/count")
    public int getCountAuthor() {
        String authorToSearch = "Margaret Atwood";
        return (int) repository.findByAuthorName(authorToSearch).stream().count();
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
