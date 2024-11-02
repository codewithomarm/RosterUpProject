package com.codewithomarm.rosterup.security.v1.user.assembler;

import com.codewithomarm.rosterup.security.v1.user.controller.RosterupUserControllerV1;
import com.codewithomarm.rosterup.security.v1.user.dto.response.RosterupUserResponse;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RosterupUserPagedResourceAssembler extends PagedResourcesAssembler<RosterupUserPagedResourceAssembler> {

    public RosterupUserPagedResourceAssembler() {
        super(null, UriComponentsBuilder.fromPath("/").build());
    }

    public EntityModel<RosterupUserResponse> toModel(RosterupUserResponse rosterupUser) {
        return new RosterupUserModelAssembler().toModel(rosterupUser);
    }

    private static class RosterupUserModelAssembler implements RepresentationModelAssembler<RosterupUserResponse,
            EntityModel<RosterupUserResponse>>{

        @Override
        public EntityModel<RosterupUserResponse> toModel(RosterupUserResponse user) {
            return EntityModel.of(user,
                    linkTo(methodOn(RosterupUserControllerV1.class)
                            .getUserById(user.getId().toString())).withSelfRel()
                    );
        }
    }
}
