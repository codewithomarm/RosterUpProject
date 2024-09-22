package com.codewithomarm.rosterup.exceptions;

import java.io.Serial;
import java.util.List;

public class InvalidTenantDataException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<String> nullFields;

    public InvalidTenantDataException(List<String> nullFields) {
        this.nullFields = nullFields;
    }

    public List<String> getNullFields() {
        return nullFields;
    }
}
