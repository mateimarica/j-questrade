package com.jquestrade.exceptions;

@SuppressWarnings("serial")
public class ArgumentException extends RuntimeException {
	public ArgumentException(String reason) {
		super(reason);
	}
}