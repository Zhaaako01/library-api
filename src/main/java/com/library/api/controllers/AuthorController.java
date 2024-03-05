package com.library.api.controllers;

import com.library.api.dto.AuthorDto;
import com.library.api.dto.AuthorResponse;
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
    public ResponseEntity<AuthorResponse> getAllAuthors(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(authorService.getAllAuthors(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("author/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable int id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping("author/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @PutMapping("author/{id}/update")
    public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto, @PathVariable("id") int authorId) {
        AuthorDto response = authorService.updateAuthor(authorDto, authorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("author/{id}/delete")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") int authorId) {
        authorService.deleteAuthorById(authorId);
        return new ResponseEntity<>("Author deleted", HttpStatus.OK);
    }
}
