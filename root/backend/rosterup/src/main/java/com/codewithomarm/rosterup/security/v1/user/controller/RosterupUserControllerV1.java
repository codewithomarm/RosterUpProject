package com.codewithomarm.rosterup.security.v1.user.controller;

import com.codewithomarm.rosterup.security.v1.user.assembler.RosterupUserPagedResourceAssembler;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import com.codewithomarm.rosterup.security.v1.user.service.IRosterupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roster-up/v1/users")
public class RosterupUserControllerV1 {

    private final IRosterupUserService rosterupUserService;
    private final RosterupUserPagedResourceAssembler rosterupUserPagedResourceAssembler;

    @Autowired
    public RosterupUserControllerV1(IRosterupUserService rosterupUserService,
                                    RosterupUserPagedResourceAssembler rosterupUserPagedResourceAssembler) {
        this.rosterupUserService = rosterupUserService;
        this.rosterupUserPagedResourceAssembler = rosterupUserPagedResourceAssembler;
    }


    public ResponseEntity<EntityModel<RosterupUserResponse>> getUserById(@PathVariable String userId) {
        //return ResponseEntity.ok(rosterupUserPagedResourceAssembler.toModel(rosterupUserService.getUserById(userId)));
        return null;
    }
}
