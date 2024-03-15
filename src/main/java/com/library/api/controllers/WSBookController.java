package com.library.api.controllers;

import com.library.api.dto.BookDto;
import com.library.api.services.BookService;
import com.library.api.services.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/api/")
public class WSBookController {

    private BookService bookService;
    private WebSocketNotificationService webSocketNotificationService;

    @Autowired
    public WSBookController(BookService bookService, WebSocketNotificationService webSocketNotificationService) {
        this.bookService = bookService;
        this.webSocketNotificationService = webSocketNotificationService;
    }


    @MessageMapping("/book.createBook")
    @PostMapping("/book")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBookDto = bookService.createBookWS(bookDto);
        webSocketNotificationService.notify(createdBookDto, "/topic/book/notifications");
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
    }

    // хз как работать и куда писать id
    @MessageMapping("/book.updateBook")
    @PutMapping("/books/{id}")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBookWS(bookDto);
        webSocketNotificationService.notify(updatedBook, "/topic/book/notifications");
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

//         Работает в jxy.me но не в Postman
    @MessageMapping("/book.deleteBook")
    @DeleteMapping("/books/{id}")
    @SendTo("/topic/book/notifications")
    public ResponseEntity<BookDto> deleteBook(@RequestBody BookDto bookDto) {
        bookService.deleteBookWS(bookDto);
        webSocketNotificationService.notify(bookDto, "/topic/book/notifications");
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }


    // Работает в Postman, но не в jxy.me
//    @MessageMapping("/book.deleteBook")
//    @DeleteMapping("/books/{id}")
//    @SendTo("/topic/book/notifications")
//    public ResponseEntity<BookDto> deleteBook(@Payload BookDto bookDto,
//            @PathVariable(value = "id") int bookId) {
//        bookService.deleteBookWS(bookId);
//        webSocketNotificationService.notify(bookDto, "/topic/book/notifications");
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
}
