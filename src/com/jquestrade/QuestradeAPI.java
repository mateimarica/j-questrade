package com.jquestrade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jquestrade.exceptions.RefreshTokenException;
import com.jquestrade.exceptions.StatusCodeException;
import com.jquestrade.exceptions.RefreshTokenException;

/** Questrade API object */
public class QuestradeAPI {
	
	private final DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
	
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
	 * @throws RefreshTokenException */
	public QuestradeAPI activate() throws RefreshTokenException {
		if(startingRefreshToken != null) {
			retrieveAccessToken(startingRefreshToken);
			startingRefreshToken = null;
		}
		return this;
	}
	
	/** Doesn't work. Don't know why.
	 * @throws RefreshTokenException
	 */
	@Deprecated
	public void revokeAuthorization() throws RefreshTokenException {
		String URL = "https://login.questrade.com/oauth2/revoke";
		
		Request request = new Request(URL);
		request.addParameter("token", authorization.getRefreshToken());
		request.setRequestMethod(RequestMethod.POST);
		request.setContentType("application/x-www-form-urlencoded");
		
		connectToURL(request);
	}
	
	
	public void retrieveAccessToken(String refreshToken) throws RefreshTokenException {		
		String URL = "https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refreshToken;
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		
		
		String responseJSON = connectToURL(request);
		
		authorization = new Gson().fromJson(responseJSON, Authorization.class);
		

		if(authRelayFunction != null) {
			authRelayFunction.accept(authorization);
		}
		
	}	
	
	public void retrieveAccessToken() throws RefreshTokenException{
		retrieveAccessToken(authorization.getRefreshToken());
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public QuestradeAPI setAuthRelay(Consumer<Authorization> authRelayFunction) {
		this.authRelayFunction = authRelayFunction;
		return this;
	}
	
	
	
	public Account[] getAccounts() throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String accountsJSON = connectToURL(request);
		
		//String accs = "{\"accounts\":[{\"type\":\"TFSA\",\"number\":\"52241053\",\"status\":\"Active\",\"isPrimary\":true,\"isBilling\":true,\"clientAccountType\":\"Individual\"}],\"userId\":1006658}";
		Accounts accounts = new Gson().fromJson(accountsJSON, Accounts.class);
		
		return accounts.getAccounts();
	}
	
	public ZonedDateTime getTime() throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/time";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		
		String timeJSON = connectToURL(request);
		
		String timeISO = new Gson().fromJson(timeJSON, JsonObject.class).get("time").getAsString();
		return ZonedDateTime.parse(timeISO);
	}
	
	public Activity[] getActivities(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/activities";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String activitiesJSON = connectToURL(request);
		
		Activities activities = new Gson().fromJson(activitiesJSON, Activities.class);
		
		return activities.getActivities();
	}	
	
	public Execution[] getExecutions(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/executions";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String executionsJSON = connectToURL(request);

		Executions executions = new Gson().fromJson(executionsJSON, Executions.class);
		
		return executions.getExecutions();
	}	
	
	/**
     * Connect to a URL that doesn't require an access token.
     * @param URLIn The URL that is to be connected to.
     * @return Returns a reference to the BufferedReader from info can be read from.
     * @throws ResponseCodeException If the website's response code is not 200 (200 = OK)
     */
    /*private String connectToURL(RequestMethod requestMethod, String URLIn, HttpParameter ...params) throws RefreshTokenException {
        return connectToURL(requestMethod, URLIn, null, params);
    }
	
    private String connectToURL(RequestMethod requestMethod, String URLIn, String accessToken, HttpParameter ...params) throws RefreshTokenException {
    	return null;
    }*/
	/**
     * Connect to a URL that requires a start date and an end date.
     * @param URLIn The URL that is to be connected to.
     * @param accessToken The access token.
     * @param startTime Start of time range in ISO format. By default – start of today, 12:00am. Eg of date: startTime=2020-08-23T21:14:07+00:00
     * @param endTime End of time range in ISO format. By default – end of today, 11:59pm
     * @return Returns a reference to the BufferedReader from info can be read from.
	 * @throws RefreshTokenException 
     */
    private String connectToURL(Request request) throws RefreshTokenException {

        try {

            HttpURLConnection connection = request.getConnection();
            
            int statusCode = connection.getResponseCode();
            
       
            //java.net.UnknownHostException e
            
            
            System.out.println("responseCode=" + statusCode + "\n");
            if(statusCode == 400) {
            	throw new RefreshTokenException("Error code " + statusCode + "was returned. Assuming refresh token is invalid.");
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseJSON = in.readLine();
            connection.disconnect();
            
            
            
            // Response codes in the 200s are "successful"
            if (statusCode > 299 || statusCode < 200) {
            	int errorCode = new Gson().fromJson(responseJSON, int.class);
            	
            	// Error code 1017 means access token is invalid or expired
            	if(errorCode == 1017) {
            		retrieveAccessToken(authorization.getRefreshToken()); // get new access token
            		request.setAccessToken(authorization.getAccessToken());
            		return connectToURL(request);
            	}
            	
            	
            	throw new StatusCodeException("A bad status code was returned: " + statusCode, statusCode);
            }
            
            
            
            return responseJSON;
            
        } catch(IOException e) {
        	e.printStackTrace();
        }


        return null;
    }
}
