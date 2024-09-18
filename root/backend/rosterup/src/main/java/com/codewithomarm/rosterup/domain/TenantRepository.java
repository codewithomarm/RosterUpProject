package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Fetch tenant by name
    Optional<Tenant> findByName(String name);

    // Fetch tenant by subdomain
    List<Tenant> findBySubdomain(String subdomain);

    // Fetch only active tenants
    List<Tenant> findAllActive();

    // Fetch only inactive tenants
    List<Tenant> findAllInactive();
}
