package com.bergamota.jasperreports.domain.core.exceptions;


import com.bergamota.jasperreports.common.domain.exception.NotFoundException;

public class FileNotFoundException extends NotFoundException {

    public FileNotFoundException(String message) {
        super(message);
    }
}
