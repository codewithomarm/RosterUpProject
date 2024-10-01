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

@Service
public class TenantServiceImpl implements ITenantService {

    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    private final TenantRepository tenantRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }


    @Override
    public Page<TenantResponse> getAllTenants(Pageable pageable) {
        logger.info("Retrieving all tenants with pagination: {}", pageable);

        Page<Tenant> allTenants = tenantRepository.findAll(pageable);
        Page<TenantResponse> allTenantsResponse = allTenants.map(this::convertToResponse);

        logger.info("Retrieved {} tenants", allTenantsResponse.getTotalElements());
        return allTenantsResponse;
    }


    @Override
    public TenantResponse getTenantById(String id) {
        logger.info("Attempting to retrieve tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        Tenant tenantById = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with ID: {}", tenantId);
                    return new TenantNotFoundException(tenantId);
                });

        TenantResponse tenantResponse = convertToResponse(tenantById);

        logger.info("Retrieved tenant with ID: {}", tenantId);
        return tenantResponse;
    }


    @Override
    public Page<TenantResponse> getTenantsByName(String name, Pageable pageable) {
        logger.info("Retrieving tenants by name: {} with pagination: {}", name, pageable);

        Page<Tenant> tenantsByName = tenantRepository.findByName(name, pageable);
        Page<TenantResponse> tenantsByNameResponsePage = tenantsByName.map(this::convertToResponse);

        logger.info("Retrieved {} tenants with name: {}", tenantsByNameResponsePage.getTotalElements(), name);
        return tenantsByNameResponsePage;
    }

    @Override
    public TenantResponse getTenantBySubdomain(String subdomain) {
        logger.info("Attempting to retrieve tenant with subdomain: {}", subdomain);
        Tenant tenantBySubdomain = tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with subdomain: {}", subdomain);
                    return new TenantNotFoundException(subdomain);
                });

        TenantResponse tenantBySubdomainResponse = convertToResponse(tenantBySubdomain);

        logger.info("Retrieved tenant with subdomain: {}", subdomain);
        return tenantBySubdomainResponse;
    }


    @Override
    public Page<TenantResponse> getActiveTenants(Pageable pageable) {
        logger.info("Retrieving active tenants with pagination: {}", pageable);

        Page<Tenant> activeTenants = tenantRepository.findAllActive(pageable);
        Page<TenantResponse> activeTenantsResponsePage = activeTenants.map(this::convertToResponse);

        logger.info("Retrieved {} active tenants", activeTenantsResponsePage.getTotalElements());
        return activeTenantsResponsePage;
    }


    @Override
    public Page<TenantResponse> getInactiveTenants(Pageable pageable) {
        logger.info("Retrieving inactive tenants with pagination: {}", pageable);

        Page<Tenant> inactiveTenants = tenantRepository.findAllInactive(pageable);
        Page<TenantResponse> inactiveTenantsResponsePage = inactiveTenants.map(this::convertToResponse);

        logger.info("Retrieved {} inactive tenants", inactiveTenantsResponsePage.getTotalElements());
        return inactiveTenantsResponsePage;
    }


    @Override
    @Transactional
    public TenantResponse createTenant(CreateTenantRequest request) {
        logger.info("Attempting to create a new tenant with subdomain: {}", request.getSubdomain());

        if (tenantRepository.findBySubdomain(request.getSubdomain()).isPresent()) {
            logger.error("Subdomain already exists: {}", request.getSubdomain());
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
        logger.info("Successfully created new tenant with ID: {}", savedTenantEntity.getId());

        return convertToResponse(savedTenantEntity);
    }


    @Override
    @Transactional
    public TenantResponse updateTenant(String id, UpdateTenantRequest request) {
        logger.info("Attempting to update tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        // Fetch the existing tenant from the db using id parameter
        Tenant tenantEntity = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with ID: {}", tenantId);
                    return new TenantNotFoundException(tenantId);
                });

        // Verify if the subdomain is being changed and if it's already in use by other tenant
        if (!tenantEntity.getSubdomain().equals(request.getSubdomain()) &&
            tenantRepository.findBySubdomain(request.getSubdomain()).isPresent()) {
            logger.error("Subdomain already in use: {}", request.getSubdomain());
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

        logger.info("Successfully updated tenant with ID: {}", updatedTenantResponse.getId());
        return updatedTenantResponse;
    }

    /**
     * Deletes an existing tenant.
     *
     * @param id the id from the tenant to be deleted.
     * @throws TenantNotFoundException if the tenant is not found based on the id provided.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public void deleteTenant(String id) {
        logger.info("Attempting to delete tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        tenantRepository.findById(tenantId)
                .ifPresentOrElse(tenant -> {
                    tenantRepository.delete(tenant);
                    logger.info("Successfully deleted tenant with ID: {}", tenantId);
                }, () -> {
                    logger.error("Tenant not found with ID: {}", tenantId);
                    throw new TenantNotFoundException(tenantId);
                });
    }

    /**
     * Validates and parses the tenant ID from String to Long
     *
     * @param id the ID of the tenant as a String
     * @return the parse Long ID
     * @throws InvalidTenantParameterException if the ID is invalid
     */
    private Long validateAndParseTenantId(String id) {
        try {
            Long tenantId = Long.parseLong(id);
            if (tenantId <= 0) {
                logger.error("Invalid tenant ID: {}", id);
                throw new InvalidTenantParameterException("tenant id", "cannot be negative or zero");
            }
            return tenantId;
        } catch(NumberFormatException e) {
            logger.error("Invalid tenant ID format: {}", id);
            throw new InvalidTenantParameterException("tenant id", "needs to be numeric");
        }
    }

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
