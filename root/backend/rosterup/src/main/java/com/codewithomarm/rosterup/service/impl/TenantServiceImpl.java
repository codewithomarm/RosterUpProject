package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.dto.request.tenant.CreateTenantRequest;
import com.codewithomarm.rosterup.dto.request.tenant.UpdateTenantRequest;
import com.codewithomarm.rosterup.dto.response.TenantResponse;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.InvalidTenantParameterException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.model.Tenant;
import com.codewithomarm.rosterup.repository.TenantRepository;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of ITenantService interface.
 * This class provides the business logic for tenant-related operations.
 */
@Service
public class TenantServiceImpl implements ITenantService {

    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    private final TenantRepository tenantRepository;

    /**
     * Constructs a new TenantServiceImpl with the specified TenantRepository dependency.
     * @param tenantRepository The repository for tenant-related database operations.
     */
    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TenantResponse> getAllTenants(Pageable pageable) {
        logger.info("Service: Retrieving all tenants with pagination: {}", pageable);

        Page<Tenant> allTenants = tenantRepository.findAll(pageable);
        Page<TenantResponse> allTenantsResponse = allTenants.map(this::convertToResponse);

        logger.info("Service: Retrieved {} tenants from getAllTenants", allTenantsResponse.getTotalElements());
        return allTenantsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TenantResponse getTenantById(String id) {
        logger.info("Service: Attempting to retrieve tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        Tenant tenantById = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Service: Tenant not found with ID: {} from getTenantById", tenantId);
                    return new TenantNotFoundException(tenantId);
                });

        TenantResponse tenantResponse = convertToResponse(tenantById);

