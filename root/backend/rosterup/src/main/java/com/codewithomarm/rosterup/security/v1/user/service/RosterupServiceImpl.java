package com.codewithomarm.rosterup.security.v1.user.service;

import com.codewithomarm.rosterup.security.v1.user.dto.request.CreateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.request.RoleRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import com.codewithomarm.rosterup.security.v1.user.exception.DuplicateEmailException;
import com.codewithomarm.rosterup.security.v1.user.exception.DuplicateUsernameException;
import com.codewithomarm.rosterup.security.v1.user.exception.InvalidRoleNameException;
import com.codewithomarm.rosterup.security.v1.user.model.ERole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupRoleRepository;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RosterupServiceImpl implements IRosterupUserService {

    private final RosterupUserRepository rosterupUserRepository;
    private final RosterupRoleRepository rosterupRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public RosterupServiceImpl(RosterupUserRepository rosterupUserRepository, RosterupRoleRepository rosterupRoleRepository,
                               PasswordEncoder passwordEncoder) {
        this.rosterupUserRepository = rosterupUserRepository;
        this.rosterupRoleRepository = rosterupRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RosterupUserResponse findById(Long id) {
        return null;
    }

    @Override
    public RosterupUserResponse findByUsername(String username) {
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
        rosterupUserEntity.setAccountNonExpired(request.getAccountNonExpired());
        rosterupUserEntity.setAccountNonLocked(request.getAccountNonLocked());
        rosterupUserEntity.setCredentialsNonExpired(request.getCredentialsNonExpired());
        rosterupUserEntity.setEnabled(request.getEnabled());
        rosterupUserEntity.setRosterupRoles(validatedRosterupRoles);

        RosterupUser savedRosterupUser = rosterupUserRepository.save(rosterupUserEntity);

        return convertToResponse(savedRosterupUser);
    }

    @Override
    public RosterupUserResponse updateUser(Long id, RosterupUser rosterupUser) {
        // TODO implement update user
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        // TODO implement delete user
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
