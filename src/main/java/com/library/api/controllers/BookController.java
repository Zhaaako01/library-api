package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.models.Book;
import com.library.api.services.BookService;
import com.library.api.services.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lib")
//@PreAuthorize("hasRole('ADMIN')")
public class BookController {
    private BookService bookService;
    private WebSocketNotificationService webSocketNotificationService;


    @Autowired
    public BookController(BookService bookService, WebSocketNotificationService webSocketNotificationService) {
        this.bookService = bookService;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    @PostMapping("/admin/author/{authorId}/book")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<BookDto> createBook(@PathVariable(value = "authorId") int authorId,
                                              @RequestBody BookDto bookDto) {
        bookDto.setAuthor_id(Optional.of(authorId));
        BookDto createdBookDto = bookService.createBook(bookDto);
        createdBookDto.setAuthor_id(bookDto.getAuthor_id());
        webSocketNotificationService.notify(createdBookDto, "/topic/book/notifications");
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
    }

    @GetMapping("public/author/{authorId}/books")
    public ResponseEntity<List<BookDto>> getBookByAuthorId(@PathVariable(value = "authorId") int authorId) {
        return new ResponseEntity<>(bookService.getBookByAuthorId(authorId), HttpStatus.OK);
    }

    //  -----          QUERY          -----
    // I did not make it REST, I just checked Custom SQL Queries in work

    @GetMapping("/books_sql")
    public ResponseEntity<List<BookDto>> getAllBooksSQL() {
        return new ResponseEntity<>(bookService.getAllBooksQUERY(), HttpStatus.OK);
    }


    @GetMapping("/books_sql/{bookTitle}")
    public ResponseEntity<BookDto> getBooksByTitleSQL(@PathVariable(value = "bookTitle") String bookTitle) {
        return new ResponseEntity<>(bookService.getBookByTitle(bookTitle), HttpStatus.OK);
    }

    @GetMapping("/books/sql/{pages}")
    public ResponseEntity<List<BookDto>> getAllBooksPagesLessThan(@PathVariable(value = "pages") int pages) {
        return new ResponseEntity<>(bookService.getAllBooksPagesLessThan(pages), HttpStatus.OK);
    }


    //  -----          QUERY          -----


    @GetMapping("public/author/{authorId}/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(value = "authorId") int authorId,
                                               @PathVariable(value = "id") int bookId) {
        BookDto bookDto = bookService.getBookById(authorId, bookId);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PutMapping("admin/author/{authorId}/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable(value = "authorId") int authorId,
                                              @PathVariable(value = "id") int bookId,
                                              @RequestBody BookDto bookDto) {
        bookDto.setId(bookId);
        bookDto.setAuthor_id(Optional.of(authorId));
        BookDto updatedBook = bookService.updateBook(bookDto);
        webSocketNotificationService.notify(updatedBook, "/topic/book/notifications");
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("admin/author/{authorId}/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "authorId") int authorId,
                                             @PathVariable(value = "id") int bookId,
                                             BookDto bookDto) {
        bookDto.setId(bookId);
        bookDto.setAuthor_id(Optional.of(authorId));
        bookService.deleteBook(bookDto);
        webSocketNotificationService.notify(bookDto, "/topic/book/notifications");
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }
}
