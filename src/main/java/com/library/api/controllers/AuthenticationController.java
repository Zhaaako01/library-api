package com.library.api.controllers;

import com.library.api.dto.AuthResReqDTO;
//import com.library.api.repository.RoleRepository;
import com.library.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
            }else {
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


//    @PostMapping("login")
//    public ResponseEntity<AuthenticationRespDTO> login(@RequestBody LoginDto loginDto) {
//        // there has to be validation and verification, I think
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
//                        loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        return new ResponseEntity<>(new AuthenticationRespDTO(token), HttpStatus.OK);
//    }
//
//    @PostMapping("register")
//    public ResponseEntity<String> register(@RequestBody RegisterDto registerDTO) {
//        if (userRepository.existsByUsername(registerDTO.getUsername())) {
//            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
//        }
//
//        UserEntity user = new UserEntity();
//        user.setUsername(registerDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
//
//        Role roles = roleRepository.findByName("USER").get();
//        user.setRoles(Collections.singletonList(roles));
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
//    }
}
