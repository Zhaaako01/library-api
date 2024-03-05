package com.library.api.services;

import com.library.api.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(int authorId, BookDto bookDto);

    List<BookDto> getBookByAuthorId(int id);

    BookDto getBookById(int authorId, int bookId);

    BookDto updateBook(int authorId, int bookId, BookDto bookDto);

    void deleteBook(int authorId, int bookId);
}
