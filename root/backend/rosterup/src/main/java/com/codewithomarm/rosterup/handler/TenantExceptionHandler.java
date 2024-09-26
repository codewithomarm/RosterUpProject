package com.codewithomarm.rosterup.handler;

import com.codewithomarm.rosterup.dto.response.ErrorResponse;
import com.codewithomarm.rosterup.exceptions.DuplicateSubdomainException;
import com.codewithomarm.rosterup.exceptions.InvalidTenantParameterException;
import com.codewithomarm.rosterup.exceptions.TenantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class TenantExceptionHandler {

    @ExceptionHandler(InvalidTenantParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTenantParameterException(InvalidTenantParameterException e) {
        ErrorResponse error = new ErrorResponse(
                "Invalid Tenant Parameter",
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName()
        );
        error.addDetail(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation Error",
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getName()
        );
        errors.forEach(errorResponse::addDetail);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
