package com.library.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorResponse {
    private List<AuthorDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
