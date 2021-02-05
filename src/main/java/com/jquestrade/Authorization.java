package com.jquestrade;

/** Represents the data that allows an application to make requests the Questrade API. */
public class Authorization {
	
	/** Creates a Authorization object using cached data. */
	Authorization(String refreshToken, String accessToken, String apiServer) {
		this.refresh_token = refreshToken;
		this.access_token = accessToken;
		this.api_server = apiServer;
		this.expires_in = 1800; // I've always seen it 1800 seconds
		this.token_type = "Bearer"; // Access token type is always Bearer
	}
	
	private String access_token;
	private String api_server;
	private int expires_in;
	private String refresh_token;
	private String token_type;
	
	/** Returns the access token. Expires in 30 minutes after generation.
	 * Only works with the assocatied API server, see {@link #getApiServer()}
	 * @return The access token.
	 */
	public String getAccessToken() {
		return access_token;
	}
	
	/** Returns the URL of the API server assigned to the access token.
	 * @return The URL of the API server assigned to the access token.
	 */
	public String getApiServer() {
		return api_server;
	}
	
	/** Returns how long after generation the access token will expire.
	 * @return How long after generation the access token will expire.
	 * It appears to always be {@code 1800} seconds (30 minutes).
	 */
	public int getAccessTokenExpiry() {
		return expires_in;
	}
	
	/** Returns the new refresh token, which can be used to generate a new {@code Authorization}.
	 * @return The new refresh token.
	 */
	public String getRefreshToken() {
		return refresh_token;
	}
	
	/** The access token type.
	 * @return The access token type. Is always <b>Bearer</b>
	 */
	public String getTokenType() {
		return token_type;
	}
	
	
}
