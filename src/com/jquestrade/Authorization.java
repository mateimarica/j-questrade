package com.jquestrade;

public class Authorization {
	private String access_token;
	
	private String api_server;
	
	/** The time (in seconds?) in which access token will expire. */
	private int expiresIn;
	
	private String refresh_token;
	
	private String token_type;
	
	public String getAccessToken() {
		return access_token;
	}
	
	public String getApiServer() {
		return api_server;
	}
	
	public int getAccessTokenExpiry() {
		return expiresIn;
	}
	
	public String getRefreshToken() {
		return refresh_token;
	}
	
	public String getTokenType() {
		return token_type;
	}
	
	
}
