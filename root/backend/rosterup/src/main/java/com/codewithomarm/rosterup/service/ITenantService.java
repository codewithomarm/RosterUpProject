package com.codewithomarm.rosterup.service;

import com.codewithomarm.rosterup.dto.TenantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITenantService {
    Page<TenantDTO> getAllTenants(Pageable pageable);
    TenantDTO getTenantById(Long tenantId);
    Page<TenantDTO> getTenantsByName(String name, Pageable pageable);
    TenantDTO getTenantBySubdomain(String subdomain);
    Page<TenantDTO> getActiveTenants(Pageable pageable);
    Page<TenantDTO> getInactiveTenants(Pageable pageable);
    TenantDTO createTenant(TenantDTO tenant);
    TenantDTO updateTenant(Long tenantId, TenantDTO tenant);
    void deleteTenant(Long tenantId);
}
