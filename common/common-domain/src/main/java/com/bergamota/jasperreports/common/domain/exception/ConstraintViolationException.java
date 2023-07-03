package com.bergamota.jasperreports.common.domain.exception;

import com.bergamota.jasperreports.common.domain.valueobjects.InternalErrorCodeConstants;


public class ConstraintViolationException extends DomainException{

    public ConstraintViolationException(String keyName, String keyValue){
        super(String.format("Constraint %s violation: %s", keyName, keyValue));
        setInternalCode(InternalErrorCodeConstants.DB_CONSTRAINT_VIOLATION);
    }
}
