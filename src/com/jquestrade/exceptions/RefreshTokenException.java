package com.jquestrade.exceptions;

/**
Thrown when the refresh token is not valid. Refresh tokens expire 7 days from when they were generated.
*/
public class RefreshTokenException extends java.lang.Exception {
	public RefreshTokenException(String reason) {
		super(reason);
	}
}