package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Fetch account by name
    Optional<Account> findByName(String name);

    // Fetch only active accounts
    @Query("SELECT a FROM Account a WHERE a.isActive = true")
    List<Account> findAllActive();

    // Fetch only inactive accounts
    @Query("SELECT a FROM Account a WHERE a.isActive = false")
    List<Account> findAllInactive();
}
