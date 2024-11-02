package com.codewithomarm.rosterup.security.v1.user.service;

import com.codewithomarm.rosterup.security.v1.user.dto.request.CreateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.request.UpdateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing RosterupUser entities.
 */
public interface IRosterupUserService {

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getAllUsers(Pageable pageable);

    /**
     * Retrieves a {@link RosterupUserResponse} by its id.
     * @param id The id of the user.
     * @return an {@link Optional} of {@link RosterupUserResponse}
     */
    Optional<RosterupUserResponse> getUserById(String id);

    /**
     * Retrieves a {@link RosterupUserResponse} by its username.
     * @param username The username of the user.
     * @return an {@link Optional} of {@link RosterupUserResponse}
     */
    Optional<RosterupUserResponse> getUserByUsername(String username);

    /**
     * Retrieves a {@link RosterupUserResponse} by its email.
     * @param email The email of the user.
     * @return an {@link Optional} of {@link RosterupUserResponse}
     */
    Optional<RosterupUserResponse> getUserByEmail(String email);

    /**
     * Updates the password of a {@link RosterupUser} by its id.
     * @param userId The id of the user.
     * @param newPassword The new password.
     */
    void updatePassword(String userId, String newPassword);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by accountNonExpired.
     * @param accountNonExpired The accountNonExpired of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getUsersByAccountNonExpired(Boolean accountNonExpired, Pageable pageable);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by accountNonLocked.
     * @param accountNonLocked The accountNonLocked of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getUsersByAccountNonLocked(Boolean accountNonLocked, Pageable pageable);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by credentialsExpired.
     * @param credentialsExpired The credentialsExpired of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getUsersByCredentialsExpired(Boolean credentialsExpired, Pageable pageable);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by enabled.
     * @param enabled The enabled of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getUsersByEnabled(Boolean enabled, Pageable pageable);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by username.
     * @param username The username of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getRolesByUsername(String username, Pageable pageable);

    /**
     * Retrieves all {@link RosterupUserResponse}s with pagination by username.
     * @param username The username of the user.
     * @param pageable The pagination information.
     * @return a page of {@link RosterupUserResponse}
     */
    Page<RosterupUserResponse> getTokensByUsername(String username, Pageable pageable);

    /**
     * Create a new {@link RosterupUser}.
     * @param request The request containing {@link RosterupUser} details.
     * @return a {@link RosterupUserResponse}
     */
    RosterupUserResponse createUser(CreateRosterupUserRequest request);

    /**
     * Updates an existing {@link RosterupUser}.
     * @param id The id of the {@link RosterupUser} to be updated.
     * @param request The request containing the updated {@link RosterupUser} details.
     * @return a {@link RosterupUserResponse}
     */
    RosterupUserResponse updateUser(String id, UpdateRosterupUserRequest request);

    /**
     * Deletes a {@link RosterupUser} by its id.
     * @param id The id of the {@link RosterupUser} to be deleted.
     */
    void deleteUser(String id);
}
