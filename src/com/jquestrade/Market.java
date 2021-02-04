package com.jquestrade;

/** Represents a market (e.g: TSX, NYSE, NASDAQ, etc) 
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/market-calls/markets">
 * The market properties documentation</a>
 */
public class Market {
	
	private Market() {}
	
	private String name;
	private String[] tradingVenues;
	private String defaultTradingVenue;
	private String[] primaryOrderRoutes;
	private String[] secondaryOrderRoutes;
	private String[] level1Feeds;
	private String[] level2Feeds;
	private String extendedStartTime;
	private String startTime;
	private String endTime;
	private String extendedEndTime;
	private int snapQuotesLimit;
	
	/** Returns the market name.
	 * @return The market name.
	 */
	public String getName() {
		return name;
	}
	
	/** Returns a list of trading venue codes.
	 * @return A list of trading venue codes.
	 */
	public String[] getTradingVenues() {
		return tradingVenues;
	}
	
	/** Returns the default trading venue code.
	 * @return The default trading venue code.
	 */
	public String getDefaultTradingVenue() {
		return defaultTradingVenue;
	}
	
	/** Returns a list of the primary order route codes.
	 * @return A list of the primary order route codes.
	 */
	public String[] getPrimaryOrderRoutes() {
		return primaryOrderRoutes;
	}
	
	/** Returns a list of the secondary order route codes.
	 * @return A list of the secondary order route codes.
	 */
	public String[] getSecondaryOrderRoutes() {
		return secondaryOrderRoutes;
	}
	
	/** Returns a list of level 1 market data feed codes.
	 * @return A list of level 1 market data feed codes.
	 */
	public String[] getLevel1Feeds() {
		return level1Feeds;
	}
	
	/** Returns a list of level 2 market data feed codes.
	 * @return A list of level 2 market data feed codes.
	 */
	public String[] getLevel2Feeds() {
		return level2Feeds;
	}
	
	/** Returns the pre-market opening time for current trading date.
	 * @return The pre-market opening time for current trading date.
	 */
	public String getExtendedStartTime() {
		return extendedStartTime;
	}
	
	/** Returns the regular market opening time for current trading date.
	 * @return The regular market opening time for current trading date.
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/** Returns the regular market closing time for current trading date.
	 * @return The regular market closing time for current trading date.
	 */
	public String getEndTime() {
		return endTime;
	}
	
	/** Returns the extended market closing time for current trading date.
	 * @return The extended market closing time for current trading date.
	 */
	public String getExtendedEndTime() {
		return extendedEndTime;
	}
	
	/** Returns the number of snap quotes that the user can retrieve from this market.
	 * @return The snap quote limit.
	 */
	public int getSnapQuotesLimit() {
		return snapQuotesLimit;
	}
	
}
