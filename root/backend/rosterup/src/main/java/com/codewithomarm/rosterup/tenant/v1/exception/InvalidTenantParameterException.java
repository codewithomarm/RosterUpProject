package com.codewithomarm.rosterup.tenant.v1.exception;

import java.io.Serial;

public class InvalidTenantParameterException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTenantParameterException(String parameter, String message){
        super("Invalid tenant parameter: " + parameter + " " + message);
    }
}
