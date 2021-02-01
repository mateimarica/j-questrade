package com.jquestrade;

public class Position {
	private String symbol;
	private int symbolId;
	private int openQuantity;
	private int closedQuantity;
	private double currentMarketValue;
	private double currentPrice;
	private double averageEntryPrice;
	private double dayPnl;
	private double closedPnl;
	private double openPnl;
	private double totalCost;
	private boolean isRealTime;
	private boolean isUnderReorg;
	
	
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * @return the symbolId
	 */
	public int getSymbolId() {
		return symbolId;
	}
	/**
	 * @return the openQuantity
	 */
	public int getOpenQuantity() {
		return openQuantity;
	}
	/**
	 * @return the closedQuantity
	 */
	public int getClosedQuantity() {
		return closedQuantity;
	}
	/**
	 * @return the currentMarketValue
	 */
	public double getCurrentMarketValue() {
		return currentMarketValue;
	}
	/**
	 * @return the currentPrice
	 */
	public double getCurrentPrice() {
		return currentPrice;
	}
	/**
	 * @return the averageEntryPrice
	 */
	public double getAverageEntryPrice() {
		return averageEntryPrice;
	}
	/**
	 * @return the dayPnl
	 */
	public double getDayPnl() {
		return dayPnl;
	}
	/**
	 * @return the closedPnl
	 */
	public double getClosedPnl() {
		return closedPnl;
	}
	/**
	 * @return the openPnl
	 */
	public double getOpenPnl() {
		return openPnl;
	}
	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}
	/**
	 * @return the isRealTime
	 */
	public boolean isRealTime() {
		return isRealTime;
	}
	/**
	 * @return the isUnderReorg
	 */
	public boolean isUnderReorg() {
		return isUnderReorg;
	}
}
