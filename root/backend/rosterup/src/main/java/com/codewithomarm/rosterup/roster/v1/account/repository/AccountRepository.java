package com.codewithomarm.rosterup.roster.v1.account.repository;

import com.codewithomarm.rosterup.roster.v1.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Fetch account by name and tenant Id
    @Query("SELECT a FROM Account a WHERE LOWER(REPLACE(a.name, ' ', '-')) = LOWER(:name) AND a.tenant.id = :tenantId")
    Optional<Account> findByNameAndTenantId(String name, Long tenantId, Pageable pageable);

    // Fetch accounts by isActive and tenant Id
    Page<Account> findByIsActiveAndTenantId(Boolean isActive, Long tenantId, Pageable pageable);

    // Fetch accounts by tenant Id
    Page<Account> findByTenantId(Long tenantId, Pageable pageable);

    // Fetch accounts by tenant name
    Page<Account> findByTenantName(String tenantName, Pageable pageable);

    // Fetch accounts by modifiedAt and tenant id
    Page<Account> findByModifiedAtBefore(LocalDateTime dateTime, Pageable pageable);

    // Fetch accounts by created at and tenant id
    Page<Account> findByCreatedAtAfter(LocalDateTime dateTime, Pageable pageable);
}
