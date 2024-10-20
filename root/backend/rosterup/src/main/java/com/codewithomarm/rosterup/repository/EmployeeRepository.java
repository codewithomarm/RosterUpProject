package com.codewithomarm.rosterup.repository;
/*
import com.codewithomarm.rosterup.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Fetch employees by employee number
    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    // Fetch employees by first name and tenant id
    List<Employee> findByFirstNameAndTenantId(String firstName, Long tenantId);

    // Fetch employees by middle name and tenant id
    List<Employee> findByMiddleNameAndTenantId(String middleName, Long tenantId);

    // Fetch employees by paternal surname and tenant id
    List<Employee> findByPaternalSurnameAndTenantId(String paternalSurname, Long tenantId);

    // Fetch employees by maternal surname and tenant id
    List<Employee> findByMaternalSurnameAndTenantId(String maternalSurname, Long tenantId);

    // Fetch employees by full name and tenant id
    List<Employee> findByFullNameAndTenantId(String fullName, Long tenantId);

    // Fetch employees by full name reversed and tenant id
    List<Employee> findByFullNameReversedAndTenantId(String fullNameReversed, Long tenantId);

    // Fetch employees by status = "ACTIVE" and tenant id
    @Query("SELECT e FROM Employee e WHERE e.status = 'ACTIVE' AND e.tenant.id = :tenantId")
    List<Employee> findActiveEmployeesByTenantId(@Param("tenantId") Long tenantId);

    // Fetch employees by status = "NLE" and tenant id
    @Query("SELECT e FROM Employee e WHERE e.status = 'NLE' AND e.tenant.id = :tenantId")
    List<Employee> findNLEEmployeesByTenantId(@Param("tenantId") Long tenantId);

    // Fetch employees by line of business id and tenant id
    List<Employee> findByLineOfBusiness_IdAndTenant_Id(Long lineOfBusinessId, Long tenantId);

    // Fetch employees by tenant id
    List<Employee> findByTenantId(Long tenantId);
}

 */
