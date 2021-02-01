package com.jquestrade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jquestrade.Balances.Currency;
import com.jquestrade.exceptions.RefreshTokenException;
import com.jquestrade.exceptions.StatusCodeException;

/** Questrade API object */
public class QuestradeAPI {
	
	private final DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
	private Authorization authorization;
	private Consumer<Authorization> authRelayFunction = null;
	
	/** The refresh token that this object is created with. 
	 * After this token is consumed by getting an access token, this string will be null for the remainder of the object's life.
	 */
	private String startingRefreshToken;
	
	/** Create a Questrade API object  */
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
	
	
	public void retrieveAccessToken(String refreshToken) throws RefreshTokenException, StatusCodeException {		
		String URL = "https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refreshToken;
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		
		
		String responseJSON = connectToURL(request);
		
		authorization = new Gson().fromJson(responseJSON, Authorization.class);
		

		if(authRelayFunction != null) {
			authRelayFunction.accept(authorization);
		}
		
	}	
	
	public void retrieveAccessToken() throws RefreshTokenException, StatusCodeException {
		retrieveAccessToken(authorization.getRefreshToken());
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public QuestradeAPI setAuthRelay(Consumer<Authorization> authRelayFunction) {
		this.authRelayFunction = authRelayFunction;
		return this;
	}
	
	public Balances getBalances(String accountNumber) throws StatusCodeException, RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/balances";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String balancesJSON = connectToURL(request);
		
		Balances balance = new Gson().fromJson(balancesJSON, Balances.class);
		
		return balance;
	}
	
	

	
	private class Accounts { private Account[] accounts; }
	public Account[] getAccounts() throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/accounts/";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String accountsJSON = connectToURL(request);
		
		
		
		Accounts accounts = new Gson().fromJson(accountsJSON, Accounts.class);
		
		return accounts.accounts;
	}
	
	public ZonedDateTime getTime() throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/time";

		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		
		String timeJSON = connectToURL(request);
		
		String timeISO = new Gson().fromJson(timeJSON, JsonObject.class).get("time").getAsString();
		return ZonedDateTime.parse(timeISO);
	}
	
	private class Activities { private Activity[] activities; }
	public Activity[] getActivities(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/activities";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String activitiesJSON = connectToURL(request);
	
		Activities activities = new Gson().fromJson(activitiesJSON, Activities.class);
		
		return activities.activities;
	}	
	
	private class Executions { private Execution[] executions; }
	public Execution[] getExecutions(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/executions";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String executionsJSON = connectToURL(request);

		
		
		Executions executions = new Gson().fromJson(executionsJSON, Executions.class);
		
		return executions.executions;
	}	

	
	private Order[] getOrders(String accountNumber, String[] orderIds) throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/orders";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("ids", orderIds);
		
		return completeGetOrders(request);
	}
	
	public Order[] getOrders(String accountNumber, int[] orderIds) throws RefreshTokenException, StatusCodeException {
		String[] allOrderIds = new String[orderIds.length ];
	
		//copy over orderIds array as string
		for(int i = 0; i < orderIds.length; i++) {
			allOrderIds[i] = orderIds[i] + "";
		}
		
		return getOrders(accountNumber, allOrderIds);
	}
	
	public Order[] getOrders(String accountNumber, int orderId, int ...orderIds) throws RefreshTokenException, StatusCodeException {
		String[] allOrderIds = new String[orderIds.length + 1];
		
		allOrderIds[0] = orderId + "";
		
		//copy over orderIds array
		for(int i = 1; i <= orderIds.length; i++) {
			allOrderIds[i] = orderIds[i-1] + "";
		}
		
		return getOrders(accountNumber, allOrderIds);
	}
	
	public Order[] getOrders(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException, StatusCodeException {
		return getOrders(accountNumber, startTime, endTime, null);
	}
	
	public Order[] getOrders(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime, StateFilter stateFilter) throws RefreshTokenException, StatusCodeException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/orders";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		if(stateFilter != null) {
			request.addParameter("stateFilter", stateFilter.name());
		}
		
		return completeGetOrders(request);
	}
	
	private class Orders { private Order[] orders; }
	private Order[] completeGetOrders(Request request) throws StatusCodeException, RefreshTokenException {
		String ordersJSON = connectToURL(request);
		Orders orders = new Gson().fromJson(ordersJSON, Orders.class);
		return orders.orders;
	}
	
	private class Positions { private Position[] positions; }
	public Position[] getPositions(String accountNumber) throws StatusCodeException, RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/positions";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());		
		
		String positionsJSON = connectToURL(request);
		
		Positions positions = new Gson().fromJson(positionsJSON, Positions.class);
		
		return positions.positions;
	}
	
    private String connectToURL(Request request) throws RefreshTokenException, StatusCodeException {

        try {

            HttpURLConnection connection = request.getConnection();
            
            int statusCode = connection.getResponseCode();
            
       
            //java.net.UnknownHostException e
            
            
            //System.out.println("responseCode=" + statusCode + "\n");
            if(statusCode == 400) {
            	throw new RefreshTokenException("Error code " + statusCode + " was returned. Assuming refresh token is invalid.");
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
