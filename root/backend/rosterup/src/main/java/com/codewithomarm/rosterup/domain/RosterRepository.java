package com.codewithomarm.rosterup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RosterRepository extends JpaRepository<Roster, Long> {
    // Fetch roster by employee id
    Optional<Roster> findByEmployee_Id(Long employeeId);

    // Fetch rosters by position id and tenant id
    List<Roster> findByPosition_IdAndTenant_Id(Long positionId, Long tenantId);

    // Fetch rosters by account id and tenant id
    List<Roster> findByAccount_IdAndTenant_Id(Long accountId, Long tenantId);

    // Fetch rosters by tenant id
    List<Roster> findByTenant_Id(Long tenantId);
}
