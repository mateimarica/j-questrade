package com.jquestrade.exceptions;

/**
Thrown when the manual authorization token is not valid.
*/
public class BadRefreshTokenException extends java.lang.Exception {
	public BadRefreshTokenException() {}
	
	public BadRefreshTokenException(String reason) {
		super(reason);
	}
}