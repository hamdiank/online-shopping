package com.onepoint.test.exceptions;

import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Class<?> clazz, String id) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), id));
    }

    private static String generateMessage(String entity, String id) {
        return StringUtils.capitalize(entity) +
                " was not found for id " +
                id;
    }
}
