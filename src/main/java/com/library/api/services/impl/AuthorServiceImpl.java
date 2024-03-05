package com.library.api.services.impl;

import com.library.api.dto.AuthorDto;
import com.library.api.dto.AuthorResponse;
import com.library.api.exceptions.AuthorNotFoundException;
import com.library.api.models.Author;
import com.library.api.repository.AuthorRepository;
import com.library.api.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public AuthorResponse getAllAuthors(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Author> authors = authorRepository.findAll(pageable);
        List<Author> listOfAuthors = authors.getContent();

                                                         //map because it returns a new list
        List<AuthorDto> content = listOfAuthors.stream().map(auth -> mapToDto(auth)).collect(Collectors.toList());
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setContent(content);
        authorResponse.setPageNo(authors.getNumber());
        authorResponse.setPageSize(authors.getSize());
        authorResponse.setTotalElements(authors.getTotalElements());
        authorResponse.setTotalPages(authors.getTotalPages());
        authorResponse.setLast(authors.isLast());

        return authorResponse;
    }

    @Override
    public AuthorDto getAuthorById(int id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author could not be found"));
        return mapToDto(author);

    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, int id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author could not be updated"));
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        Author updatedAuthor = authorRepository.save(author);
        return mapToDto(updatedAuthor);
    }

    @Override
    public void deleteAuthorById(int id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author could not be deleted"));
        authorRepository.delete(author);
    }


    // Mapper
    private AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setSurname(author.getSurname());
        return authorDto;
    }

    private Author mapToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        return author;
    }
}
