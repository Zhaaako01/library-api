package com.library.api.repository;

import com.library.api.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(nativeQuery = true, value = "SELECT a.name, a.surname, title, pages from book b \n" +
            "left join author a on b.author_id = a.id\n" +
            "where a.id =:authorId")
    List<Author> getAuthorWithHisBooks(int authorId);
}
