package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    // Fetch position by Name and Tenant id
    Optional<Position> findByNameAndTenantId(String name, Long tenantId);
}
