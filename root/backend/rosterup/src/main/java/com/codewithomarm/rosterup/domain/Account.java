package com.codewithomarm.rosterup.domain;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Account(){
    }

    public Account(String name, Boolean isActive) {
        this.name = name;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}