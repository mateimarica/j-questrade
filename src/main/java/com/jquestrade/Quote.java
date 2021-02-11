package com.jquestrade;

/** Represents a single Level 1 market data quote for a ticker. <br><br>
 * <b>IMPORTANT NOTE:</b> The Questrade user needs to be subscribed to a real-time data package, 
 * to receive market quotes in real-time, otherwise call to get quote is considered snap quote and 
 * limit per market can be quickly reached. Without real-time data package, once limit is reached, the response 
 * will return delayed data. <b>(Please check "delay" parameter in response always)</b>
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/market-calls/markets-quotes-id">
 * The symbol quote documentation</a>
 */
public class Quote {
	private Quote() {}
	
	private String symbol;
	private long symbolId;
	private String tier;
	private double bidPrice;
	private int bidSize;
	private double askPrice;
	private int askSize;
	private double lastTradePriceTrHrs;
	private double lastTradePrice;
	private int lastTradeSize;
	private String lastTradeTick;
	private String lastTradeTime;
	private long volume;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private int delay;
	private boolean isHalted;
	private double high52w;
	private double low52w;
	private double VWAP;
	
	/** Returns the symbol/ticker following Questrade's symbology.
	 * @return The symbol/ticker.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/** Returns the internal symbol identifier.
	 * @return The internal symbol identifier.
	 */
	public long getSymbolId() {
		return symbolId;
	}
	
	/** Returns the market tier.
	 * @return The market tier.
	 */
	public String getTier() {
		return tier;
	}
	
	/** Returns the bid price.
	 * @return The bid price.
	 */
	public double getBidPrice() {
		return bidPrice;
	}
	
	/** Returns the bid size.
	 * @return The bid size.
	 */
	public int getBidSize() {
		return bidSize;
	}
	
	/** Returns the ask price.
	 * @return The ask price.
	 */
	public double getAskPrice() {
		return askPrice;
	}
	
	/** Return the ask size.
	 * @return The ask size.
	 */
	public int getAskSize() {
		return askSize;
	}
	
	/** Returns the price of the last trade during regular trade hours.
	 * @return The price of the last trade during regular trade hours.
	 */
	public double getLastTradePriceTrHrs() {
		return lastTradePriceTrHrs;
	}
	
	/** Returns the price of the last trade.
	 * @return The price of the last trade.
	 */
	public double getLastTradePrice() {
		return lastTradePrice;
	}
	
	/** Returns the quantity of the last trade.
	 * @return The quantity of the last trade.
	 */
	public int getLastTradeSize() {
		return lastTradeSize;
	}
	
	/**
	 * @return the lastTradeTick
	 */
	public String getLastTradeTick() {
		return lastTradeTick;
	}
	
	/** Returns the trade direction.
	 * @return The trade direction.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#tick-type">
	 * The Tick Type section</a>
	 * for all possible values.
	 */
	public String getLastTradeTime() {
		return lastTradeTime;
	}
	
	/** Returns the trading volume.
	 * @return The volume.
	 */
	public long getVolume() {
		return volume;
	}
	
	/** Returns the opening trade price.
	 * @return The opening trade price.
	 */
	public double getOpenPrice() {
		return openPrice;
	}
	
	/** Returns the daily high price.
	 * @return The daily high price.
	 */
	public double getHighPrice() {
		return highPrice;
	}
	
	/** Returns the daily low price.
	 * @return The daily low price.
	 */
	public double getLowPrice() {
		return lowPrice;
	}
	
	/** Whether the quote is delayed (true) or real-time (false)
	 * @return Whether the quote is delayed.
	 * @see {@link Quote} javadoc (read it)
	 */
	public boolean isDelayed() {
		return delay != 0;
	}
	
	/** Returns whether trading in the symbol is currently halted.
	 * @return Whether trading in the symbol is currently halted.
	 */
	public boolean isHalted() {
		return isHalted;
	}
	
	/** Returns the highest price in the past 52 weeks.
	 * @return The highest price in the past 52 weeks.
	 */
	public double getHigh52w() {
		return high52w;
	}
	
	/** Returns the lowest price in the past 52 weeks.
	 * @return The lowest price in the past 52 weeks.
	 */
	public double getLow52w() {
		return low52w;
	}
	
	/** Returns the Volume Weighted Average Price.
	 * @return The VWAP.
	 * @see <a href="https://www.investopedia.com/terms/v/vwap.asp">
	 * The definition and the formula for VWAP on investopedia.com</a> 
	 */
	public double getVWAP() {
		return VWAP;
	}
}
