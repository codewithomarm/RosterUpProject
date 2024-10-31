package com.codewithomarm.rosterup.security.v1.user.exception;

import java.io.Serial;

public class DuplicateUsernameException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateUsernameException(String username) {
        super("Username already exists: " + username);
    }
}
