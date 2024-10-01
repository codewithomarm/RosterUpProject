package com.codewithomarm.rosterup.service;

import com.codewithomarm.rosterup.dto.request.tenant.CreateTenantRequest;
import com.codewithomarm.rosterup.dto.request.tenant.UpdateTenantRequest;
import com.codewithomarm.rosterup.dto.response.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITenantService {
    Page<TenantResponse> getAllTenants(Pageable pageable);
    TenantResponse getTenantById(String tenantId);
    Page<TenantResponse> getTenantsByName(String name, Pageable pageable);
    TenantResponse getTenantBySubdomain(String subdomain);
    Page<TenantResponse> getActiveTenants(Pageable pageable);
    Page<TenantResponse> getInactiveTenants(Pageable pageable);
    TenantResponse createTenant(CreateTenantRequest request);
    TenantResponse updateTenant(String tenantId, UpdateTenantRequest request);
    void deleteTenant(String tenantId);
}
