package com.library.api.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class BookDto {
    private int id;
    private String title;
    private int pages;
    private Optional<Integer> author_id;
}
