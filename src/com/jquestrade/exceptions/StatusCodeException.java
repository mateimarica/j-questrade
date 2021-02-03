package com.jquestrade.exceptions;

/** Thrown when there is some kind of issue with the API request. 
 * Use {@link #getStatusCode()} to get the response status code.
 */
@SuppressWarnings("serial")
public class StatusCodeException extends RuntimeException {

	private int statusCode;
	
	public StatusCodeException(String reason, int statusCode) {
		super(reason);
		this.statusCode = statusCode;
	}

	/** Returns the status code that caused this exception.
	 * @return The status code.
	 * @see <a href="https://www.questrade.com/api/documentation/error-handling">
	 * What the status codes mean in terms of the Questrade API.</a> 
	 * (See the table at the bottom of the page.)
	 */ 
	public int getStatusCode() {
		return statusCode;
	}
}
