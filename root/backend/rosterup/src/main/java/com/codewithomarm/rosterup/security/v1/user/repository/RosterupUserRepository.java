package com.codewithomarm.rosterup.security.v1.user.repository;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RosterupUserRepository extends JpaRepository<RosterupUser, Long> {
    Optional<RosterupUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
