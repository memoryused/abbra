package com.sit.exception;

public class MaxExceedReportException extends Exception {

	private static final long serialVersionUID = 1896899296798918384L;

	public MaxExceedReportException() {
		super("30030");
	}

	public MaxExceedReportException(String args0) {
		super(args0);
	}
}
