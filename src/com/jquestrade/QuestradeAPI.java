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
import com.jquestrade.Order.OrderState;
import com.jquestrade.exceptions.ArgumentException;
import com.jquestrade.exceptions.RefreshTokenException;
import com.jquestrade.exceptions.StatusCodeException;
import com.jquestrade.exceptions.TimeRangeException;

/** The {@code QuestradeAPI} class represents the Questrade API. It contains methods for easily accessing the Questrade API,
 * such as retrieving the balances, positions, orders, market data, etc. It will also automatically retrieve a new access token
 * if the current access token expires during runtime.
 * @author Matei Marica
 * @see <a href="https://www.questrade.com/api">Questrade API documentation</a>
 */
public class QuestradeAPI {
	
	/** A string representation of the this object's last HTTP request. */
	private String lastRequest;
	
	/** Date formatter object for converting converting <code>ZonedDateTime</code> objects to strings in the 
	 * ISO 8601 time format.
	 */
	private final DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
	
	/** Authorization object that is created with information retrieved when consuming refresh token. */
	private Authorization authorization;
	
	/** Represents a {@code void} function that relays the {@code Authorization} object to a given function.
	 * Set in {@code retrieveAccessToken()}.
	 */
	private Consumer<Authorization> authRelayFunction = null;
	
	/** The refresh token that this object is created with. This variable's only function is to hold the refresh token between the
	 * time that a {@code QuestradeAPI} object is created and the time that it's activated.
	 * After this token is consumed by getting an access token, this string will be {@code null} for the remainder of the object's life.
	 */
	private String startingRefreshToken;
	
	private Authorization startingAuthorization;
	
	/** Creates an instance of the {@code QuestradeAPI} wrapper, whose methods can be used to access the Questrade API. 
	 * To use the object to access the Questrade API,
	 * you must call the {@link #activate()} method on the object, otherwise API methods will not work.
	 * @param refreshToken The refresh token that is used to gain access to the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/getting-started">
	 * Instructions for getting a refresh token for your Questrade account.</a>
	 */
	public QuestradeAPI(String refreshToken) {
		this.startingRefreshToken = refreshToken;
	}
	
	/** Creates an instance of the {@code QuestradeAPI} wrapper, whose methods can be used to access the Questrade API. To use the object to access the Questrade API,
	 * you must call the {@link #activate()} method on the object, otherwise API methods will not work. This constructor is intended for using cached data to create a
	 * {@code QuestradeAPI} object, to save time and bandwidth since an access token can be used for 30 minutes after generation. Calling this constructor on its own
	 * will <b>not</b> make an API request to Questrade.
	 * @param refreshToken The refresh token that is used to gain access to the Questrade API.
	 * @param accessToken The previously-generated access token. This will be used until it expires, after which the refreshToken will
	 * be used to get a new authorization.
	 * @param apiServer The API server address associated with the accessToken.
	 * @see <a href="https://www.questrade.com/api/documentation/getting-started">
	 * Instructions for getting a refresh token for your Questrade account.</a>
	 */
	public QuestradeAPI(String refreshToken, String accessToken, String apiServer) {
		this.startingAuthorization = new Authorization(refreshToken, accessToken, apiServer);
	}
	
	/** A string representation of the this object's last HTTP request.
	 * @return A string representation of the this object's last HTTP request.
	 */
	public String getLastRequest() {
		return lastRequest;
	}
	
	/** Starts up the API connection by exchanging the refresh token for an access token. You must call this method before calling any methods that perform
	 * API requests. This will create an {@link Authorization} for this object. Calling this method more than one time has no effect.
	 * @return A reference to the calling object, for optional method chaining.<br>
	 * Example: {@code QuestradeApi q = new QuestradeAPI(token).activate();}
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * */
	public QuestradeAPI activate() throws RefreshTokenException {
		if(startingRefreshToken != null) {
			retrieveAccessToken(startingRefreshToken);
			startingRefreshToken = null;
		} else if(startingAuthorization != null) {
			authorization = startingAuthorization;
			startingAuthorization = null;
		}
		
		return this;
	}
	
