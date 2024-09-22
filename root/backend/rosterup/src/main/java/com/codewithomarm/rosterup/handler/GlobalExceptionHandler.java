package com.codewithomarm.rosterup.handler;

import com.codewithomarm.rosterup.dto.response.ErrorResponse;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.InvalidTenantDataException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFoundException(TenantNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                "Tenant Not Found",
                HttpStatus.NOT_FOUND.value(),
                e.getClass().getName()
        );
        error.addDetail(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTenantDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTenantDataException(InvalidTenantDataException e) {
        ErrorResponse error = new ErrorResponse(
                "Invalid Tenant Data",
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName()
        );
        setErrorDetails(e.getNullFields(), error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateSubdomainException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSubdomainException(DuplicateSubdomainException e) {
        ErrorResponse error = new ErrorResponse(
                "Duplicate Subdomain",
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName()
        );
        error.addDetail(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private void setErrorDetails(List<String> nullFields, ErrorResponse error){
        for (String field : nullFields) {
            error.addDetail("Field '"+field+"' is required");
        }
    }
}
