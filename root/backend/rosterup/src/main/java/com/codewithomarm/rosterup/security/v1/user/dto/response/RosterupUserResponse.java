package com.codewithomarm.rosterup.security.v1.user.dto.response;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;

import java.time.LocalDateTime;
import java.util.Set;

public class RosterupUserResponse {
    private Long id;
    private String username;
    private String email;
    private String accountNonExpired;
    private String accountNonLocked;
    private String credentialsNonExpired;
    private String enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RosterupRole> rosterupRoles;

    public RosterupUserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(String accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public String getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(String accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(String credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<RosterupRole> getRoles() {
        return rosterupRoles;
    }

    public void setRoles(Set<RosterupRole> rosterupRoles) {
        this.rosterupRoles = rosterupRoles;
    }
}
