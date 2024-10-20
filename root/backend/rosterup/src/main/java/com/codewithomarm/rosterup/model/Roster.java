package com.codewithomarm.rosterup.model;

import com.codewithomarm.rosterup.tenant.v1.model.Tenant;
import jakarta.persistence.*;
/*
@Entity
public class Roster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_employee_id", nullable = false, unique = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tenant_id", nullable = false)
    private Tenant tenant;

    public Roster() {}

    public Roster(Employee employee, Position position, Account account, Tenant tenant) {
        this.employee = employee;
        this.position = position;
        this.account = account;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}

 */
