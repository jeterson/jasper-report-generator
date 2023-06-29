package com.bergamota.jasperreports.common.domain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.*;

@Slf4j
public class JSONParseExtension {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> Optional<T> deserialize(String serializedData,Class<T> classz){
        try{
            return Optional.ofNullable(objectMapper.readValue(serializedData, classz));
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}
