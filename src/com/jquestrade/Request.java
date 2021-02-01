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
	
	public void addParameter(String key, String[] values) {
		URL += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + values[0];
		
		for(int i = 1; i < values.length; i++) {
			URL += "," + values[i];
		}
		
		parameterCount++;
	}
	
	public void addParameter(String key, String value, String ...values) {
		URL += ((parameterCount == 0) ? "?" : "&")
			+ key + "=" + value;
		
		for(int i = 0; i < values.length; i++) {
			URL += "," + values[i];
		}
		
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
			
		//System.out.println("URL = " + URL);
		return connection;
	}
	
		
}
