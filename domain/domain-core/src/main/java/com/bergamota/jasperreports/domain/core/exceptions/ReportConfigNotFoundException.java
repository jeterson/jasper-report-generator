package com.bergamota.jasperreports.domain.core.exceptions;


import com.bergamota.jasperreports.common.domain.exception.NotFoundException;

public class ReportConfigNotFoundException extends NotFoundException {

    public ReportConfigNotFoundException(Class<?> className, Object... args) {
        super(className, args);
    }
}
