package com.bergamota.jasperreports.common.domain.exception;

import com.bergamota.jasperreports.common.domain.valueobjects.InternalErrorCodeConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DomainException extends RuntimeException{

    @Getter
    @Setter(AccessLevel.MODULE)
    private InternalErrorCodeConstants.InternalErrorCode internalCode;
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
