package com.bergamota.jasperreport.services.base;

import java.lang.reflect.ParameterizedType;

import lombok.SneakyThrows;

public class GetTypeParent<T> {

	@SneakyThrows
	protected Class<?> getGenericClass()
    {                	
    	Class<?> clazz = Class.forName(getGenericClassName());
    	return clazz;
    }
    
    @SuppressWarnings("unchecked")
	private String getGenericClassName() {
    	return ((Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
        
    }
}
