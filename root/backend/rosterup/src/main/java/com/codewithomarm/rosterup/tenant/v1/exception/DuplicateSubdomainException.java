package com.codewithomarm.rosterup.tenant.v1.exception;

import java.io.Serial;

public class DuplicateSubdomainException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateSubdomainException(String subdomain) {
        super("Tenant already exists with subdomain: " + subdomain);
    }
}
