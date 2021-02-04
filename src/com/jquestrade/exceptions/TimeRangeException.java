package com.jquestrade.exceptions;

/** Thrown when the user-given time-range is invalid, such as if the endTime is sooner than the startDate. */
@SuppressWarnings("serial")
public class TimeRangeException extends ArgumentException {
	public TimeRangeException(String reason) {
		super(reason);
	}
}
