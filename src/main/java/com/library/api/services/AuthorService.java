package com.library.api.services;

import com.library.api.dto.AuthorDto;
import com.library.api.dto.AuthorResponse;
import com.library.api.models.Author;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);

    AuthorResponse getAllAuthors(int pageNo, int pageSize);

    AuthorDto getAuthorById(int id);

    AuthorDto updateAuthor(AuthorDto authorDto, int id);

    void deleteAuthorById(int id);

    List<Author> getAuthorAndAllHisBooks(int id);
}
