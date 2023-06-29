package com.bergamota.jasperreports.domain.core.exceptions;

import com.bergamota.jasperreports.common.domain.exception.DomainException;

public class EncryptionDomainException extends DomainException {
    public EncryptionDomainException(String message) {
        super(message);
    }

    public EncryptionDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
