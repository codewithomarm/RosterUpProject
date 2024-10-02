package com.codewithomarm.rosterup.service;

import com.codewithomarm.rosterup.dto.request.tenant.CreateTenantRequest;
import com.codewithomarm.rosterup.dto.request.tenant.UpdateTenantRequest;
import com.codewithomarm.rosterup.dto.response.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing tenant-related operations.
 * This interface defines methods for CRUD operations on tenants,
 * as well as various search and filtering functionalities.
 */
public interface ITenantService {
    /**
     * Retrieves all tenants with pagination
     *
     * @param pageable Pagination information.
     * @return A Page of TenantResponse objects.
     */
    Page<TenantResponse> getAllTenants(Pageable pageable);

    /**
     * Retrieves a specific tenant by its ID.
     *
     * @param tenantId The ID of the tenant to retrieve.
     * @return The TenantResponse object for the specified tenant.
     * @throws com.codewithomarm.rosterup.exceptions.TenantNotFoundException if the tenant is not found.
     */
    TenantResponse getTenantById(String tenantId);

    /**
     * Searches for tenants by name with pagination.
     *
     * @param name The name to search for.
     * @param pageable Pagination information.
     * @return A page of TenantResponse objects matching the given name.
     */
    Page<TenantResponse> getTenantsByName(String name, Pageable pageable);

    /**
     * Retrieves a tenant by its subdomain.
     *
     * @param subdomain The subdomain of the tenant to retrieve.
     * @return The TenantResponse object for the specified subdomain.
     * @throws com.codewithomarm.rosterup.exceptions.TenantNotFoundException if no tenant is found with the given subdomain.
     */
    TenantResponse getTenantBySubdomain(String subdomain);

    /**
     * Retrieves all active tenants with pagination.
     *
     * @param pageable Pageable information.
     * @return A page of TenantResponse objects for active tenants
     */
    Page<TenantResponse> getActiveTenants(Pageable pageable);

    /**
     * Retrieves all inactive tenants with pagination.
     *
     * @param pageable Pageable information.
     * @return A page of TenantResponse objects for inactive tenants
     */
    Page<TenantResponse> getInactiveTenants(Pageable pageable);

    /**
     * Creates a new tenant.
     *
     * @param request The CreateTenantRequest object containing the new tenant´s details.
     * @return The TenantResponse object for the newly created tenant.
     * @throws com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException if the subdomain already exists.
     */
    TenantResponse createTenant(CreateTenantRequest request);

    /**
     * Updates an existing tenant.
     * @param tenantId The ID of the tenant to update.
     * @param request The UpdateTenantRequest object containing the updated tenant details.
     * @return The TenantResponse object for the updated tenant.
     * @throws com.codewithomarm.rosterup.exceptions.TenantNotFoundException if the tenant is not found.
     * @throws com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException if the subdomain already exists for another tenant.
     */
    TenantResponse updateTenant(String tenantId, UpdateTenantRequest request);

    /**
     * Deletes a tenant by its ID.
     *
     * @param tenantId The ID of the tenant to delete.
     * @throws com.codewithomarm.rosterup.exceptions.TenantNotFoundException if the tenant is not found.
     */
    void deleteTenant(String tenantId);
}
