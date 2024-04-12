package com.library.api.controllers;

import com.library.api.dto.AuthResReqDTO;
import com.library.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResReqDTO> signUp(@RequestBody AuthResReqDTO signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResReqDTO> signIn(@RequestBody AuthResReqDTO signInRequest) {
        try {
            AuthResReqDTO signInResult = authService.signIn(signInRequest);
            if (signInResult != null && signInResult.getStatusCode() == 200) {
                return ResponseEntity.status(HttpStatus.OK).body(signInResult);
            } else {
                return ResponseEntity.status(signInResult.getStatusCode()).body(signInResult);
            }
        } catch (Exception e) {
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResReqDTO> refreshToken(@RequestBody AuthResReqDTO refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
