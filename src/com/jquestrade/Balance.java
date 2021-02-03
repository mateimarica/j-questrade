package com.jquestrade;

/** Represents a per-currency or combined balances for a specified account. */
public class Balance {
	
	private Balance() {}
	
	private String currency;
	private double cash;
	private double marketValue;
	private double totalEquity;
	private double buyingPower;
	private double maintenanceExcess;
	private boolean isRealTime;
	
	/** Returns this balance's currency.
	 * @return Which currency was involved in the activity (<b>CAD</b> or <b>USD</b>).
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#account-status">
	 * Currency section</a>
	 * for all possible values.
	 */
	public String getCurrency() {
		return currency;
	}
	
	/** Returns how much cash (non-invested money) is in this balance.
	 * @return How much cash is in this balance.
	 */
	public double getCash() {
		return cash;
	}
	
	/** Returns the market value of all securities in the account <b>in this balance's currency.
	 * @return The market value of all securities in the account in this balance's currency.
	 */
	public double getMarketValue() {
		return marketValue;
	}
	
	/** Returns the total equity for this balance (cash + market value).
	 * @return The total equity for this balance.
	 */
	public double getTotalEquity() {
		return totalEquity;
	}
	/** Returns the buying power for that particular currency side of the account.
	 * @return The buying power for that particular currency side of the account.
	 */
	public double getBuyingPower() {
		return buyingPower;
	}
	
	/** Returns the maintenance excess for that particular currency side of the account.
	 * @return The maintenance excess
	 */
	public double getMaintenanceExcess() {
		return maintenanceExcess;
	}
	
	/** Whether or not real-time data was used to calculate this balance's values.
	 * @return Whether real-time data was used to calculate this balance's values.
	 */
	public boolean isRealTime() {
		return isRealTime;
	}
	
	
}
