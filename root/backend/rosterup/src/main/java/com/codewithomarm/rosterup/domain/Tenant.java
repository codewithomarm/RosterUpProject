package com.codewithomarm.rosterup.domain;

import jakarta.persistence.*;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "subdomain", nullable = false, length = 50, unique = true)
    private String subdomain;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Tenant() {
    }

    public Tenant(String name, String subdomain, Boolean isActive) {
        this.name = name;
        this.subdomain = subdomain;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
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
