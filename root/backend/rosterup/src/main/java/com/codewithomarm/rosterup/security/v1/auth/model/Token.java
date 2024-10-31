package com.codewithomarm.rosterup.security.v1.auth.model;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import jakarta.persistence.*;

@Entity
@Table(name = "tokens", schema = "auth")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    private ETokenType tokenType = ETokenType.BEARER;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private RosterupUser rosterupUser;

    public Token() {
    }

    public Token(String token, RosterupUser rosterupUser) {
        this.token = token;
        this.rosterupUser = rosterupUser;
        this.revoked = false;
        this.expired = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ETokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(ETokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public RosterupUser getRosterupUser() {
        return rosterupUser;
    }

    public void setRosterupUser(RosterupUser rosterupUser) {
        this.rosterupUser = rosterupUser;
    }
}
