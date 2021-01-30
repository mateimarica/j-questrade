package com.jquestrade;

public class Execution {
	
	private Execution() {}
	
	private String symbol;
	private int symbolId;
	private int quantity;
	private String side;
	private double price;
	private double id;
	private int orderId;
	private int orderChainId;
	private String exchangeExecId;
	private String timestamp;
	private String notes;
	private String venue;
	private double totalCost;
	private double orderPlacementCommission;
	private double commission;
	private double executionFee;
	private double secFee;
	private int canadianExecutionFee;
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @return the side
	 */
	public String getSide() {
		return side;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @return the id
	 */
	public double getId() {
		return id;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @return the orderChainId
	 */
	public int getOrderChainId() {
		return orderChainId;
	}
	/**
	 * @return the exchangeExecId
	 */
	public String getExchangeExecId() {
		return exchangeExecId;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}
	/**
	 * @return the orderPlacementCommission
	 */
	public double getOrderPlacementCommission() {
		return orderPlacementCommission;
	}
	/**
	 * @return the commission
	 */
	public double getCommission() {
		return commission;
	}
	/**
	 * @return the executionFee
	 */
	public double getExecutionFee() {
		return executionFee;
	}
	/**
	 * @return the secFee
	 */
	public double getSecFee() {
		return secFee;
	}
	/**
	 * @return the canadianExecutionFee
	 */
	public int getCanadianExecutionFee() {
		return canadianExecutionFee;
	}
	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	private int parentId;
}
