package com.bergamota.jasperreports.domain.core.exceptions;

import com.bergamota.jasperreports.common.domain.exception.DomainException;

public class ReportDomainException extends DomainException {

    public ReportDomainException(String message) {
        super(message);
    }

    public ReportDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
