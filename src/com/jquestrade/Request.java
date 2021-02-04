package com.jquestrade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class Request {
	
	/** Request method for the HTTP request */
	class RequestMethod {
		static final String GET = "GET";
		static final String POST = "POST";
	}
	
	private String accessToken;
	
	private int parameterCount = 0;
	
	private String requestMethod = "GET";
	
	private String URL;

	private String contentType;
	
	Request(String URL) {
        this.URL = URL;
	}
	
	void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	void addParameter(String key, String[] values) {
		URL += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + values[0];
		
		for(int i = 1; i < values.length; i++) {
			URL += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void addParameter(String key, String value, String ...values) {
		URL += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + value;
		
		for(int i = 0; i < values.length; i++) {
			URL += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	HttpURLConnection getConnection() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
		
		if (accessToken != null) {
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        }
		
		if(contentType != null) {
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", contentType);
		}
		
		connection.setRequestMethod(requestMethod);
			
		return connection;
	}
	
	@Override
	public String toString() {
		return requestMethod + " " + URL;
	}
	
		
}
