package com.codewithomarm.rosterup.model;

import com.codewithomarm.rosterup.tenant.v1.model.Tenant;
import jakarta.persistence.*;

import java.util.List;

/*
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_number", nullable = false, unique = true, length = 10)
    private String employeeNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "paternal_surname", nullable = false, length = 100)
    private String paternalSurname;

    @Column(name = "maternal_surname", nullable = false, length = 100)
    private String maternalSurname;

    @Column(name = "full_name", nullable = false, length = 300)
    private String fullName;

    @Column(name = "full_name_reversed", nullable = false, length = 300)
    private String fullNameReversed;

    @Column(nullable = false, length = 6)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_head", nullable = true)
    private Employee accountHead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_line_of_business_id", nullable = false)
    private LineOfBusiness lineOfBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Roster> rosters;

    public Employee() {}

    public Employee(String employeeNumber, String firstName, String middleName,
                    String paternalSurname, String maternalSurname, String status,
                    Employee accountHead, LineOfBusiness lineOfBusiness, Tenant tenant) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.fullName = firstName + " " + middleName + " " + paternalSurname +
                " " + maternalSurname;
        this.fullNameReversed = paternalSurname + " " + maternalSurname +
                ", " + firstName + " " + middleName;
        this.status = status;
        this.accountHead = accountHead;
        this.lineOfBusiness = lineOfBusiness;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameReversed() {
        return fullNameReversed;
    }

    public void setFullNameReversed(String fullNameReversed) {
        this.fullNameReversed = fullNameReversed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(Employee accountHead) {
        this.accountHead = accountHead;
    }

    public LineOfBusiness getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
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

 */
