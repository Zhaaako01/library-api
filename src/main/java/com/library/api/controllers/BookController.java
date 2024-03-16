package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.services.BookService;
import com.library.api.services.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class BookController {
    private BookService bookService;
//    private WebSocketNotificationService webSocketNotificationService;


//    @Autowired
//    public BookController(BookService bookService, WebSocketNotificationService webSocketNotificationService) {
//        this.bookService = bookService;
//        this.webSocketNotificationService = webSocketNotificationService;
//    }

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/author/{authorId}/book")
    public ResponseEntity<BookDto> createBook(@PathVariable(value = "authorId") int authorId,
                                              @RequestBody BookDto bookDto) {
        bookDto.setAuthor_id(Optional.of(authorId));
        BookDto createdBookDto = bookService.createBook(bookDto);
        createdBookDto.setAuthor_id(bookDto.getAuthor_id());
//        webSocketNotificationService.notify(createdBookDto, "/topic/book/notifications");
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
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
        bookDto.setId(bookId);
        bookDto.setAuthor_id(Optional.of(authorId));
        BookDto updatedBook = bookService.updateBook(bookDto);
//        webSocketNotificationService.notify(updatedBook, "/topic/book/notifications");
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/author/{authorId}/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "authorId") int authorId,
                                             @PathVariable(value = "id") int bookId,
                                             BookDto bookDto) {
        bookDto.setId(bookId);
        bookDto.setAuthor_id(Optional.of(authorId));
        bookService.deleteBook(bookDto);
//        webSocketNotificationService.notify(bookDto, "/topic/book/notifications");
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }
}
