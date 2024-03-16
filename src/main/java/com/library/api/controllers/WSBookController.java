package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class WSBookController {

    private BookService bookService;

    @Autowired
    public WSBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @MessageMapping("/book.createBook")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBookDto = bookService.createBook(bookDto);
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
    }

    @MessageMapping("/book.updateBook")
    @SendTo("/topic/book/notifications")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(bookDto);
        return updatedBook;
    }

    @MessageMapping("/book.deleteBook")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> deleteBook(@RequestBody BookDto bookDto) {
        bookService.deleteBook(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
