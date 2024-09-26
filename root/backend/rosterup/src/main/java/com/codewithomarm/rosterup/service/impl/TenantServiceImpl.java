package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.InvalidTenantParameterException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.mapper.TenantMapper;
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
    private final TenantMapper tenantMapper;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    /**
     * Retrieves a page list of all tenants
     *
     * @param pageable Pagination information
     * @return A page of TenantDTOs
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getAllTenants(Pageable pageable) {
        logger.info("Retrieving all tenants with pagination: {}", pageable);
        Page<TenantDTO> allTenants = tenantRepository.findAll(pageable).map(tenantMapper::toDto);
        logger.info("Retrieved {} tenants", allTenants.getTotalElements());
        return allTenants;
    }

    /**
     * Retrieves a tenant by its ID.
     *
     * @param id the ID of the tenant to retrieve
     * @return the TenantDTO of the found tenant
     * @throws TenantNotFoundException if the tenant is not found
     * @throws InvalidTenantParameterException if the tenant id is empty or less than 0
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public TenantDTO getTenantById(String id) {
        logger.info("Attempting to retrieve tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        TenantDTO tenant = tenantRepository.findById(tenantId)
                .map(tenantMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with ID: {}", tenantId);
                    return new TenantNotFoundException(tenantId);
                });
        logger.info("Retrieved tenant with ID: {}", tenantId);
        return tenant;
    }

    /**
     * Retrieves a page list of tenantDTO by name.
     *
     * @param name the name of the tenants to retrieve
     * @param pageable Pagination information
     * @return a page of TenantDTOs
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getTenantsByName(String name, Pageable pageable) {
        logger.info("Retrieving tenants by name: {} with pagination: {}", name, pageable);
        Page<TenantDTO> tenantsByName = tenantRepository.findByName(name, pageable).map(tenantMapper::toDto);
        logger.info("Retrieved {} tenants with name: {}", tenantsByName.getTotalElements(), name);
        return tenantsByName;
    }

    /**
     * Retrieves a tenant by its subdomain
     *
     * @param subdomain the subdomain of the tenant to retrieve
     * @return the TenantDTO of the found tenant
     * @throws TenantNotFoundException if the tenant is not found
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public TenantDTO getTenantBySubdomain(String subdomain) {
        logger.info("Attempting to retrieve tenant with subdomain: {}", subdomain);
        TenantDTO tenant = tenantRepository.findBySubdomain(subdomain)
                .map(tenantMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with subdomain: {}", subdomain);
                    return new TenantNotFoundException(subdomain);
                });
        logger.info("Retrieved tenant with subdomain: {}", subdomain);
        return tenant;
    }

    /**
     * Retrieves a paginated list of all active tenants.
     *
     * @param pageable Pagination information.
     * @return a page containing tenantDTOs of active tenants.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getActiveTenants(Pageable pageable) {
        logger.info("Retrieving active tenants with pagination: {}", pageable);
        Page<TenantDTO> activeTenants = tenantRepository.findAllActive(pageable).map(tenantMapper::toDto);
        logger.info("Retrieved {} active tenants", activeTenants.getTotalElements());
        return activeTenants;
    }

    /**
     * Retrieves a paginated list of all inactive tenants.
     *
     * @param pageable Pagination information.
     * @return a page containing tenantDTOs of inactive tenants.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getInactiveTenants(Pageable pageable) {
        logger.info("Retrieving inactive tenants with pagination: {}", pageable);
        Page<TenantDTO> inactiveTenants = tenantRepository.findAllInactive(pageable).map(tenantMapper::toDto);
        logger.info("Retrieved {} inactive tenants", inactiveTenants.getTotalElements());
        return inactiveTenants;
    }

    /**
     * Creates a new tenant.
     *
     * @param tenantDto the tenantDTO containing the new tenant´s information.
     * @return the created TenantDTO.
     * @throws DuplicateSubdomainException if the subdomain already exists.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    @Transactional
    public TenantDTO createTenant(TenantDTO tenantDto) {
        logger.info("Attempting to create a new tenant with subdomain: {}", tenantDto.getSubdomain());
        if (tenantRepository.findBySubdomain(tenantDto.getSubdomain()).isPresent()) {
            logger.error("Subdomain already exists: {}", tenantDto.getSubdomain());
            throw new DuplicateSubdomainException(tenantDto.getSubdomain());
        }
        // Convert from Tenant DTO to Tenant Entity
        Tenant tenantEntity = tenantMapper.toEntity(tenantDto);
        // Set isActive value by default
        tenantEntity.setActive(true);
        // Save tenant entity in db
        Tenant savedTenantEntity = tenantRepository.save(tenantEntity);
        // Convert saved tenant entity to dto and return
        logger.info("Successfully created new tenant with ID: {}", savedTenantEntity.getId());
        return tenantMapper.toDto(savedTenantEntity);
    }

    /**
     * Updates and existing tenant.
     *
     * @param id the id from the tenant to be updated.
     * @param tenantDto the tenantDTO containing the updated tenant´s information.
     * @return the updated tenantDTO.
     * @throws TenantNotFoundException if the tenant is not found based on the id provided.
     * @throws DuplicateSubdomainException if the new subdomain is already in use by another tenant
     * @author Omar Montoya @codewithomarm
     */
    @Override
    @Transactional
    public TenantDTO updateTenant(String id, TenantDTO tenantDto) {
        logger.info("Attempting to update tenant with ID: {}", id);
        Long tenantId = validateAndParseTenantId(id);

        // Fetch the existing tenant from the db using id parameter
        Tenant tenantEntity = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with ID: {}", tenantId);
                    return new TenantNotFoundException(tenantId);
                });

        // Verify if the subdomain is being changed and if it's already in use by other tenant
        if (!tenantEntity.getSubdomain().equals(tenantDto.getSubdomain()) &&
            tenantRepository.findBySubdomain(tenantDto.getSubdomain()).isPresent()) {
            logger.error("Subdomain already in use: {}", tenantDto.getSubdomain());
            throw new DuplicateSubdomainException(tenantDto.getSubdomain());
        }

        // Update tenant entity
        tenantEntity.setName(tenantDto.getName());
        tenantEntity.setSubdomain(tenantDto.getSubdomain());
        tenantEntity.setActive(tenantDto.getActive());

        // Save updated tenant entity in db
        Tenant updatedTenantEntity = tenantRepository.save(tenantEntity);

        // Convert and return updated tenant entity to dto
        logger.info("Successfully updated tenant with ID: {}", updatedTenantEntity.getId());
        return tenantMapper.toDto(updatedTenantEntity);
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
}
