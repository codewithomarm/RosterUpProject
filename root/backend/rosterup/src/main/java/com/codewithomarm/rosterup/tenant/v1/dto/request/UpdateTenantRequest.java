package com.codewithomarm.rosterup.tenant.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateTenantRequest {

    @NotBlank(message = "Name field is required")
    @Size(min = 2, max = 100, message = "Name max length must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Subdomain field is required")
    @Size(min = 3, max = 50, message = "Subdomain max length must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?$",
            message = "Subdomain must consist of lowercase letters, numbers, and hyphens, and must" +
                    "start and end with a letter or number")
    private String subdomain;

    @NotNull(message = "Active field is required")
    private Boolean isActive;

    public UpdateTenantRequest() {
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

    @Override
    public String toString() {
        return "UpdateTenantRequest{" +
                "name='" + name + '\'' +
                ", subdomain='" + subdomain + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