	/** Doesn't work. Don't know why. <b>Do not use.</b>
	 * @throws RefreshTokenException  If the refresh token is invalid.
	 * @see <a href="ttps://www.questrade.com/api/documentation/security">
	 * Revoking a token.</a>
	 */
	/*@Deprecated
	public void revokeAuthorization() throws RefreshTokenException {
		String URL = "https://login.questrade.com/oauth2/revoke";
		
		Request request = new Request(URL);
		request.addParameter("token", authorization.getRefreshToken());
		request.setRequestMethod(RequestMethod.POST);
		request.setContentType("application/x-www-form-urlencoded");
		
		sendRequest(request);
	}*/
	
	/** Manually refresh the authorization (which includes the access token) with a given refresh token. Calling this function will save the resulting
	 * {@link Authorization} object to be relayed to <i>authorization relay function</i> 
	 * (if set using the {@link #setAuthRelay(Consumer)} method).<br><br>
	 * For reference, an access token usually expires in 1800 seconds (30 minutes). This value can be retrieved by using 
	 * {@link #getAuthorization()} then the {@link Authorization#getAccessTokenExpiry()} method.
	 * @param refreshToken The refresh token to be used to refresh the authorization.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 */
	public void retrieveAccessToken(String refreshToken) throws RefreshTokenException {		
		String URL = "https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refreshToken;
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		
		String responseJSON = sendRequest(request);
		
		authorization = new Gson().fromJson(responseJSON, Authorization.class);
		
		if(authRelayFunction != null) {
			authRelayFunction.accept(authorization);
		}
		
	}	
	
	/** Forcefully refreshes the authorization (which includes the access token) with the refresh token saved within the object. 
	 * Calling this function will save the resulting {@link Authorization} object to be relayed to <i>authorization relay function</i> 
	 * (if set using the {@link #setAuthRelay(Consumer)} method).<br><br>
	 * For reference, an access token usually expires in 1800 seconds (30 minutes). This value can be retrieved by using 
	 * {@link #getAuthorization()} then the {@link Authorization#getAccessTokenExpiry()} method.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 */
	public void retrieveAccessToken() throws RefreshTokenException {
		retrieveAccessToken(authorization.getRefreshToken());
	}

	/**Returns an {@link Authorization} object which contains the access token, api server,
	 * access token expiry time, new refresh token, and the access token type (which is always Bearer).<br><br>
	 * @return The current {@code Authorization} object. Will be {@code null} if {@link #activate()} has not been called yet.
	 */
	public Authorization getAuthorization() {
		return authorization;
	}

	/** Sets the authorization relay function, which is user-created method to which an {@link Authorization} object is relayed to.
	 * This is useful for when you want to save the new refresh token instead of having to manually generate a new one on the Questrade website
	 * every time you use your application. For example, you could relay the {@code Authorization} object to a method that saved the new refresh
	 * token in a text file, so that the refresh token could be used when creating another {@code QuestradeAPI} object.<br><br>
	 * 
	 * Example usage:<br>
	 * {@code QuestradeAPI q = new QuestradeAPI(token);}<br>
	 * {@code q.setAuthRelay(auth -> saveToFile(auth));}<br>
	 * @param authRelayFunction Should be a {@code void} function to which the {@code Authorization} will be forwarded to.
	 * This can be written as a lambda function.
	 * @return A reference to the calling object, for optional method chaining.<br>
	 * Example: {@code QuestradeApi q = new QuestradeAPI(token).setAuthRelay(function);}
	 */
	public QuestradeAPI setAuthRelay(Consumer<Authorization> authRelayFunction) {
		this.authRelayFunction = authRelayFunction;
		return this;
	}
	
