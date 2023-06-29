package com.bergamota.jasperreports.domain.core.exceptions;

import com.bergamota.jasperreports.common.domain.exception.DomainException;

public class ExportPDFDomainException extends DomainException {

    public ExportPDFDomainException(String message) {
        super(message);
    }

    public ExportPDFDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
