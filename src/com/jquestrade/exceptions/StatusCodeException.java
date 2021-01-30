package com.jquestrade.exceptions;

public class StatusCodeException extends RuntimeException {
	
	private int statusCode;
	
	public StatusCodeException(String reason, int statusCode) {
		super(reason);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
