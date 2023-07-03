package com.bergamota.jasperreports.common.domain.valueobjects;

public class InternalErrorCodeConstants {

    public record InternalErrorCode(String code, String defaultMessage) {

    }
    public static final InternalErrorCode DB_CONSTRAINT_VIOLATION = new InternalErrorCode("DB_100","The record have a dependencies, check it.");
    public static final InternalErrorCode DB_OBJECT_NOT_FOUND = new InternalErrorCode("DB_101","Object not exists on database");
}
