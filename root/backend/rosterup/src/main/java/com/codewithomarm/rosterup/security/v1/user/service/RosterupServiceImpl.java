package com.codewithomarm.rosterup.security.v1.user.service;

import com.codewithomarm.rosterup.security.v1.user.dto.request.CreateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.request.RoleRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.request.UpdateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import com.codewithomarm.rosterup.security.v1.user.exception.DuplicateEmailException;
import com.codewithomarm.rosterup.security.v1.user.exception.DuplicateUsernameException;
import com.codewithomarm.rosterup.security.v1.user.exception.InvalidRoleNameException;
import com.codewithomarm.rosterup.security.v1.user.model.ERole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupRoleRepository;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RosterupServiceImpl implements IRosterupUserService {

    private final RosterupUserRepository rosterupUserRepository;
    private final RosterupRoleRepository rosterupRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public RosterupServiceImpl(RosterupUserRepository rosterupUserRepository,
                               RosterupRoleRepository rosterupRoleRepository,
                               PasswordEncoder passwordEncoder) {
        this.rosterupUserRepository = rosterupUserRepository;
        this.rosterupRoleRepository = rosterupRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Page<RosterupUserResponse> getAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<RosterupUserResponse> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<RosterupUserResponse> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<RosterupUserResponse> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void updatePassword(String userId, String newPassword) {

    }

    @Override
    public Page<RosterupUserResponse> getUsersByAccountNonExpired(Boolean accountNonExpired, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RosterupUserResponse> getUsersByAccountNonLocked(Boolean accountNonLocked, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RosterupUserResponse> getUsersByCredentialsExpired(Boolean credentialsExpired, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RosterupUserResponse> getUsersByEnabled(Boolean enabled, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RosterupUserResponse> getRolesByUsername(String username, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RosterupUserResponse> getTokensByUsername(String username, Pageable pageable) {
        return null;
    }

    @Override
    public RosterupUserResponse createUser(CreateRosterupUserRequest request) {
        // Validate if username is unique
        if (rosterupUserRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUsernameException(request.getUsername());
        }
        // Validate if email is unique
        if (rosterupUserRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        // Validate all roles from request
        Set<RosterupRole> validatedRosterupRoles = validateAndFetchRoles(request.getRoles());

        RosterupUser rosterupUserEntity = new RosterupUser();
        rosterupUserEntity.setUsername(request.getUsername());
        rosterupUserEntity.setEmail(request.getEmail());
        rosterupUserEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        rosterupUserEntity.setAccountNonExpired(true);
        rosterupUserEntity.setAccountNonLocked(true);
        rosterupUserEntity.setCredentialsNonExpired(true);
        rosterupUserEntity.setEnabled(true);
        rosterupUserEntity.setRosterupRoles(validatedRosterupRoles);

        RosterupUser savedRosterupUser = rosterupUserRepository.save(rosterupUserEntity);

        return convertToResponse(savedRosterupUser);
    }

    @Override
    public RosterupUserResponse updateUser(String id, UpdateRosterupUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    private Set<RosterupRole> validateAndFetchRoles(Set<RoleRequest> roles) {
        Set<String> validRoleNames = Set.of(ERole.DEV.name(), ERole.ADMIN.name(),
                ERole.MANAGER.name(), ERole.SUP.name(), ERole.USER.name());
        Set<String> requestedRoleNames = roles.stream().map(RoleRequest::getName)
                .collect(Collectors.toSet());

        Set<String> invalidRoleNames = requestedRoleNames.stream()
                .filter(roleName -> !validRoleNames.contains(roleName))
                .collect(Collectors.toSet());

        if (!invalidRoleNames.isEmpty()) {
            throw new InvalidRoleNameException("Invalid roles: " + invalidRoleNames);
        }

        return roles.stream()
                .map(roleRequest -> {
                    ERole role = ERole.valueOf(roleRequest.getName());
                    return rosterupRoleRepository.findByName(role)
                            .orElseThrow(() -> new InvalidRoleNameException("Role not found: " + roleRequest.getName()));
                })
                .collect(Collectors.toSet());
    }

    private RosterupUserResponse convertToResponse(RosterupUser rosterupUser) {
        RosterupUserResponse response = new RosterupUserResponse();
        response.setId(rosterupUser.getId());
        response.setUsername(rosterupUser.getUsername());
        response.setEmail(rosterupUser.getEmail());
        response.setRoles(rosterupUser.getRosterupRoles());
        return response;
    }
}
