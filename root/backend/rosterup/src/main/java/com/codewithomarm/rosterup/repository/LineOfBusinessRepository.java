package com.codewithomarm.rosterup.repository;

import com.codewithomarm.rosterup.model.entity.LineOfBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineOfBusinessRepository extends JpaRepository<LineOfBusiness, Long> {
    // Fetch Line Of Business by name and tenant id
    Optional<LineOfBusiness> findByNameAndTenantId(String name, Long tenantId);
}
