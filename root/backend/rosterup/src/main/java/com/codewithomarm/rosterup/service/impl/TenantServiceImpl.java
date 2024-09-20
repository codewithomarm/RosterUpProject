package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.mapper.TenantMapper;
import com.codewithomarm.rosterup.model.entity.Tenant;
import com.codewithomarm.rosterup.repository.TenantRepository;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));
    }

    @Override
    public List<Tenant> getTenantsByName(String name) {
        return tenantRepository.findByName(name);
    }

    @Override
    public Tenant getTenantBySubdomain(String subdomain) {
        return tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with subdomain: " + subdomain));
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
            throw new IllegalArgumentException("Tenant already exists with subdomain: " + tenantDto.getSubdomain());
        }

        // Convert from Tenant DTO to Tenant Entity
        Tenant tenant = tenantMapper.toEntity(tenantDto);

        // Set isActive value by default
        tenant.setActive(true);

        // Save tenant entity in db
        Tenant savedTenant = tenantRepository.save(tenant);

        // Convert saved tenant entity to dto and return
        return tenantMapper.toDto(savedTenant);
    }

    @Override
    public Tenant updateTenant(Tenant tenant) {
        return null;
    }

    @Override
    public void deleteTenant(Long tenantId) {
        tenantRepository.findById(tenantId)
                .ifPresentOrElse(tenantRepository::delete, () -> {
                    throw new TenantNotFoundException("Tenant not found with id: " + tenantId);
                });
    }
}
