package com.codewithomarm.rosterup.controller;

import com.codewithomarm.rosterup.assembler.TenantPagedResourcesAssembler;
import com.codewithomarm.rosterup.dto.request.tenant.CreateTenantRequest;
import com.codewithomarm.rosterup.dto.request.tenant.UpdateTenantRequest;
import com.codewithomarm.rosterup.dto.response.TenantResponse;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST Controller for managing Tenant-related operations.
 * This controller provides endpoints for CRUD operations on Tenants
 * as well as search functionalities.
 */
@RestController
@RequestMapping("api/roster-up/tenants")
public class TenantController {

    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    private final ITenantService tenantService;
    private final TenantPagedResourcesAssembler tenantPagedResourcesAssembler;

    /**
     * Constructs a new TenantController with the specified dependencies.
     *
     * @param tenantService The service for tenant-related operations.
     * @param tenantPagedResourcesAssembler The assembler for creating paged resources of tenants.
     */
    @Autowired
    public TenantController(ITenantService tenantService, TenantPagedResourcesAssembler tenantPagedResourcesAssembler) {
        this.tenantService = tenantService;
        this.tenantPagedResourcesAssembler = tenantPagedResourcesAssembler;
    }

    /**
     * Retrieves all tenants with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paged model of tenant responses.
     */
    @GetMapping() // GET http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getAllTenants(Pageable pageable) {
        logger.info("Controller: Fetching all tenants with pagination: {}", pageable);

        Page<TenantResponse> allTenantsPagedResponse = tenantService.getAllTenants(pageable);

        PagedModel<EntityModel<TenantResponse>> allTenantsPagedModel = tenantPagedResourcesAssembler.toPagedModel(allTenantsPagedResponse);

