package com.codewithomarm.rosterup.security.v1.user.exception;

import java.io.Serial;

public class InvalidRoleNameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRoleNameException(String message) {
        super(message);
    }
}
