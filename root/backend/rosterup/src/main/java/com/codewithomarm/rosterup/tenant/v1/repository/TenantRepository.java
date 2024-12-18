package com.codewithomarm.rosterup.tenant.v1.repository;

import com.codewithomarm.rosterup.tenant.v1.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Fetch tenant by name
    @Query("SELECT t FROM Tenant t WHERE LOWER(REPLACE(t.name, ' ', '-')) = LOWER(:name)")
    Page<Tenant> findByName(String name, Pageable pageable);

    // Fetch tenant by subdomain
    Optional<Tenant> findBySubdomain(String subdomain);

    // Fetch only active tenants
    @Query("SELECT t FROM Tenant t WHERE t.isActive = true")
    Page<Tenant> findAllActive(Pageable pageable);

    // Fetch only inactive tenants
    @Query("SELECT t FROM Tenant t WHERE t.isActive = false")
    Page<Tenant> findAllInactive(Pageable pageable);
}