        logger.info("Controller: Retrieved {} tenants", allTenantsPagedResponse.getTotalElements());
        return ResponseEntity.ok(allTenantsPagedModel);
    }

    /**
     * Retrieves a specific tenant by its ID.
     *
     * @param id The ID of the tenant to retrieve
     * @return ResponseEntity containing the tenant response.
     */
    @GetMapping("/{id}") // GET http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<EntityModel<TenantResponse>> getTenantById(@PathVariable String id) {
        logger.info("Controller: Fetching tenant with id: {}", id);

        TenantResponse tenantByIdResponse = tenantService.getTenantById(id);

        EntityModel<TenantResponse> tenantByIdModel = tenantPagedResourcesAssembler.toModel(tenantByIdResponse);

        logger.info("Controller: Retrieved tenant with id: {}", id);
        return ResponseEntity.ok(tenantByIdModel);
    }

    /**
     * Creates a new tenant.
     *
     * @param request The create tenant request containing tenant details.
     * @return ResponseEntity containing the created tenant response.
     */
    @PostMapping() // POST http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<EntityModel<TenantResponse>> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        logger.info("Controller: Creating new tenant with name: {}", request.getName());

        TenantResponse createdTenantResponse = tenantService.createTenant(request);

        // Set the location header for the newly created tenant
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTenantResponse.getId())
                .toUri();

        EntityModel<TenantResponse> createdTenantModel = tenantPagedResourcesAssembler.toModel(createdTenantResponse);

        logger.info("Controller: Created new tenant with id: {}", createdTenantResponse.getId());
        return ResponseEntity.created(location).body(createdTenantModel);
    }

    /**
     * Updates an existing tenant.
     *
     * @param id The ID of the tenant to update.
     * @param request The update tenant request containing updated tenant details.
     * @return ResponseEntity containing the updated tenant response.
     */
    @PutMapping("/{id}") // PUT http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<EntityModel<TenantResponse>> updateTenant(@PathVariable String id,
                                                                    @Valid @RequestBody UpdateTenantRequest request) {
        logger.info("Controller: Updating tenant with id: {}", id);

        TenantResponse updatedTenantResponse = tenantService.updateTenant(id, request);

        EntityModel<TenantResponse> updatedTenantModel = tenantPagedResourcesAssembler.toModel(updatedTenantResponse);

        logger.info("Controller: Updated tenant with id: {}", id);
        return ResponseEntity.ok(updatedTenantModel);
    }

    /**
     * Deletes a tenant by its ID.
     *
     * @param id The ID of the tenant to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{id}") // DELETE http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<Void> deleteTenant(@PathVariable String id) {
        logger.info("Controller: Deleting tenant with id: {}", id);

        tenantService.deleteTenant(id);

        logger.info("Controller: Deleted tenant with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches for tenants by name with pagination
     *
     * @param name The name to search for.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paged model of tenant responses.
     */
    @GetMapping("/search/name") // GET http://localhost:8080/api/roster-up/tenants/search/name?name=tenantName
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getTenantsByName(@RequestParam String name,
                                                                                    @PageableDefault(size = 20) Pageable pageable) {
        logger.info("Controller: Searching tenants by name: {} with pagination: {}", name, pageable);

        Page<TenantResponse> tenantsByNameResponse = tenantService.getTenantsByName(name, pageable);

        PagedModel<EntityModel<TenantResponse>> tenantsByNamePagedModel = tenantPagedResourcesAssembler.toPagedModelWithNameSearch(tenantsByNameResponse, name);

        logger.info("Controller: Found {} tenants matching name: {}", tenantsByNameResponse.getTotalElements(), name);
        return ResponseEntity.ok(tenantsByNamePagedModel);
    }

    /**
     * Retrieves tenants by their active status with pagination.
     *
     * @param isActive The active status to filter by.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paged model of tenant responses.
     */
    @GetMapping("/search/active") // GET http://localhost:8080/api/roster-up/tenants/search/active?active={true/false}
    public ResponseEntity<PagedModel<EntityModel<TenantResponse>>> getTenantsByActiveStatus(
            @RequestParam(name="active", required=true) Boolean isActive,
            @PageableDefault(size = 20) Pageable pageable) {
        logger.info("Controller: Fetching {} tenants with pagination: {}", isActive ? "active" : "inactive", pageable);

        Page<TenantResponse> tenantsByActiveStatusResponse;
        if (isActive) {
            tenantsByActiveStatusResponse = tenantService.getActiveTenants(pageable);
        } else {
            tenantsByActiveStatusResponse = tenantService.getInactiveTenants(pageable);
        }

        PagedModel<EntityModel<TenantResponse>> tenantsByActiveStatusPagedModel = tenantPagedResourcesAssembler
                .toPagedModelWithActiveStatus(tenantsByActiveStatusResponse, isActive);

        logger.info("Controller: Retrieved {} {} tenants", tenantsByActiveStatusResponse.getTotalElements(), isActive ? "active" : "inactive");
        return ResponseEntity.ok(tenantsByActiveStatusPagedModel);
    }

    /**
     * Retrieves a tenant by its subdomain.
     * @param subdomainName The subdomain of the tenant to retrieve.
     * @return ResponseEntity containing the tenant response.
     */
    @GetMapping("/subdomains/{subdomainName}") // GET http://localhost:8080/api/roster-up/tenants/subdomains/{subdomainName}
    public ResponseEntity<EntityModel<TenantResponse>> getTenantBySubdomain(@PathVariable String subdomainName) {
        logger.info("Controller: Fetching tenant with subdomain: {}", subdomainName);

        TenantResponse tenantBySubdomainResponse = tenantService.getTenantBySubdomain(subdomainName);

        EntityModel<TenantResponse> tenantBySubdomainModel = tenantPagedResourcesAssembler.toModel(tenantBySubdomainResponse);

        logger.info("Controller: Retrieved tenant with subdomain: {}", subdomainName);
        return ResponseEntity.ok(tenantBySubdomainModel);
    }
}
