package com.codewithomarm.rosterup.security.v1.auth.service.userdetails;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class RosterupUserDetailsImpl implements UserDetails {

    private final String username;
    @JsonIgnore
    private final String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final Set<RosterupRole> rosterupRoles;

    public RosterupUserDetailsImpl(RosterupUser rosterupUser) {
        this.username = rosterupUser.getUsername();
        this.password = rosterupUser.getPassword();
        this.accountNonExpired = rosterupUser.getAccountNonExpired();
        this.accountNonLocked = rosterupUser.getAccountNonLocked();
        this.credentialsNonExpired = rosterupUser.getCredentialsNonExpired();
        this.enabled = rosterupUser.getEnabled();
        this.rosterupRoles = rosterupUser.getRosterupRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rosterupRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
