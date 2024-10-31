package com.codewithomarm.rosterup.security.v1.user.service;

import com.codewithomarm.rosterup.security.v1.user.dto.request.CreateRosterupUserRequest;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;

public interface IRosterupUserService {
    public RosterupUserResponse findById(Long id);
    public RosterupUserResponse findByUsername(String username);
    public RosterupUserResponse createUser(CreateRosterupUserRequest request);
    public RosterupUserResponse updateUser(Long id, RosterupUser rosterupUser);
    public void deleteUser(Long id);
}
