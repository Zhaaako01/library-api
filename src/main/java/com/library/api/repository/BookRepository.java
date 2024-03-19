package com.library.api.repository;

import com.library.api.models.Author;
import com.library.api.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorId(int authorId);


    @Query(value = "SELECT b from Book b")
    List<Book> getAllBooksFromMySQL();

    @Query(value = "SELECT b FROM Book b where b.title=:value")
    Book findBookByTitle(@Param("value") String titleS);

    @Query(nativeQuery = true, value = "SELECT b.* FROM book b WHERE b.pages <:pages")
    List<Book> findBookByPagesLessThan(int pages);

}
