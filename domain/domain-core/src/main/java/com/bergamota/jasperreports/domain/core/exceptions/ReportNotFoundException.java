package com.bergamota.jasperreports.domain.core.exceptions;


import com.bergamota.jasperreports.common.domain.exception.NotFoundException;

public class ReportNotFoundException extends NotFoundException {

    public ReportNotFoundException(Class<?> className, Object... args) {
        super(className, args);
    }
}
