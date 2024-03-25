package com.library.api.dto;

import lombok.Data;

@Data
public class AuthenticationRespDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthenticationRespDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
