package com.bergamota.jasperreports.common.domain.exception;

import com.bergamota.jasperreports.common.domain.valueobjects.InternalErrorCodeConstants;

import java.util.ArrayList;
import java.util.List;

public class NotFoundException extends DomainException{
    public NotFoundException(Class<?> className, Object... args) {
        this(String.format("Object {%s} not found with identifier(s) {%s}",className.getSimpleName(), formatArgsToMessage(args)));
    }

    public NotFoundException(String message) {
        super(message);
        setInternalCode(InternalErrorCodeConstants.DB_OBJECT_NOT_FOUND);
    }

    private static String formatArgsToMessage(Object... args){
        if(args.length == 1)
            return String.format("{%s}", args[0]);

        List<String> messagesGroups = new ArrayList<>();
        for(int i = 0;i<args.length;i+=2) {
            var name = args[i];
            var value = args[i+1];
            messagesGroups.add(String.format("%s=%s", name, value));
            i++;
        }

        return String.join(", ", messagesGroups);
    }
}
