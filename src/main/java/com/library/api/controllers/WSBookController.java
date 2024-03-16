package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.services.BookService;
import com.library.api.services.WebSocketNotificationService;
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

    private WebSocketNotificationService webSocketNotificationService;

    @Autowired
    public WSBookController(BookService bookService, WebSocketNotificationService webSocketNotificationService) {
        this.bookService = bookService;
        this.webSocketNotificationService = webSocketNotificationService;
    }


    @MessageMapping("/book.createBook")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBookDto = bookService.createBook(bookDto);
//        webSocketNotificationService.notify(createdBookDto, "/topic/book/notifications");
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
    }


    @MessageMapping("/book.updateBook")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(bookDto);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


    @MessageMapping("/book.deleteBook")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> deleteBook(@RequestBody BookDto bookDto) {
        bookService.deleteBook(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
