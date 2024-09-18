package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Fetch account by name
    Optional<Account> findByName(String name);

    // Fetch only active accounts
    List<Account> findAllActive();

    // Fetch only inactive accounts
    List<Account> findallInactive();
}
