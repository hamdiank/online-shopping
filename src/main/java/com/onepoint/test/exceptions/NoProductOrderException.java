package com.onepoint.test.exceptions;

public class NoProductOrderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoProductOrderException(String message) {
		super(message);
	}
}
