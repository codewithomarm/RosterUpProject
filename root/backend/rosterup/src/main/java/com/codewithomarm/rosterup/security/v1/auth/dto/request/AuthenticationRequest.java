package com.codewithomarm.rosterup.security.v1.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {

    @NotBlank(message = "Username field is required")
    private String username;

    @NotBlank(message = "Password field is required")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
