package com.codewithomarm.rosterup.tenant.v1.dto.response;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "tenants", itemRelation = "tenant")
public class TenantResponse extends RepresentationModel<TenantResponse> {

    private Long id;
    private String name;
    private String subdomain;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TenantResponse() {
    }

    public TenantResponse(Long id, String name, String subdomain, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.subdomain = subdomain;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    @Override
    public String toString() {
        return "TenantResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subdomain='" + subdomain + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
