package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.mapper.TenantMapper;
import com.codewithomarm.rosterup.model.entity.Tenant;
import com.codewithomarm.rosterup.repository.TenantRepository;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements ITenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    /**
     * Retrieves a page list of all tenants
     * @param pageable Pagination information
     * @return A page of TenantDTOs
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getAllTenants(Pageable pageable) {
        return tenantRepository.findAll(pageable).map(tenantMapper::toDto);
    }

    /**
     * Retrieves a tenant by its ID.
     * @param tenantId the ID of the tenant to retrieve
     * @return the TenantDTO of the found tenant
     * @throws TenantNotFoundException if the tenant is not found
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public TenantDTO getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .map(tenantMapper::toDto)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));
    }

    /**
     * Retrieves a page list of tenantDTO by name.
     * @param name the name of the tenants to retrieve
     * @param pageable Pagination information
     * @return a page of TenantDTOs
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getTenantsByName(String name, Pageable pageable) {
        return tenantRepository.findByName(name, pageable).map(tenantMapper::toDto);
    }

    /**
     * Retrieves a tenant by its subdomain
     * @param subdomain the subdomain of the tenant to retrieve
     * @return the TenantDTO of the found tenant
     * @throws TenantNotFoundException if the tenant is not found
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public TenantDTO getTenantBySubdomain(String subdomain) {
        return tenantRepository.findBySubdomain(subdomain)
                .map(tenantMapper::toDto)
                .orElseThrow(() -> new TenantNotFoundException(subdomain));
    }

    /**
     * Retrieves a paginated list of all active tenants.
     * @param pageable Pagination information: page number, page size, and sorting details.
     * @return a page containing tenantDTOs of active tenants.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getActiveTenants(Pageable pageable) {
        return tenantRepository.findAllActive(pageable).map(tenantMapper::toDto);
    }

    /**
     * Retrieves a paginated list of all inactive tenants.
     * @param pageable Pagination information: page number, page size, and sorting details.
     * @return a page containing tenantDTOs of inactive tenants.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public Page<TenantDTO> getInactiveTenants(Pageable pageable) {
        return tenantRepository.findAllInactive(pageable).map(tenantMapper::toDto);
    }

    /**
     * Creates a new tenant.
     * @param tenantDto the tenantDTO containing the new tenant´s information.
     * @return the created TenantDTO.
     * @throws DuplicateSubdomainException if the subdomain already exists.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    @Transactional
    public TenantDTO createTenant(TenantDTO tenantDto) {
        // Validate if subdomain already exist
        if (tenantRepository.findBySubdomain(tenantDto.getSubdomain()).isPresent()) {
            throw new DuplicateSubdomainException(tenantDto.getSubdomain());
        }
        // Convert from Tenant DTO to Tenant Entity
        Tenant tenantEntity = tenantMapper.toEntity(tenantDto);

        // Set isActive value by default
        tenantEntity.setActive(true);

        // Save tenant entity in db
        Tenant savedTenantEntity = tenantRepository.save(tenantEntity);

        // Convert saved tenant entity to dto and return
        return tenantMapper.toDto(savedTenantEntity);
    }

    /**
     * Updates and existing tenant.
     * @param tenantId the id from the tenant to be updated.
     * @param tenantDto the tenantDTO containing the updated tenant´s information.
     * @return the updated tenantDTO.
     * @throws TenantNotFoundException if the tenant is not found based on the id provided.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    @Transactional
    public TenantDTO updateTenant(Long tenantId, TenantDTO tenantDto) {
        // Fetch the existing tenant from the db using id parameter
        Tenant tenantEntity = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));

        // Verify if the subdomain is being changed and if it's already in use by other tenant
        if (!tenantEntity.getSubdomain().equals(tenantDto.getSubdomain()) &&
            tenantRepository.findBySubdomain(tenantDto.getSubdomain()).isPresent()) {
            throw new DuplicateSubdomainException(tenantDto.getSubdomain());
        }

        // Update tenant entity
        tenantEntity.setName(tenantDto.getName());
        tenantEntity.setSubdomain(tenantDto.getSubdomain());
        tenantEntity.setActive(tenantDto.getActive());

        // Save updated tenant entity in db
        Tenant updatedTenantEntity = tenantRepository.save(tenantEntity);

        // Convert and return updated tenant entity to dto
        return tenantMapper.toDto(updatedTenantEntity);
    }

    /**
     * Deletes an existing tenant.
     * @param tenantId the id from the tenant to be deleted.
     * @throws TenantNotFoundException if the tenant is not found based on the id provided.
     * @author Omar Montoya @codewithomarm
     */
    @Override
    public void deleteTenant(Long tenantId) {
        tenantRepository.findById(tenantId)
                .ifPresentOrElse(tenantRepository::delete, () -> {
                    throw new TenantNotFoundException(tenantId);
                });
    }
}
