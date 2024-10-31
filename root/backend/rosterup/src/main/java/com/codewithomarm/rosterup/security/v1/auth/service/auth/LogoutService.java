package com.codewithomarm.rosterup.security.v1.auth.service.auth;

import com.codewithomarm.rosterup.security.v1.auth.jwt.JwtService;
import com.codewithomarm.rosterup.security.v1.auth.repository.TokenRepository;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    
    private final RosterupUserRepository rosterupUserRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public LogoutService(RosterupUserRepository rosterupUserRepository, TokenRepository tokenRepository, JwtService jwtService) {
        this.rosterupUserRepository = rosterupUserRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            RosterupUser user = rosterupUserRepository.findByUsername(jwtService.extractUsername(token)).orElseThrow();
            revokeAllUserTokens(user);
        }
        SecurityContextHolder.clearContext();
    }

    private void revokeAllUserTokens(RosterupUser user) {
        AuthenticationService.revokeAllUserTokens(user, tokenRepository);
    }
}
