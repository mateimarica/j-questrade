package com.jquestrade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jquestrade.exceptions.BadRefreshTokenException;

public class QuestradeAPI {
	
	private Authorization authorization;
	private Consumer<Authorization> authRelayFunction = null;
	
	/** The refresh token that this object is created with. 
	 * After this token is consumed by getting an access token, this string will be null for the remainder of the object's life.
	 */
	private String startingRefreshToken;
	
	/** Create a Questrade API object to */
	public QuestradeAPI(String refreshToken) {
		this.startingRefreshToken = refreshToken;
	}
	
	/** Starts up the API by exchanging the refresh token for an access token. 
	 * @throws BadRefreshTokenException */
	public QuestradeAPI activate() throws BadRefreshTokenException {
		if(startingRefreshToken != null) {
			retrieveAccessToken(startingRefreshToken);
			startingRefreshToken = null;
		}
		return this;
	}
	/** Doesn't work. Don't know why.
	 * @throws BadRefreshTokenException
	 */
	@Deprecated
	public void revokeAuthorization() throws BadRefreshTokenException {
		String URL = "https://login.questrade.com/oauth2/revoke";
		
		Request request = new Request(URL);
		request.addParameter("token", authorization.getRefreshToken());
		request.setRequestMethod(RequestMethod.POST);
		request.setContentType("application/x-www-form-urlencoded");
		
		connectToURL(request);
	}
	
	
	public void retrieveAccessToken(String refreshToken) throws BadRefreshTokenException {		
		String URL = "https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refreshToken;
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		
		
		String responseJSON = connectToURL(request);
		
		authorization = new Gson().fromJson(responseJSON, Authorization.class);
		

		if(authRelayFunction != null) {
			authRelayFunction.accept(authorization);
		}
		
	}	
	
	public void retrieveAccessToken() throws BadRefreshTokenException{
		retrieveAccessToken(authorization.getRefreshToken());
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public QuestradeAPI setAuthRelay(Consumer<Authorization> authRelayFunction) {
		this.authRelayFunction = authRelayFunction;
		return this;
	}
	
	
	
	public Account[] getAccounts() throws BadRefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String accountsJSON = connectToURL(request);
		
		//String accs = "{\"accounts\":[{\"type\":\"TFSA\",\"number\":\"52241053\",\"status\":\"Active\",\"isPrimary\":true,\"isBilling\":true,\"clientAccountType\":\"Individual\"}],\"userId\":1006658}";
		Accounts accounts = new Gson().fromJson(accountsJSON, Accounts.class);
		
		return accounts.getAccounts();
	}
	
	public ZonedDateTime getTime() throws BadRefreshTokenException {
		String URL = authorization.getApiServer() + "v1/time";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		
		String timeJSON = connectToURL(request);
		
		String timeISO = new Gson().fromJson(timeJSON, JsonObject.class).get("time").getAsString();
		return ZonedDateTime.parse(timeISO);
	}
	
	/**
     * Connect to a URL that doesn't require an access token.
     * @param URLIn The URL that is to be connected to.
     * @return Returns a reference to the BufferedReader from info can be read from.
     * @throws BadResponseCodeException If the website's response code is not 200 (200 = OK)
     */
    /*private String connectToURL(RequestMethod requestMethod, String URLIn, HttpParameter ...params) throws BadRefreshTokenException {
        return connectToURL(requestMethod, URLIn, null, params);
    }
	
    private String connectToURL(RequestMethod requestMethod, String URLIn, String accessToken, HttpParameter ...params) throws BadRefreshTokenException {
    	return null;
    }*/
	/**
     * Connect to a URL that requires a start date and an end date.
     * @param URLIn The URL that is to be connected to.
     * @param accessToken The access token.
     * @param startTime Start of time range in ISO format. By default – start of today, 12:00am. Eg of date: startTime=2020-08-23T21:14:07+00:00
     * @param endTime End of time range in ISO format. By default – end of today, 11:59pm
     * @return Returns a reference to the BufferedReader from info can be read from.
	 * @throws BadRefreshTokenException 
     */
    private String connectToURL(Request request) throws BadRefreshTokenException {

        try {

            HttpURLConnection connection = request.getConnection();
            
            int responseCode = 400;
            
            try {
            	responseCode = connection.getResponseCode();
            } catch (java.net.UnknownHostException e){
            	e.printStackTrace();
            }
            
            
            System.out.println("responseCode=" + responseCode + "\n");
            if(responseCode == 400) {
            	throw new BadRefreshTokenException("Error code " + responseCode + "was returned. Assuming refresh token is invalid.");
            }
            
            // Response codes in the 200s are "successful"
            /*if (responseCode > 299 || responseCode < 200) {
            	//int errorCode = new Gson().fromJson(responseJSON, int.class);
            	/*int errorCode = new Gson().fromJson("", int.class);
            	
            	// Error code 1017 means access token is invalid or expired
            	if(errorCode == 1017) {
            		retrieveAccessToken(authorization.getRefreshToken()); // get new access token
            		return connectToURL(requestMethod, URLIn, authorization.getAccessToken(), params);
            	}*/
            	
            	
            	//return null;
            //}
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String responseJSON = in.readLine();

            return responseJSON;
            
        } catch(IOException e) {
        	e.printStackTrace();
        }


        return null;
    }
}
