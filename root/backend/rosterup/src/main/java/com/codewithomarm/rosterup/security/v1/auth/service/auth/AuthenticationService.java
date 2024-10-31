package com.codewithomarm.rosterup.security.v1.auth.service.auth;

import com.codewithomarm.rosterup.security.v1.auth.dto.request.AuthenticationRequest;
import com.codewithomarm.rosterup.security.v1.auth.dto.response.AuthenticationResponse;
import com.codewithomarm.rosterup.security.v1.auth.jwt.JwtService;
import com.codewithomarm.rosterup.security.v1.auth.model.ETokenType;
import com.codewithomarm.rosterup.security.v1.auth.model.Token;
import com.codewithomarm.rosterup.security.v1.auth.repository.TokenRepository;
import com.codewithomarm.rosterup.security.v1.auth.service.userdetails.RosterupUserDetailsImpl;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthenticationService {

    private final RosterupUserRepository rosterupUserRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(RosterupUserRepository rosterupUserRepository, TokenRepository tokenRepository,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.rosterupUserRepository = rosterupUserRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        RosterupUser user = rosterupUserRepository.findByUsername(request.getUsername()).orElseThrow();
        RosterupUserDetailsImpl userDetails = new RosterupUserDetailsImpl(user);
        String jwtToken = jwtService.generateToken(userDetails);
        String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, jwtRefreshToken);
    }

    private void revokeAllUserTokens(RosterupUser user) {
        revokeAllUserTokens(user, tokenRepository);
    }

    static void revokeAllUserTokens(RosterupUser user, TokenRepository tokenRepository) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(RosterupUser user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setTokenType(ETokenType.BEARER);
        token.setRevoked(false);
        token.setExpired(false);
        token.setRosterupUser(user);
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader==null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            RosterupUser user = rosterupUserRepository.findByUsername(username).orElseThrow();
            RosterupUserDetailsImpl userDetails = new RosterupUserDetailsImpl(user);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }
}
