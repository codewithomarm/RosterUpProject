package com.codewithomarm.rosterup.repository;

import com.codewithomarm.rosterup.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Fetch account by name and tenant id
    Optional<Account> findByNameAndTenantId(String name, Long tenantId);

    // Fetch only active accounts by tenant id
    @Query("SELECT a FROM Account a WHERE a.isActive = true AND a.tenant.id = :tenantId")
    List<Account> findAllActiveByTenantId(@Param("tenantId") Long tenantId);

    // Fetch only inactive accounts by tenant id
    @Query("SELECT a FROM Account a WHERE a.isActive = false AND a.tenant.id = :tenantId")
    List<Account> findAllInactive(@Param("tenantId") Long tenantId);
}
