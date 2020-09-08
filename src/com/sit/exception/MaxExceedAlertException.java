package com.sit.exception;

public class MaxExceedAlertException extends Exception {

	private static final long serialVersionUID = 1896899296798918384L;

	public MaxExceedAlertException() {
		super("30018");
	}

	public MaxExceedAlertException(String args0) {
		super(args0);
	}
}
