package com.library.api.services;

import com.library.api.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    List<BookDto> getBookByAuthorId(int id);

    BookDto getBookById(int authorId, int bookId);

    BookDto updateBook(BookDto bookDto);

    void deleteBook(BookDto bookDto);

    List<BookDto> getAllBooksQUERY();

    BookDto getBookByTitle(String title);

    List<BookDto> getAllBooksPagesLessThan(int pages);
}
