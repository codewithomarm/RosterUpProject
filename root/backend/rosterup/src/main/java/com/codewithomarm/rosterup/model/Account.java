package com.codewithomarm.rosterup.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Roster> rosters;

    public Account(){
    }

    public Account(String name, Boolean isActive, Tenant tenant) {
        this.name = name;
        this.isActive = isActive;
        this.tenant = tenant;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<Roster> getRosters() {
        return rosters;
    }

    public void setRosters(List<Roster> rosters) {
        this.rosters = rosters;
    }
}
