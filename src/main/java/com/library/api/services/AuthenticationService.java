package com.library.api.services;

import com.library.api.dto.AuthResReqDTO;
import com.library.api.models.UserEntity;
import com.library.api.repository.UserRepository;
import com.library.api.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResReqDTO signUp(AuthResReqDTO registrationRequest) {
        AuthResReqDTO response = new AuthResReqDTO();
        try {
            UserEntity reqUser = new UserEntity();
            reqUser.setUsername(registrationRequest.getUsername());
            reqUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            reqUser.setRole(registrationRequest.getRole());
            UserEntity newUser = userRepository.save(reqUser);
            if (newUser != null) {
                response.setUser(newUser);
                response.setMessage("User saved Successfully");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public AuthResReqDTO signIn(AuthResReqDTO signInRequest) {
        AuthResReqDTO response = new AuthResReqDTO();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                    signInRequest.getPassword()));
            var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow();
            System.out.println("USER IS: " + user);
            var accessToken = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("15min");
            response.setMessage("Successfully Signed In");
        } catch (Exception e) {
            response.setStatusCode(401);
            response.setError(e.getMessage());
        }
        return response;
    }


    public AuthResReqDTO refreshToken(AuthResReqDTO refreshTokenRequest) {
        AuthResReqDTO response = new AuthResReqDTO();
        String username = jwtUtils.extractUsername(refreshTokenRequest.getAccessToken());
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getAccessToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setAccessToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getAccessToken());
            response.setExpirationTime("15min");
            response.setMessage("Successfully Refreshed Token");
        } else response.setStatusCode(500);
        return response;
    }
}


