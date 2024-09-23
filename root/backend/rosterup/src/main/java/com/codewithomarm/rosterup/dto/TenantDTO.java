package com.codewithomarm.rosterup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TenantDTO {
    private Long id;

    @NotBlank(message = "Name field is required")
    @Size(max = 100, message = "Name max length is 100 characters")
    private String name;

    @NotBlank(message = "Subdomain field is required")
    @Size(max = 50, message = "Subdomain max length is 50 characters")
    private String subdomain;

    @NotNull(message = "Active field is required")
    private Boolean isActive;

    public TenantDTO() {
    }

    public TenantDTO(String name, String subdomain, Boolean isActive) {
        this.name = name;
        this.subdomain = subdomain;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
