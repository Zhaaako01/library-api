package com.library.api.services.impl;

import com.library.api.dto.AuthorDto;
import com.library.api.models.Author;
import com.library.api.repository.AuthorRepository;
import com.library.api.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());

        Author newAuthor = authorRepository.save(author);

        AuthorDto authorResponse = new AuthorDto();
        authorResponse.setId(newAuthor.getId());
        authorResponse.setName(newAuthor.getName());
        authorResponse.setSurname(newAuthor.getSurname());

        return authorResponse;
    }
}
