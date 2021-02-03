package com.jquestrade;

/** Represents an account activity. Could be a cash transactions, dividends, trades, etc. 
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-activities">
 * The activity properties documentation</a>
 */
public class Activity {
	
	private Activity() {}
	
	private String tradeDate;
	private String transactionDate;
	private String settlementDate;
	private String action;
	private String symbol;
	private int symbolId;
	private String description;
	private String currency;
	private double quantity;
	private double price;
	private double grossAmount;
	private double commission;
	private double netAmount;
	private String type;
	
	/** The trade date as a string in ISO 8601 format.
	 * @return The trade date
	 */
	public String getTradeDate() {
		return tradeDate;
	}
	
	/** The transaction date as a string in ISO 8601 format.
	 * @return The transaction date
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	
	/** The settlement date as a string in ISO 8601 format.
	 * @return The settlement date.
	 */
	public String getSettlementDate() {
		return settlementDate;
	}
	
	/** The action.<br>
	 * <u>Example types:</u><br>
	 * Buy (for orders)<br>
	 * Sell (for orders)<br>
	 * CON (for deposits)
	 * @return The action.
	 */
	public String getAction() {
		return action;
	}
	
	/** Returns the stock symbol involved in this activity (if applicable).
	 * @return Returns the stock symbol involved in this activity,
	 * Returns an empty string ({@code ""}) if the activity isn't related to any particular security, 
	 * like a desposit.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/** Returns a numeric stock ID for the particular stock involved in this activity (if applicable).
	 * @return Returns the stock ID for the stock involved in this activity,
	 * Returns {@code 0} if the activity isn't related to any particular security, 
	 * like a desposit.
	 */
	public int getSymbolId() {
		return symbolId;
	}
	
	/** Returns the description of the activity.
	 * @return The description of the activity.
	 */
	public String getDescription() {
		return description;
	}
	
	/** Returns which currency was involved in the activity.
	 * @return Which currency was involved in the activity (<b>CAD</b> or <b>USD</b>).
	 */
	public String getCurrency() {
		return currency;
	}
	
	/** Returns the quantity of shares involved in the activity (if applicable).
	 * @return Returns quantity of shares involved in the activity.
	 * Returns {@code 0} if the activity isn't related to any particular security, 
	 * like a desposit.
	 */
	public double getQuantity() {
		return quantity;
	}
	
	/** Returns the price of the shares involved in the activity (if the activity was an order).
	 * @return Returns The price of the shares involved in the activity
	 * Returns {@code 0} if the activity isn't related to any particular security, 
	 * like a desposit.
	 */
	public double getPrice() {
		return price;
	}
	
	/** Returns the gross amount.
	 * @return Returns the gross amount.
	 * Returns {@code 0} if the activity isn't related to any particular security, 
	 * like a desposit.
	 */
	public double getGrossAmount() {
		return grossAmount;
	}
	
	/** Returns the net change for the commission paid for the activity. 
	 * For example, orders will often charge ECN fees, which are generally a few cents.
	 * @return The commission.
	 */
	public double getCommission() {
		return commission;
	}
	
	/** Returns the net amount for the account. 
	 * For example, deposits and sell orders will have a positive net amount
	 * and withdrawals and buy orders will have negative net amounts.
	 * @return The net amount.
	 */
	public double getNetAmount() {
		return netAmount;
	}
	
	/** Returns the activity type. Possible values: Deposits, Dividends, Trades, etc.
	 * @return The activity type.
	 */
	public String getType() {
		return type;
	}
}