package com.codewithomarm.rosterup.security.v1.user.repository;

import com.codewithomarm.rosterup.security.v1.auth.model.Token;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for the {@link RosterupUser} entity.
 * This interface provides methods to perform CRUD operations
 * on the {@link RosterupUser} entity.
 */
public interface RosterupUserRepository extends JpaRepository<RosterupUser, Long> {

    /**
     * Finds a {@link RosterupUser} by its username.
     * @param username The username of the {@link RosterupUser} to find.
     * @return An {@link Optional} of the {@link RosterupUser} with the given username.
     */
    Optional<RosterupUser> findByUsername(String username);

    /**
     * Checks if a {@link RosterupUser} with the given username exists.
     * @param username The username to check.
     * @return {@code true} if a {@link RosterupUser} with the given username exists,
     * {@code false} otherwise.
     */
    Boolean existsByUsername(String username);

    /**
     * Finds a {@link RosterupUser} by its email.
     * @param email The email of the {@link RosterupUser} to find.
     * @return An {@link Optional} of the {@link RosterupUser} with the given email.
     */
    Optional<RosterupUser> findByEmail(String email);

    /**
     * Checks if a {@link RosterupUser} with the given email exists.
     * @param email The email to check.
     * @return {@code true} if a {@link RosterupUser} with the given email exists,
     * {@code false} otherwise.
     */
    Boolean existsByEmail(String email);

    /**
     * Updates the password of a {@link RosterupUser} with the given ID.
     * @param userId The ID of the {@link RosterupUser} to update the password for.
     * @param password The new password.
     */
    @Modifying
    @Transactional
    @Query("UPDATE RosterupUser u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * Finds all {@link RosterupUser}s with the given accountNonExpired value.
     * @param accountNonExpired The accountNonExpired value to search for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RosterupUser}s with the given
     * accountNonExpired value.
     */
    Page<RosterupUser> findByAccountNonExpired(Boolean accountNonExpired, Pageable pageable);

    /**
     * Finds all {@link RosterupUser}s with the given accountNonLocked value.
     * @param accountNonLocked The accountNonLocked value to search for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RosterupUser}s with the given accountNonLocked value.
     */
    Page<RosterupUser> findByAccountNonLocked(Boolean accountNonLocked, Pageable pageable);

    /**
     * Finds all {@link RosterupUser}s with the given credentialsNonExpired value.
     * @param credentialsNonExpired The credentialsNonExpired value to search for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RosterupUser}s with the given credentialsNonExpired value.
     */
    Page<RosterupUser> findByCredentialsNonExpired(Boolean credentialsNonExpired, Pageable pageable);

    /**
     * Finds all {@link RosterupUser}s with the given enabled value.
     * @param enabled The enabled value to search for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RosterupUser}s with the given enabled value.
     */
    Page<RosterupUser> findByEnabled(Boolean enabled, Pageable pageable);

    /**
     * Finds the roles of a {@link RosterupUser} by its username.
     * @param username The username of the {@link RosterupUser} to find the roles for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link Set} of {@link RosterupRole}s of the {@link RosterupUser}
     */
    @Query("SELECT u.rosterupRoles FROM RosterupUser u WHERE u.username = :username")
    Page<Set<RosterupRole>> findRolesByUsername(@Param("username") String username, Pageable pageable);

    /**
     * Finds the tokens of a {@link RosterupUser} by its username.
     * @param username The username of the {@link RosterupUser} to find the tokens for.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link Set} of {@link Token}s of the {@link RosterupUser}
     */
    @Query("SELECT u.tokens FROM RosterupUser u WHERE u.username = :username")
    Page<Set<Token>> findTokensByUsername(@Param("username") String username, Pageable pageable);
}
