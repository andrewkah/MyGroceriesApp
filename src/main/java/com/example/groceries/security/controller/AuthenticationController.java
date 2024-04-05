package com.example.groceries.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.dto.UserPasswordReset;
import com.example.groceries.security.service.AuthService;

import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        try {
            JwtAuthResponse auth = authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest request) {
        try {
            JwtAuthResponse auth = authService.signin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            JwtAuthResponse auth = authService.refreshToken(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public String helloUser() {
        return "Hello User";
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserPasswordReset user) throws UnsupportedEncodingException, MessagingException {
        try {
            String auth = authService.passwordResetToken(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserPasswordReset request) {
        try {
            String auth = authService.resetPassword(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
