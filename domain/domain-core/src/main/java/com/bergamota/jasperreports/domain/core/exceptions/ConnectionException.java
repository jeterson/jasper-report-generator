package com.bergamota.jasperreports.domain.core.exceptions;

import com.bergamota.jasperreports.common.domain.exception.DomainException;

public class ConnectionException extends DomainException {
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }
}