        logger.info("Service: Retrieved tenant with ID: {}", tenantId);
        return tenantResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TenantResponse> getTenantsByName(String name, Pageable pageable) {
        logger.info("Service: Retrieving tenants by name: {} with pagination: {}", name, pageable);

        Page<Tenant> tenantsByName = tenantRepository.findByName(name, pageable);
        Page<TenantResponse> tenantsByNameResponsePage = tenantsByName.map(this::convertToResponse);

        logger.info("Service: Retrieved {} tenants with name: {}", tenantsByNameResponsePage.getTotalElements(), name);
        return tenantsByNameResponsePage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TenantResponse getTenantBySubdomain(String subdomain) {
        logger.info("Service: Attempting to retrieve tenant with subdomain: {}", subdomain);
        Tenant tenantBySubdomain = tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> {
                    logger.error("Service: Tenant not found with subdomain: {}", subdomain);
                    return new TenantNotFoundException(subdomain);
                });

        TenantResponse tenantBySubdomainResponse = convertToResponse(tenantBySubdomain);

        logger.info("Service: Retrieved tenant with subdomain: {}", subdomain);
        return tenantBySubdomainResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TenantResponse> getActiveTenants(Pageable pageable) {
        logger.info("Service: Retrieving active tenants with pagination: {}", pageable);

        Page<Tenant> activeTenants = tenantRepository.findAllActive(pageable);
        Page<TenantResponse> activeTenantsResponsePage = activeTenants.map(this::convertToResponse);

        logger.info("Service: Retrieved {} active tenants", activeTenantsResponsePage.getTotalElements());
        return activeTenantsResponsePage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TenantResponse> getInactiveTenants(Pageable pageable) {
        logger.info("Service: Retrieving inactive tenants with pagination: {}", pageable);

        Page<Tenant> inactiveTenants = tenantRepository.findAllInactive(pageable);
        Page<TenantResponse> inactiveTenantsResponsePage = inactiveTenants.map(this::convertToResponse);

        logger.info("Service: Retrieved {} inactive tenants", inactiveTenantsResponsePage.getTotalElements());
        return inactiveTenantsResponsePage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TenantResponse createTenant(CreateTenantRequest request) {
        logger.info("Service: Attempting to create a new tenant with subdomain: {}", request.getSubdomain());

        if (tenantRepository.findBySubdomain(request.getSubdomain()).isPresent()) {
            logger.error("Service: Subdomain already exists: {}", request.getSubdomain());
            throw new DuplicateSubdomainException(request.getSubdomain());
        }

        // Fill tenant entity object with request data
        Tenant tenantEntity = new Tenant();
        tenantEntity.setName(request.getName());
        tenantEntity.setSubdomain(request.getSubdomain());
        tenantEntity.setActive(request.getActive() != null ? request.getActive() : true);

        // Save tenant entity in db
        Tenant savedTenantEntity = tenantRepository.save(tenantEntity);

        // Convert saved tenant entity to dto and return
        logger.info("Service: Successfully created new tenant with ID: {}", savedTenantEntity.getId());

        return convertToResponse(savedTenantEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TenantResponse updateTenant(String id, UpdateTenantRequest request) {
        logger.info("Service: Attempting to update tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        // Fetch the existing tenant from the db using id parameter
        Tenant tenantEntity = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Service: Tenant not found with ID: {} from updateTenant", tenantId);
                    return new TenantNotFoundException(tenantId);
                });

        // Verify if the subdomain is being changed and if it's already in use by other tenant
        if (!tenantEntity.getSubdomain().equals(request.getSubdomain()) &&
            tenantRepository.findBySubdomain(request.getSubdomain()).isPresent()) {
            logger.error("Service: Subdomain already in use: {}", request.getSubdomain());
            throw new DuplicateSubdomainException(request.getSubdomain());
        }

        // Update tenant entity
        tenantEntity.setName(request.getName());
        tenantEntity.setSubdomain(request.getSubdomain());
        tenantEntity.setActive(request.getActive());

        // Save updated tenant entity in db
        Tenant updatedTenantEntity = tenantRepository.save(tenantEntity);

        // Convert and return updated tenant entity to dto
        TenantResponse updatedTenantResponse = convertToResponse(updatedTenantEntity);

        logger.info("Service: Successfully updated tenant with ID: {}", updatedTenantResponse.getId());
        return updatedTenantResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTenant(String id) {
        logger.info("Service: Attempting to delete tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        tenantRepository.findById(tenantId)
                .ifPresentOrElse(tenant -> {
                    tenantRepository.delete(tenant);
                    logger.info("Service: Successfully deleted tenant with ID: {}", tenantId);
                }, () -> {
                    logger.error("Service: Tenant not found with ID: {} from deleteTenant", tenantId);
                    throw new TenantNotFoundException(tenantId);
                });
    }

    /**
     * Validates and parses the tenant ID from a String to a Long.
     *
     * @param id The ID of the tenant as a String.
     * @return the parsed Long ID.
     * @throws InvalidTenantParameterException if the ID is invalid or not numeric.
     */
    private Long validateAndParseTenantId(String id) {
        try {
            Long tenantId = Long.parseLong(id);
            if (tenantId <= 0) {
                logger.error("Service: Invalid tenant ID: {}", id);
                throw new InvalidTenantParameterException("tenant id", "cannot be negative or zero");
            }
            return tenantId;
        } catch(NumberFormatException e) {
            logger.error("Service: Invalid tenant ID format: {}", id);
            throw new InvalidTenantParameterException("tenant id", "needs to be numeric");
        }
    }

    /**
     * Converts a Tenant entity model to a TenantResponse DTO.
     * @param tenant The tenant entity model to convert.
     * @return the converted TenantResponse DTO.
     */
    private TenantResponse convertToResponse(Tenant tenant){
        TenantResponse response = new TenantResponse();
        response.setId(tenant.getId());
        response.setName(tenant.getName());
        response.setSubdomain(tenant.getSubdomain());
        response.setActive(tenant.getActive());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setUpdatedAt(tenant.getUpdatedAt());
        return response;
    }
}
