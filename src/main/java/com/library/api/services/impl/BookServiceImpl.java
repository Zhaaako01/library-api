package com.library.api.services.impl;

import com.library.api.dto.BookDto;
import com.library.api.exceptions.AuthorNotFoundException;
import com.library.api.exceptions.BookNotFoundException;
import com.library.api.models.Author;
import com.library.api.models.Book;
import com.library.api.repository.AuthorRepository;
import com.library.api.repository.BookRepository;
import com.library.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDto createBook(int authorId, BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new AuthorNotFoundException("Author with associated book not found"));
        book.setAuthor(author);
        Book newBook = bookRepository.save(book);
        return mapToDto(newBook);
    }



    @Override
    public List<BookDto> getBookByAuthorId(int id) {
        List<Book> books = bookRepository.findByAuthorId(id);
        return books.stream().map(book -> mapToDto(book)).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(int authorId, int bookId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new AuthorNotFoundException("Author with associated book not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book with associated author not found"));

//        объекты лучше сравнивать через equals?
//        Integer bookGetAuthorGetId = book.getAuthor().getId();
//        Integer authorGetId = author.getId();
//        if (bookGetAuthorGetId.equals(authorGetId)){
//            return mapToDto(book);
//        }else throw new BookNotFoundException("This book does not belong to a author");

        if (book.getAuthor().getId() != author.getId()) {
            throw new BookNotFoundException("This book does not belong to a author");
        }
        return mapToDto(book);
    }

    @Override
    public BookDto updateBook(int authorId, int bookId, BookDto bookDto) {

        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new AuthorNotFoundException("Author with associated book not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book with associated author not found"));

        if (book.getAuthor().getId() != author.getId()) {
            throw new BookNotFoundException("This book does not belong to a author");
        }

        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());

        Book updatedBook = bookRepository.save(book);

        return mapToDto(updatedBook);
    }


    @Override
    public void deleteBook(int authorId, int bookId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new AuthorNotFoundException("Author with associated book not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book with associated author not found"));

        if (book.getAuthor().getId() != author.getId()) {
            throw new BookNotFoundException("This book does not belong to a author");
        }

        bookRepository.delete(book);
    }

    private BookDto mapToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setPages(book.getPages());
        return bookDto;
    }

    private Book mapToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());
        return book;
    }



    // --------- WEBSOCKET ---------

    @Override
    public BookDto createBookWS(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book newBook = bookRepository.save(book);
        return mapToDto(newBook);
    }

    // Работает в jxy.me но не в Postman
    @Override
    public void deleteBookWS(BookDto bookDto){
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(() ->
                new BookNotFoundException("Book with associated author not found"));
        bookRepository.delete(book);
    }

    // Работает в Postman, но не в jxy.me
//    @Override
//    public void deleteBookWS(int bookId){
//        Book book = bookRepository.findById(bookId).orElseThrow(() ->
//                new BookNotFoundException("Book with associated author not found"));
//        bookRepository.delete(book);
//    }

    @Override
    public BookDto updateBookWS(BookDto bookDto) {

        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(() ->
                new BookNotFoundException("Book with associated author not found"));

        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());

        Book updatedBook = bookRepository.save(book);

        return mapToDto(updatedBook);
    }
}
