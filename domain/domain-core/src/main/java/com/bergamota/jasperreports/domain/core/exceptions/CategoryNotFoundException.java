package com.bergamota.jasperreports.domain.core.exceptions;


import com.bergamota.jasperreports.common.domain.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Class<?> className, Object... args) {
        super(className, args);
    }
}
