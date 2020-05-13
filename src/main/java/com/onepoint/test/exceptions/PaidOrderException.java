package com.onepoint.test.exceptions;

public class PaidOrderException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PaidOrderException(String message) {
		super(message);
	}

}
