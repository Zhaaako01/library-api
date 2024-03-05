package com.library.api.services;

import com.library.api.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthors();
}
