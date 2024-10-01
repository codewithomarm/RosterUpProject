package com.codewithomarm.rosterup.controller;

import com.codewithomarm.rosterup.assembler.TenantPagedResourcesAssembler;
import com.codewithomarm.rosterup.dto.request.tenant.CreateTenantRequest;
import com.codewithomarm.rosterup.dto.request.tenant.UpdateTenantRequest;
import com.codewithomarm.rosterup.dto.response.TenantResponse;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/roster-up/tenants")
public class TenantController {

    private final ITenantService tenantService;
    private final TenantPagedResourcesAssembler tenantPagedResourcesAssembler;

    @Autowired
    public TenantController(ITenantService tenantService, TenantPagedResourcesAssembler tenantPagedResourcesAssembler) {
        this.tenantService = tenantService;
        this.tenantPagedResourcesAssembler = tenantPagedResourcesAssembler;
    }

    @GetMapping() // GET http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getAllTenants(Pageable pageable) {
        Page<TenantResponse> allTenantsPagedResponse = tenantService.getAllTenants(pageable);

        PagedModel<EntityModel<TenantResponse>> allTenantsPagedModel = tenantPagedResourcesAssembler.toPagedModel(allTenantsPagedResponse);

        return ResponseEntity.ok(allTenantsPagedModel);
    }

    @GetMapping("/{id}") // GET http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<EntityModel<TenantResponse>> getTenantById(@PathVariable String id) {
        TenantResponse tenantByIdResponse = tenantService.getTenantById(id);

        EntityModel<TenantResponse> tenantByIdModel = tenantPagedResourcesAssembler.toModel(tenantByIdResponse);

        return ResponseEntity.ok(tenantByIdModel);
    }

    @PostMapping() // POST http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<EntityModel<TenantResponse>> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        TenantResponse createdTenantResponse = tenantService.createTenant(request);

        // Set the location header for the newly created tenant
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTenantResponse.getId())
                .toUri();

        EntityModel<TenantResponse> createdTenantModel = tenantPagedResourcesAssembler.toModel(createdTenantResponse);

        return ResponseEntity.created(location).body(createdTenantModel);
    }

    @PutMapping("/{id}") // PUT http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<EntityModel<TenantResponse>> updateTenant(@PathVariable String id,
                                                                    @Valid @RequestBody UpdateTenantRequest request) {
        TenantResponse updatedTenantResponse = tenantService.updateTenant(id, request);

        EntityModel<TenantResponse> updatedTenantModel = tenantPagedResourcesAssembler.toModel(updatedTenantResponse);

        return ResponseEntity.ok(updatedTenantModel);
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<Void> deleteTenant(@PathVariable String id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/name") // GET http://localhost:8080/api/roster-up/tenants/search/name?name=tenantName
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getTenantsByName(@RequestParam String name,
                                                                                    @PageableDefault(size = 20) Pageable pageable) {
        Page<TenantResponse> tenantsByNameResponse = tenantService.getTenantsByName(name, pageable);

        PagedModel<EntityModel<TenantResponse>> tenantsByNamePagedModel = tenantPagedResourcesAssembler.toPagedModelWithNameSearch(tenantsByNameResponse, name);

        return ResponseEntity.ok(tenantsByNamePagedModel);
    }

    @GetMapping("/search/active") // GET http://localhost:8080/api/roster-up/tenants/search/active?active={true/false}
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getTenantsByActiveStatus(
            @RequestParam(name="active", required=true) Boolean isActive,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TenantResponse> tenantsByActiveStatusResponse;
        if (isActive) {
            tenantsByActiveStatusResponse = tenantService.getActiveTenants(pageable);
        } else {
            tenantsByActiveStatusResponse = tenantService.getInactiveTenants(pageable);
        }

        PagedModel<EntityModel<TenantResponse>> tenantsByActiveStatusPagedModel = tenantPagedResourcesAssembler
                .toPagedModelWithActiveStatus(tenantsByActiveStatusResponse, isActive);

        return ResponseEntity.ok(tenantsByActiveStatusPagedModel);
    }

    @GetMapping("/subdomains/{subdomainName}") // GET http://localhost:8080/api/roster-up/tenants/subdomains/{subdomainName}
    public ResponseEntity<EntityModel<TenantResponse>> getTenantBySubdomain(@PathVariable String subdomainName) {
        TenantResponse tenantBySubdomainResponse = tenantService.getTenantBySubdomain(subdomainName);

        EntityModel<TenantResponse> tenantBySubdomainModel = tenantPagedResourcesAssembler.toModel(tenantBySubdomainResponse);

        return ResponseEntity.ok(tenantBySubdomainModel);
    }
}
