package com.codewithomarm.rosterup.exceptions;

import java.io.Serial;

public class TenantNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TenantNotFoundException(Long tenantId) {
        super("Tenant not found with id: " + tenantId);
    }

    public TenantNotFoundException(String tenantSubdomain){
        super("Tenant not found with subdomain: " + tenantSubdomain);
    }
}
