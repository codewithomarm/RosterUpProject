package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.InvalidTenantDataException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.mapper.TenantMapper;
import com.codewithomarm.rosterup.model.entity.Tenant;
import com.codewithomarm.rosterup.repository.TenantRepository;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenantServiceImpl implements ITenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));
    }

    @Override
    public List<Tenant> getTenantsByName(String name) {
        return tenantRepository.findByName(name);
    }

    @Override
    public Tenant getTenantBySubdomain(String subdomain) {
        return tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> new TenantNotFoundException(subdomain));
    }

    @Override
    public List<Tenant> getActiveTenants() {
        return tenantRepository.findAllActive();
    }

    @Override
    public List<Tenant> getInactiveTenants() {
        return tenantRepository.findAllInactive();
    }

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

    @Override
    @Transactional
    public TenantDTO updateTenant(Long tenantId, TenantDTO tenantDto) {
        // validate tenantDto fields
        validateTenantData(tenantDto);

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

    @Override
    public void deleteTenant(Long tenantId) {
        tenantRepository.findById(tenantId)
                .ifPresentOrElse(tenantRepository::delete, () -> {
                    throw new TenantNotFoundException(tenantId);
                });
    }

    private void validateTenantData(TenantDTO tenantDto) {
        List<String> nullFields = new ArrayList<>();

        if (tenantDto.getName() == null){
            nullFields.add("name");
        }
        if (tenantDto.getSubdomain() == null){
            nullFields.add("subdomain");
        }
        if (tenantDto.getActive() == null){
            nullFields.add("active");
        }

        if (!nullFields.isEmpty()){
            throw new InvalidTenantDataException(nullFields);
        }
    }
}
