package com.jquestrade;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class Request {
	
	private String accessToken;
	
	private int parameterCount = 0;
	
	private String requestMethod = "GET";
	
	private String URL;

	private String contentType;
	
	public Request(String URL) {
        this.URL = URL;
	}
	
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public void addParameter(String key, String value) {
		URL += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + value;
		
		parameterCount++;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public HttpURLConnection getConnection() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
		
		if (accessToken != null) {
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        }
		
		if(contentType != null) {
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", contentType);
		}
		
		connection.setRequestMethod(requestMethod);
			
		System.out.println("URL = " + URL);
		return connection;
	}
	
		
}
