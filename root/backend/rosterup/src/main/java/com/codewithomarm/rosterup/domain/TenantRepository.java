package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Fetch tenant by name
    List<Tenant> findByName(String name);

    // Fetch tenant by subdomain
    Optional<Tenant> findBySubdomain(String subdomain);

    // Fetch only active tenants
    @Query("SELECT t FROM Tenant t WHERE t.isActive = true")
    List<Tenant> findAllActive();

    // Fetch only inactive tenants
    @Query("SELECT t FROM Tenant t WHERE t.isActive = false")
    List<Tenant> findAllInactive();
}