	/** Get the balances for the given account. 
	 * @param accountNumber The account number to get the balances for. To get an account number, call
	 * {@link #getAccounts()} to get a {@code Account[]}, then call {@link Account#getNumber()} on some index.
	 * @return A {@code Balances} object, from which the following {@code Balance} objects can be gotten:
	 * perCurrencyBalances, combinedBalances, sodPerCurrencyBalances, sodCombinedBalances.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-balances">
	 * The Questrade API <b>GET accounts/:id/balances</b> documentation</a>
	 */
	public Balances getBalances(String accountNumber) throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/balances";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String balancesJSON = sendRequest(request);
		
		Balances balance = new Gson().fromJson(balancesJSON, Balances.class);
		return balance;
	}
	
	/** Private class used for GSON parsing, only in {@link QuestradeAPI#getAccounts()} */
	private class Accounts { private Account[] accounts; }
	
	/** Get all of the accounts for the associated Questrade account.
	 * @return An {@code Account[]} array, containing all of the accounts.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API
	 * @see <a href="https://questrade.com/api/documentation/rest-operations/account-calls/accounts">
	 * The Questrade API <b>GET accounts</b> documentation</a>
	 */
	public Account[] getAccounts() throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/";
		
		Request request = new Request(URL);
		request.setAccessToken(authorization.getAccessToken());
		request.setRequestMethod(RequestMethod.GET);
		
		String accountsJSON = sendRequest(request);
		
		Accounts accounts = new Gson().fromJson(accountsJSON, Accounts.class);
		return accounts.accounts;
	}
	
	/** Returns the current server time in ISO format and Eastern time zone (EST).
	 * @return A {@code ZonedDateTime} object representing the current server time.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/time">
	 * The Questrade API <b>GET time</b> documentation</a>
	 */
	public ZonedDateTime getTime() throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/time";

		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		
		String timeJSON = sendRequest(request);
		
		String timeISO = new Gson().fromJson(timeJSON, JsonObject.class).get("time").getAsString();
		return ZonedDateTime.parse(timeISO);
	}
	
	/** Private class used for GSON parsing, only in {@link QuestradeAPI#getActivities(String, ZonedDateTime, ZonedDateTime)} */
	private class Activities { private Activity[] activities; }
	
	/** Get all of the activities of an account in a given time period. A maximum of 30 days of data can be requested at a time.
	 * @param accountNumber The account for which to get the activities for.
	 * @param startTime The beginning of the time period to get the activities for.
	 * @param endTime The end of the time period to get the activities for. This cannot be more than 31 days after the startTime argument.
	 * @return An {@code Activity[]} array representing all the activities in the given time period.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-activities">
	 * The Questrade API <b>GET accounts/:id/activities</b> documentation</a>
	 */
	public Activity[] getActivities(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException {
		if(startTime.isAfter(endTime)) {
			throw new TimeRangeException("The startTime must be earlier than the endTime.");
		}
		
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/activities";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String activitiesJSON = sendRequest(request);
	
		Activities activities = new Gson().fromJson(activitiesJSON, Activities.class);
		
		return activities.activities;
	}	
	
	/** Private class used for GSON parsing, only in {@link QuestradeAPI#getExecutions(String, ZonedDateTime, ZonedDateTime)} */
	private class Executions { private Execution[] executions; }
	
	/** Get all of the executions of an account in a given time period. A maximum of 30 days of data can be requested at a time.
	 * @param accountNumber The account for which to get the executions for.
	 * @param startTime The beginning of the time period to get the executions for.
	 * @param endTime The end of the time period to get the executions for. 
	 * @return An {@code Activity[]} array representing all the executions in the given time period.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-executions">
	 * The Questrade API <b>GET accounts/:id/executions</b> documentation</a>
	 */
	public Execution[] getExecutions(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException {
		if(startTime.isAfter(endTime)) {
			throw new TimeRangeException("The startTime must be earlier than the endTime.");
		}
		
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/executions";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		
		String executionsJSON = sendRequest(request);

		Executions executions = new Gson().fromJson(executionsJSON, Executions.class);
		
		return executions.executions;
	}	

	/** A private method that both {@link #getOrders(String, int[])} and {@link #getOrders(String, int, int...)} funnel in to.
	 * The order IDs are retrieved as integers from the Questrade API, so it only makes sense to have integer parameters for those two methods.
	 * This method actually makes the order request.
	 * @param accountNumber The account to get the order information for.
	 * @param orderIds An array containing all of the orders IDs to get information for.
	 * @return An {@code Order[]} array containing all of the {@link Order} objects corresponding to the given order IDs.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	private Order[] getOrders(String accountNumber, String[] orderIds) throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/orders";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("ids", orderIds);
		
		return finishGetOrders(request);
	}
	
	/** Get all of the orders of an account in a given time period, using one or more order IDs.
	 * @param accountNumber The account to get the order information for.
	 * @param orderIds An array containing all of the orders IDs to get information for.
	 * @return An {@code Order[]} array containing all of the {@link Order} objects corresponding to the given order IDs.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	public Order[] getOrders(String accountNumber, int[] orderIds) throws RefreshTokenException {
		String[] allOrderIds = new String[orderIds.length];
	
		//copy over orderIds array as string
		for(int i = 0; i < orderIds.length; i++) {
			allOrderIds[i] = orderIds[i] + "";
		}
		
		return getOrders(accountNumber, allOrderIds);
	}
	
	/** Get all of the orders of an account in a given time period, using one or more order IDs.<br><br>
	 * 
	 * Example usages:<br>
	 * {@code getOrders(accountNumber, 11111111)}<br>
	 * {@code getOrders(accountNumber, 11111111, 22222222)}<br>
	 * {@code getOrders(accountNumber, orderNum1, orderNum2, orderNum3)}<br>
	 * @param accountNumber The account to get the order information for.
	 * @param orderId The order ID to get the order info for (an {@link Order} object contain all of an order's information).
	 * @param orderIds Optional parameter for if you want to add more order IDs to the request.
	 * @return An {@code Order[]} array containing all of the {@link Order} objects corresponding to the given order IDs.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	public Order[] getOrders(String accountNumber, int orderId, int ...orderIds) throws RefreshTokenException {
		String[] allOrderIds = new String[orderIds.length + 1];
		
		allOrderIds[0] = orderId + "";
		
		//copy over orderIds array
		for(int i = 1; i <= orderIds.length; i++) {
			allOrderIds[i] = orderIds[i-1] + "";
		}
		
		return getOrders(accountNumber, allOrderIds);
	}
	
	/** Get all of the orders of an account in a given time period. A maximum of 30 days of data can be requested at a time.
	 * This will get all orders in the time period, whether still open or closed. 
	 * To specify the state of the filter, use the {@link #getOrders(String, ZonedDateTime, ZonedDateTime, orderState)} method.
	 * @param accountNumber The account for which to get the orders for.
	 * @param startTime The beginning of the time period to get the orders for.
	 * @param endTime The end of the time period to get the orders for. 
	 * @return An {@code Order[]} array containing all of the {@link Order}s created in the time period.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	public Order[] getOrders(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime) throws RefreshTokenException {
		return getOrders(accountNumber, startTime, endTime, null);
	}
	
	/** Get all of the orders of an account in a given time period. A maximum of 30 days of data can be requested at a time.
	 * You can specify the state of the orders to retrieve using the stateFilter parameter (All, Open, or Closed).
	 * @param accountNumber The account for which to get the orders for.
	 * @param startTime The beginning of the time period to get the orders for.
	 * @param endTime The end of the time period to get the orders for. 
	 * @param orderState The state of the order. See {@link Order.OrderState} for all possible values.
	 * @return An {@code Order[]} array containing all of the {@link Order}s created in the time period.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	public Order[] getOrders(String accountNumber, ZonedDateTime startTime, ZonedDateTime endTime, OrderState orderState) throws RefreshTokenException {
		if(startTime.isAfter(endTime)) {
			throw new TimeRangeException("The startTime must be earlier than the endTime.");
		}
		
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/orders";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());
		request.addParameter("startTime", startTime.format(isoFormatter));
		request.addParameter("endTime", endTime.format(isoFormatter));
		if(orderState != null) {
			request.addParameter("stateFilter", orderState.name());
		}
		
		return finishGetOrders(request);
	}
	
	/** Private class used for GSON parsing, only in {@link QuestradeAPI#finishGetOrders(Request)} */
	private class Orders { private Order[] orders; }
	
	/** A private method that all {@code getOrders} methods eventually funnel in to. Method exists only to not have to repeat code.
	 * @param request The API request created in some {@code getOrders} method. Contains the URL, parameters, request method, etc.
	 * @return An {@code Order[]} array containing all of the corresponding {@link Order} objects.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	private Order[] finishGetOrders(Request request) throws RefreshTokenException {
		String ordersJSON = sendRequest(request);
		Orders orders = new Gson().fromJson(ordersJSON, Orders.class);
		return orders.orders;
	}
	
	/** Private class used for GSON parsing, only in {@link QuestradeAPI#getPositions(String)} */
	private class Positions { private Position[] positions; }
	
	/** Get all of the current positions for a given account.
	 * @param accountNumber The account to get the positions for.
	 * @return A {@code Position[]} array containing all of the corresponding {@link Position} objects.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
	 * The Questrade API <b>GET accounts/:id/orders[/:orderId]</b> documentation</a>
	 */
	public Position[] getPositions(String accountNumber) throws RefreshTokenException {
		String URL = authorization.getApiServer() + "v1/accounts/" + accountNumber + "/positions";
		
		Request request = new Request(URL);
		request.setRequestMethod(RequestMethod.GET);
		request.setAccessToken(authorization.getAccessToken());		
		
		String positionsJSON = sendRequest(request);
		
		Positions positions = new Gson().fromJson(positionsJSON, Positions.class);
		
		return positions.positions;
	}
	
	private class Error {
		private int code;
		private String message;
	}
	
	/** Sends the given request. If the access token expires during execution, it will automatically use the cached refresh token
	 * to get a new access token and retry the request.
	 * @param request The API request that contain contains the URL, parameters, request method, etc.
	 * @return Usually a string in JSON format.
	 * @throws RefreshTokenException If the refresh token is invalid.
	 * @throws ArgumentException If the request arguments are invalid.
	 * @throws StatusCodeException If an error occurs when contacting the Questrade API.
	 */
    private String sendRequest(Request request) throws RefreshTokenException {

        try {
        	lastRequest = request.toString();
        	
            HttpURLConnection connection = request.getConnection();
            
            int statusCode = connection.getResponseCode();
            
            // This exception is thrown when there's no internet (I'm guessing)
            //java.net.UnknownHostException
            
            // Response codes in the 200s are "successful"
            if (statusCode > 299 || statusCode < 200) {
            	
            	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            	String responseJSON = in.readLine();
            	connection.disconnect();
            	System.out.println(responseJSON);
            	Error error = new Gson().fromJson(responseJSON, Error.class);
     
            	// Error code 1017 means access token is invalid or expired
            	if(error.code == 1017) {
            		retrieveAccessToken(authorization.getRefreshToken()); // get new access token
            		request.setAccessToken(authorization.getAccessToken());
            		return sendRequest(request);
            	} else if (error.code == 1002 || error.code == 1003 || error.code == 1004) {
            		throw new ArgumentException(error.message);
            	}
            	
            	if(statusCode == 400) {
            		throw new RefreshTokenException("Error code " + statusCode + " was returned. Assuming refresh token is invalid.");
            	} 
            	
        		throw new StatusCodeException("A bad status code was returned: " + statusCode, statusCode);
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseJSON = in.readLine();
            connection.disconnect();
            
            return responseJSON;
            
        } catch(IOException e) {
        	e.printStackTrace();
        }

        return null;
    }
}
