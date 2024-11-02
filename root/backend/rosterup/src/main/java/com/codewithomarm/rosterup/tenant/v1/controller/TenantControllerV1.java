package com.codewithomarm.rosterup.tenant.v1.controller;

import com.codewithomarm.rosterup.tenant.v1.assembler.TenantPagedResourcesAssembler;
import com.codewithomarm.rosterup.tenant.v1.dto.request.CreateTenantRequest;
import com.codewithomarm.rosterup.tenant.v1.dto.request.UpdateTenantRequest;
import com.codewithomarm.rosterup.tenant.v1.dto.response.TenantResponse;
import com.codewithomarm.rosterup.tenant.v1.service.ITenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/roster-up/v1/tenants")
@Tag(name = "Tenant", description = "Tenant management APIs")
public class TenantControllerV1 {

    private static final Logger logger = LoggerFactory.getLogger(TenantControllerV1.class);

    private final ITenantService tenantService;
    private final TenantPagedResourcesAssembler tenantPagedResourcesAssembler;

    /**
     * Constructs a new TenantController with the specified dependencies.
     *
     * @param tenantService The service for tenant-related operations.
     * @param tenantPagedResourcesAssembler The assembler for creating paged resources of tenants.
     */
    @Autowired
    public TenantControllerV1(ITenantService tenantService, TenantPagedResourcesAssembler tenantPagedResourcesAssembler) {
        this.tenantService = tenantService;
        this.tenantPagedResourcesAssembler = tenantPagedResourcesAssembler;
    }

    /**
     * Retrieves all tenants with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paged model of tenant responses.
     */
    @Operation(summary = "Get all tenants", description = "Get a list of all tenants with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tenants",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameter",
                    content = @Content)
    })
    @GetMapping()
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
    @Operation(summary = "Get a tenant by ID", description = "Get a specific tenant by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tenant",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TenantResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
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
    @Operation(summary = "Create a new tenant", description = "Create a new tenant with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tenant created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TenantResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping()
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
    @Operation(summary = "Update an existing tenant", description = "Update an existing tenant with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TenantResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
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
    @Operation(summary = "Delete a tenant", description = "Delete a tenant by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
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
    @Operation(summary = "Search tenants by name", description = "Search for tenants by name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tenants",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @GetMapping("/search/name")
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
    @Operation(summary = "Get tenants by active status", description = "Get tenants by their active status with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tenants",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @GetMapping("/search/active")
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
    @Operation(summary = "Get a tenant by subdomain", description = "Get a specific tenant by its subdomain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tenant",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TenantResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content)
    })
    @GetMapping("/subdomains/{subdomainName}")
    public ResponseEntity<EntityModel<TenantResponse>> getTenantBySubdomain(@PathVariable String subdomainName) {
        logger.info("Controller: Fetching tenant with subdomain: {}", subdomainName);

        TenantResponse tenantBySubdomainResponse = tenantService.getTenantBySubdomain(subdomainName);

        EntityModel<TenantResponse> tenantBySubdomainModel = tenantPagedResourcesAssembler.toModel(tenantBySubdomainResponse);

        logger.info("Controller: Retrieved tenant with subdomain: {}", subdomainName);
        return ResponseEntity.ok(tenantBySubdomainModel);
    }
}
