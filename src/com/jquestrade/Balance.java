package com.jquestrade;

public class Balance {
	private String currency;
	private double cash;
	private double marketValue;
	private double totalEquity;
	private double buyingPower;
	private double maintenanceExcess;
	private boolean isRealTime;
	
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @return the cash
	 */
	public double getCash() {
		return cash;
	}
	/**
	 * @return the marketValue
	 */
	public double getMarketValue() {
		return marketValue;
	}
	/**
	 * @return the totalEquity
	 */
	public double getTotalEquity() {
		return totalEquity;
	}
	/**
	 * @return the buyingPower
	 */
	public double getBuyingPower() {
		return buyingPower;
	}
	/**
	 * @return the maintenanceExcess
	 */
	public double getMaintenanceExcess() {
		return maintenanceExcess;
	}
	/**
	 * @return the isRealTime
	 */
	public boolean isRealTime() {
		return isRealTime;
	}
	
	
}
