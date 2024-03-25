package com.library.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.api.models.UserEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResReqDTO {
    private int statusCode;
    private String error;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private String role;
    private String password;
    private UserEntity User;
}
