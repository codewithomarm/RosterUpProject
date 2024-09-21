package com.codewithomarm.rosterup.service;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.model.entity.Tenant;

import java.util.List;

public interface ITenantService {
    List<Tenant> getAllTenants();
    Tenant getTenantById(Long tenantId);
    List<Tenant> getTenantsByName(String name);
    Tenant getTenantBySubdomain(String subdomain);
    List<Tenant> getActiveTenants();
    List<Tenant> getInactiveTenants();
    TenantDTO createTenant(TenantDTO tenant);
    TenantDTO updateTenant(Long tenantId, TenantDTO tenant);
    void deleteTenant(Long tenantId);
}
