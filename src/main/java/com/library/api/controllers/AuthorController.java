package com.library.api.controllers;

import com.library.api.dto.AuthorDto;
import com.library.api.models.Author;
import com.library.api.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("author")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @GetMapping("author/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return new Author(id, "Author 1", "Surname 1");
    }

    @PostMapping("author/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @PutMapping("author/{id}/update")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable("id") int authorId) {
        System.out.println(author.getName());
        System.out.println(author.getSurname());
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("author/{id}/delete")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") int authorId) {
        System.out.println(authorId);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
