package com.codewithomarm.rosterup.security.v1.user.exception;

import java.io.Serial;

public class DuplicateEmailException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
}
