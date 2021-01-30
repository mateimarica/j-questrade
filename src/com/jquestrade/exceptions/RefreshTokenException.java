package com.jquestrade.exceptions;

/**
Thrown when the manual authorization token is not valid.
*/
public class RefreshTokenException extends java.lang.Exception {
	public RefreshTokenException(String reason) {
		super(reason);
	}
}