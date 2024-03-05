package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/author/{authorId}/book")
    public ResponseEntity<BookDto> createBook(@PathVariable(value = "authorId") int authorId,
                                              @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(authorId, bookDto), HttpStatus.CREATED);
    }

    @GetMapping("/author/{authorId}/books")
    public ResponseEntity<List<BookDto>> getBookByAuthorId(@PathVariable(value = "authorId") int authorId) {
        return new ResponseEntity<>(bookService.getBookByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping("/author/{authorId}/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(value = "authorId") int authorId,
                                               @PathVariable(value = "id") int bookId) {
        BookDto bookDto = bookService.getBookById(authorId, bookId);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PutMapping("/author/{authorId}/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable(value = "authorId") int authorId,
                                              @PathVariable(value = "id") int bookId,
                                              @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(authorId, bookId, bookDto);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/author/{authorId}/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "authorId") int authorId,
                                             @PathVariable(value = "id") int bookId) {
        bookService.deleteBook(authorId, bookId);
        return new ResponseEntity<>("Book deleted succesfully", HttpStatus.OK);
    }
}
