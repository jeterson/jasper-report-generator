package com.bergamota.jasperreports.domain.core.exceptions;


import com.bergamota.jasperreports.common.domain.exception.NotFoundException;

public class ConnectionConfigNotFoundException extends NotFoundException {

    public ConnectionConfigNotFoundException(Class<?> className, Object... args) {
        super(className, args);
    }
}
