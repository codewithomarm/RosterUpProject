package com.codewithomarm.rosterup.security.v1.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UpdateRosterupUserRequest {

    @NotBlank(message = "Username field is required")
    @Size(min = 3, max = 30, message = "Username max length must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Password field is required")
    @Size(min = 8, max = 30, message = "Password max length must be between 8 and 30 characters")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
    @Pattern(regexp = ".*[!@#$%^&*].*", message = "Password must contain at least one special character")
    private String password;

    @NotBlank(message = "Email field is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    @NotBlank(message = "AccountNonExpired field is required")
    private Boolean accountNonExpired;

    @NotBlank(message = "AccountNonLocked field is required")
    private Boolean accountNonLocked;

    @NotBlank(message = "CredentialsNonExpired field is required")
    private Boolean credentialsNonExpired;

    @NotBlank(message = "Enabled field is required")
    private Boolean enabled;

    @NotBlank(message = "Roles field is required")
    private Set<RoleRequest> roles;

    public UpdateRosterupUserRequest() {
    }

    public UpdateRosterupUserRequest(String username, String password, String email, Boolean accountNonExpired,
                                     Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled,
                                     Set<RoleRequest> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleRequest> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleRequest> roles) {
        this.roles = roles;
    }
}
