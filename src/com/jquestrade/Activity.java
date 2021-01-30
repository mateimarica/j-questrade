package com.jquestrade;

public class Activity {
	
	private Activity() {}
	
	private String tradeDate;
	private String transactionDate;
	private String settlementDate;
	private String action;
	private String symbol;
	private String symbolId;
	private String description;
	private String currency;
	private String quantity;
	private String price;
	private String grossAmount;
	private String commission;
	private String netAmount;
	private String type;
	
	/**
	 * @return the tradeDate
	 */
	public String getTradeDate() {
		return tradeDate;
	}
	
	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	
	/**
	 * @return the settlementDate
	 */
	public String getSettlementDate() {
		return settlementDate;
	}
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * @return the symbolId
	 */
	public String getSymbolId() {
		return symbolId;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	
	/**
	 * @return the grossAmount
	 */
	public String getGrossAmount() {
		return grossAmount;
	}
	
	/**
	 * @return the commission
	 */
	public String getCommission() {
		return commission;
	}
	
	/**
	 * @return the netAmount
	 */
	public String getNetAmount() {
		return netAmount;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
}