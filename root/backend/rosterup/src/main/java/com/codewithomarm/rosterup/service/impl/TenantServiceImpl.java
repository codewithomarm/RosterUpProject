package com.codewithomarm.rosterup.service.impl;

import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import com.codewithomarm.rosterup.model.entity.Tenant;
import com.codewithomarm.rosterup.repository.TenantRepository;
import com.codewithomarm.rosterup.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements ITenantService {

    private final TenantRepository tenantRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
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
    public Tenant createTenant(Tenant tenant) {
        return null;
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
