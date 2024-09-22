package com.codewithomarm.rosterup.exceptions;

import java.io.Serial;
import java.util.List;

public class InvalidTenantDataException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<String> nullFields;

    public InvalidTenantDataException(List<String> nullFields) {
        super(generateErrorMessage(nullFields));
        this.nullFields = nullFields;
    }

    public List<String> getNullFields() {
        return nullFields;
    }

    private static String generateErrorMessage(List<String> nullFields){
        String fields = String.join(", ", nullFields);
        return nullFields.size() == 1
                ? "Tenant " + fields + " is required"
                : "Tenant " + fields + " are required";
    }
}
