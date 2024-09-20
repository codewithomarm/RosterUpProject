package com.codewithomarm.rosterup.service;

import com.codewithomarm.rosterup.model.entity.Tenant;

import java.util.List;

public interface ITenantService {
    List<Tenant> getAllTenants();
    Tenant getTenantById(Long tenantId);
    List<Tenant> getTenantsByName(String name);
    Tenant getTenantBySubdomain(String subdomain);
    List<Tenant> getActiveTenants();
    List<Tenant> getInactiveTenants();
    Tenant createTenant(Tenant tenant);
    Tenant updateTenant(Tenant tenant);
    void deleteTenant(Long tenantId);
}
