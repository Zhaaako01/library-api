package com.library.api.services;

import com.library.api.dto.AuthorDto;
import com.library.api.dto.BookDto;
import com.library.api.models.Book;

import java.util.List;

public interface BookService {
//    BookDto createBook(int authorId, BookDto bookDto);

    BookDto createBook(BookDto bookDto);

    List<BookDto> getBookByAuthorId(int id);

    BookDto getBookById(int authorId, int bookId);

    BookDto updateBook(BookDto bookDto);

//    BookDto updateBook(int authorId, int bookId, BookDto bookDto);

//    void deleteBook(int authorId, int bookId);

    void deleteBook(BookDto bookDto);

    List<BookDto> getAllBooksQUERY();

    BookDto getBookByTitle(String title);

    List<BookDto> getAllBooksPagesLessThan(int pages);
}
