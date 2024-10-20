package com.codewithomarm.rosterup.tenant.v1.assembler;

import com.codewithomarm.rosterup.tenant.v1.controller.TenantControllerV1;
import com.codewithomarm.rosterup.tenant.v1.dto.response.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TenantPagedResourcesAssembler extends PagedResourcesAssembler<TenantResponse> {

    public TenantPagedResourcesAssembler() {
        super(null, UriComponentsBuilder.fromPath("/").build());
    }

    public EntityModel<TenantResponse> toModel(TenantResponse tenant) {
        return new TenantModelAssembler().toModel(tenant);
    }

    public PagedModel<EntityModel<TenantResponse>> toPagedModel(Page<TenantResponse> tenants) {
        return super.toModel(tenants, new TenantModelAssembler());
    }

    public PagedModel<EntityModel<TenantResponse>> toPagedModelWithNameSearch(Page<TenantResponse> tenants, String name) {
        PagedModel<EntityModel<TenantResponse>> pagedModel = toPagedModel(tenants);

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                .getTenantsByName(name, tenants.getPageable())).withSelfRel()
        );

        if (tenants.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                    .getTenantsByName(name, tenants.nextPageable())).withRel("next")
            );
        }

        if (tenants.hasPrevious()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                    .getTenantsByName(name, tenants.previousPageable())).withRel("prev")
            );
        }

        return pagedModel;
    }

    public PagedModel<EntityModel<TenantResponse>> toPagedModelWithActiveStatus(Page<TenantResponse> tenants, Boolean isActive) {
        PagedModel<EntityModel<TenantResponse>> pagedModel = toPagedModel(tenants);

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                .getTenantsByActiveStatus(isActive, tenants.getPageable())).withSelfRel()
        );

        if (tenants.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                    .getTenantsByActiveStatus(isActive, tenants.nextPageable())).withRel("next")
            );
        }

        if (tenants.hasPrevious()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TenantControllerV1.class)
                    .getTenantsByActiveStatus(isActive, tenants.previousPageable())).withRel("prev")
            );
        }

        return pagedModel;
    }

    private static class TenantModelAssembler implements RepresentationModelAssembler<TenantResponse, EntityModel<TenantResponse>> {

        @Override
        public EntityModel<TenantResponse> toModel(TenantResponse tenant) {
            return EntityModel.of(tenant,
                    linkTo(methodOn(TenantControllerV1.class).getTenantById(tenant.getId().toString())).withSelfRel(),
                    linkTo(methodOn(TenantControllerV1.class).updateTenant(tenant.getId().toString(), null)).withRel("update"),
                    linkTo(methodOn(TenantControllerV1.class).deleteTenant(tenant.getId().toString())).withRel("delete")
            );
        }
    }

}
