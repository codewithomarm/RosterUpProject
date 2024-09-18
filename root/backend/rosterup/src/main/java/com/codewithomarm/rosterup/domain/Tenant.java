package com.codewithomarm.rosterup.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String subdomain;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenant")
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenant")
    private List<Position> positions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenant")
    private List<LineOfBusiness> lineOfBusinesses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenant")
    private List<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenant")
    private List<Roster> rosters;

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<LineOfBusiness> getLineOfBusinesses() {
        return lineOfBusinesses;
    }

    public void setLineOfBusinesses(List<LineOfBusiness> lineOfBusinesses) {
        this.lineOfBusinesses = lineOfBusinesses;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Roster> getRosters() {
        return rosters;
    }

    public void setRosters(List<Roster> rosters) {
        this.rosters = rosters;
    }
}
