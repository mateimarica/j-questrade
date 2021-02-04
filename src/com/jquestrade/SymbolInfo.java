package com.jquestrade;

/** Represents information about a symbol/ticker */
public class SymbolInfo {
	private SymbolInfo() {}
	
	private String symbol;
	private int symbolId;
	private double prevDayClosePrice;
	private double highPrice52;
	private double lowPrice52;
	private long averageVol3Months;
	private long averageVol20Days;
	private long outstandingShares;
	private double eps;
	private double pe;
	private double dividend;
	private double yield;
	private String exDate;
	private long marketCap;
	private int tradeUnit;
	private String optionType;
	private String optionDurationType;
	private String optionRoot;
	private Object optionContractDeliverables;
	private String optionExerciseType;
	private String listingExchange;
	private String description;
	private String securityType;
	private String optionExpiryDate;
	private String dividendDate;
	private String optionStrikePrice;
	private boolean isTradable;
	private boolean isQuotable;
	private boolean hasOptions;
	private String currency;
	private MinTick[] minTicks;
	
	/** Represents a min tick. */
	public class MinTick {
		private MinTick() {}
		
		private double pivot;
		private double minTick;
		
		/** Returns the beginning of the interval for a given minimum price increment.
		 * @return The pivot.
		 */
		public double getPivot() {
			return pivot;
		}
		
		/** Returns the minimum price increment.
		 * @return The min Tick.
		 */
		public double getMinTick() {
			return minTick;
		}
	}
	
	private String industrySector;
	private String industryGroup;
	private String industrySubgroup;
	
	/** Returns the stock symbol/ticker.
	 * @return The stock symbol/ticker.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/** Returns the internal unique symbol identifier.
	 * @return The internal unique symbol identifier.
	 */
	public int getSymbolId() {
		return symbolId;
	}
	
	/** Returns the symbol description (e.g., "Microsoft Corp.").
	 * @return The symbol description.
	 */
	public String getDescription() {
		return description;
	}
	
	/** Returns the symbol security type (Eg: "Stock")
	 * @return The symbol security type
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#security-type">
	 * The Security Type section</a>
	 * for all possible values.
	 */
	public String getSecurityType() {
		return securityType;
	}
	
	/** Returns the primary listing exchange of the symbol.
	 * @return The primary listing exchange.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#listing-exchange">
	 * The Listing Exchange section</a>
	 * for all possible values.
	 */
	public String getListingExchange() {
		return listingExchange;
	}
	
	/** Returns whether a symbol is tradable on the platform.
	 * @return Whether a symbol is tradable on the platform.
	 */
	public boolean isTradable() {
		return isTradable;
	}
	
	/** Returns whether a symbol has live market data.
	 * @return Whether a symbol has live market data.
	 */
	public boolean isQuotable() {
		return isQuotable;
	}
	
	/** Returns the symbol currency (ISO format).
	 * @return The symbol currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/** Returns the closing trade price from the previous trading day.
	 * @return The closing trade price from the previous trading day.
	 */
	public double getPrevDayClosePrice() {
		return prevDayClosePrice;
	}

	/** Returns the 52-week high price.
	 * @return The 52-week high price.
	 */
	public double getHighPrice52() {
		return highPrice52;
	}

	/** Returns the 52-week low price.
	 * @return The 52-week low price.
	 */
	public double getLowPrice52() {
		return lowPrice52;
	}

	/** Returns the average trading volume over trailing 3 months.
	 * @return The average trading volume over trailing 3 months.
	 */
	public long getAverageVol3Months() {
		return averageVol3Months;
	}

	/** Returns the average trading volume over trailing 20 days.
	 * @return The average trading volume over trailing 20 days.
	 */
	public long getAverageVol20Days() {
		return averageVol20Days;
	}

	/** Returns the total number of shares outstanding.
	 * @return The total number of shares outstanding.
	 */
	public long getOutstandingShares() {
		return outstandingShares;
	}
	
	/** Returns the trailing 12-month earnings per share.
	 * @return The trailing 12-month earnings per share.
	 */
	public double getEps() {
		return eps;
	}

	/** Returns the trailing 12-month price to earnings ratio.
	 * @return The trailing 12-month price to earnings ratio.
	 */
	public double getPe() {
		return pe;
	}

	/** Returns the dividend amount per share.
	 * @return The dividend amount per share.
	 */
	public double getDividend() {
		return dividend;
	}

	
	/** Returns the dividend yield (dividend / prevDayClosePrice).
	 * @return The dividend yield (dividend / prevDayClosePrice).
	 */
	public double getYield() {
		return yield;
	}

	/** Returns the dividend ex-date.
	 * @return The dividend ex-date.
	 */
	public String getExDate() {
		return exDate;
	}

	/** Returns the market capitalization (outstandingShares * prevDayClosePrice).
	 * @return The market capitalization.
	 */
	public long getMarketCap() {
		return marketCap;
	}

	/** Returns the number of shares of a particular security that is used as the acceptable quantity for trading
	 * @return The trade unit.
	 */
	public int getTradeUnit() {
		return tradeUnit;
	}

	/** Returns the option duration type (e.g., "Weekly").
	 * @return The option duration type.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#option-type">
	 * The Option Type section</a>
	 * for all possible values.
	 */
	public String getOptionType() {
		return optionType;
	}

	/** Returns the option type (e.g., "Call").
	 * @return The option type
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#option-duration-type">
	 * The Option Duration Type section</a>
	 * for all possible values.
	 */
	public String getOptionDurationType() {
		return optionDurationType;
	}

	/** Returns the option root symbol (e.g., "MSFT").
	 * @return The option root symbol
	 */
	public String getOptionRoot() {
		return optionRoot;
	}

	/** Returns the option contract deliverables. Unimplemented.
	 * @return the optionContractDeliverables
	 */
	@Deprecated
	public Object getOptionContractDeliverables() {
		return optionContractDeliverables;
	}

	/** Returns the option exercise style (e.g., "American").
	 * @return The option exercise style.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#option-exercise-type">
	 * The Option Exercise Type section</a>
	 * for all possible values.
	 */
	public String getOptionExerciseType() {
		return optionExerciseType;
	}

	/** Returns the date on which the option expires.
	 * @return The date on which the option expires.
	 */
	public String getOptionExpiryDate() {
		return optionExpiryDate;
	}

	/** Returns the dividend declaration date.
	 * @return The dividend declaration date.
	 */
	public String getDividendDate() {
		return dividendDate;
	}
	
	/** Returns the option strike price
	 * @return the optionStrikePrice
	 */
	public String getOptionStrikePrice() {
		return optionStrikePrice;
	}

	/** Returns whether the symbol is an underlying option.
	 * @return Whether the symbol is an underlying option.
	 */
	public boolean hasOptions() {
		return hasOptions;
	}

	/** Returns the min ticks.
	 * @return The min ticks.
	 */
	public MinTick[] getMinTicks() {
		return minTicks;
	}

	/** Returns the industry sector classification.
	 * @return The industry sector classification.
	 */
	public String getIndustrySector() {
		return industrySector;
	}

	/** Returns the industry group classification.
	 * @return The industry group classification.
	 */
	public String getIndustryGroup() {
		return industryGroup;
	}

	/** Returns the industry subgroup classification.
	 * @return The industry subgroup classification.
	 */
	public String getIndustrySubgroup() {
		return industrySubgroup;
	}
	
}