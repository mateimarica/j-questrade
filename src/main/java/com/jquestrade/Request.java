package com.jquestrade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/** Represents an HTTPS request. */
class Request {
	
	/** Request methods for the HTTP request */
	enum RequestMethod {
		GET, 
		POST;
	}
	
	private String accessToken;
	
	private int parameterCount = 0;
	
	private String requestMethod = "GET";
	
	private String path;

	private String contentType;
	
	private String apiServer;
	

	Request(String path) {
        this.path = path;
	}
	
	void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod.name();
	}
	
	void addParameter(String key, String[] values) {
		path += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + values[0];
		
		for(int i = 1; i < values.length; i++) {
			path += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void addParameter(String key, int[] values) {
		path += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + values[0];
		
		for(int i = 1; i < values.length; i++) {
			path += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void addParameter(String key, String value, String ...values) {
		path += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + value;
		
		for(int i = 0; i < values.length; i++) {
			path += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void addParameter(String key, int value, int ...values) {
		path += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + value;
		
		for(int i = 0; i < values.length; i++) {
			path += "," + values[i];
		}
		
		parameterCount++;
	}
	
	void setContentType(String contentType) {
		this.contentType = contentType;
	}

	void setApiServer(String apiServer) {
		this.apiServer = apiServer;
	}
	
	void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	HttpURLConnection getConnection() throws IOException {
		String URL;
		
		if(apiServer != null) {
			URL = apiServer + path;
		} else {
			URL = path;
		}
		
		HttpURLConnection connection = 
				(HttpURLConnection) new URL(URL).openConnection();
		
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
		return requestMethod + " " + (apiServer != null ? apiServer : "") + path;
	}
	
		
}
