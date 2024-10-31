package com.codewithomarm.rosterup.security.v1.user.repository;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RosterupRoleRepository extends JpaRepository<RosterupRole, Long> {
    Optional<RosterupRole> findByName(String name);
}
