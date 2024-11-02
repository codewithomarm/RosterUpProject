package com.codewithomarm.rosterup.security.v1.auth.controller;

import com.codewithomarm.rosterup.security.v1.auth.dto.request.AuthenticationRequest;
import com.codewithomarm.rosterup.security.v1.auth.dto.response.AuthenticationResponse;
import com.codewithomarm.rosterup.security.v1.auth.service.auth.AuthenticationService;
import com.codewithomarm.rosterup.security.v1.auth.service.auth.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/roster-up/v1/auth")
public class AuthControllerV1 {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    public AuthControllerV1(AuthenticationService authenticationService, LogoutService logoutService) {
        this.authenticationService = authenticationService;
        this.logoutService = logoutService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
