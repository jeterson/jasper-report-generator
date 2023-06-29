package com.bergamota.jasperreports.domain.core.exceptions;

import com.bergamota.jasperreports.common.domain.exception.DomainException;

public class DownloadDomainException extends DomainException {

    public DownloadDomainException(String message) {
        super(message);
    }

    public DownloadDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
