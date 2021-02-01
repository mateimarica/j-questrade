package com.jquestrade;

public class Order {
	private Order() {}
	
	int id;
	String symbol;
	int symbolId;
	int totalQuantity;
	int openQuantity;
	Integer filledQuantity;
	String side;
	String orderType;
	double limitPrice;
	Double stopPrice;
	boolean isAllOrNone;
	boolean isAnonoymous;
	Integer icebergQuantity;
	Integer minQuantity;
	Double avgExecPrice;
	Double lastExecPrice;
	String source;
	String timeInForce;
	String gtdDate;
	String state;
	String rejectionReason;
	int chainId;
	String creationTime;
	String updateTime;
	String notes;
	String primaryRoute;
	String secondaryRoute;
	String orderRoute;
	String venueHoldingOrder;
	Double comissionCharged;
	String exchangeOrderId;
	boolean isSignificantShareHolder;
	boolean isInsider;
	boolean isLimitOffsetInDollar;
	int userId;
	Double placementCommission;
	Object[] legs;
	String strategyType;
	Double triggerStopPrice;
	int orderGroupId;
	String orderClass;
	boolean isCrossZero;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
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
	public int getSymbolId() {
		return symbolId;
	}
	/**
	 * @return the totalQuantity
	 */
	public int getTotalQuantity() {
		return totalQuantity;
	}
	/**
	 * @return the openQuantity
	 */
	public int getOpenQuantity() {
		return openQuantity;
	}
	/**
	 * @return the filledQuantity
	 */
	public Integer getFilledQuantity() {
		return filledQuantity;
	}
	/**
	 * @return the side
	 */
	public String getSide() {
		return side;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @return the limitPrice
	 */
	public double getLimitPrice() {
		return limitPrice;
	}
	/**
	 * @return the stopPrice
	 */
	public Double getStopPrice() {
		return stopPrice;
	}
	/**
	 * @return the isAllOrNone
	 */
	public boolean isAllOrNone() {
		return isAllOrNone;
	}
	/**
	 * @return the isAnonoymous
	 */
	public boolean isAnonoymous() {
		return isAnonoymous;
	}
	/**
	 * @return the icebergQuantity
	 */
	public Integer getIcebergQuantity() {
		return icebergQuantity;
	}
	/**
	 * @return the minQuantity
	 */
	public Integer getMinQuantity() {
		return minQuantity;
	}
	/**
	 * @return the avgExecPrice
	 */
	public Double getAvgExecPrice() {
		return avgExecPrice;
	}
	/**
	 * @return the lastExecPrice
	 */
	public Double getLastExecPrice() {
		return lastExecPrice;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @return the timeInForce
	 */
	public String getTimeInForce() {
		return timeInForce;
	}
	/**
	 * @return the gtdDate
	 */
	public String getGtdDate() {
		return gtdDate;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return the rejectionReason
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}
	/**
	 * @return the chainId
	 */
	public int getChainId() {
		return chainId;
	}
	/**
	 * @return the creationTime
	 */
	public String getCreationTime() {
		return creationTime;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @return the primaryRoute
	 */
	public String getPrimaryRoute() {
		return primaryRoute;
	}
	/**
	 * @return the secondaryRoute
	 */
	public String getSecondaryRoute() {
		return secondaryRoute;
	}
	/**
	 * @return the orderRoute
	 */
	public String getOrderRoute() {
		return orderRoute;
	}
	/**
	 * @return the venueHoldingOrder
	 */
	public String getVenueHoldingOrder() {
		return venueHoldingOrder;
	}
	/**
	 * @return the comissionCharged
	 */
	public Double getComissionCharged() {
		return comissionCharged;
	}
	/**
	 * @return the exchangeOrderId
	 */
	public String getExchangeOrderId() {
		return exchangeOrderId;
	}
	/**
	 * @return the isSignificantShareHolder
	 */
	public boolean isSignificantShareHolder() {
		return isSignificantShareHolder;
	}
	/**
	 * @return the isInsider
	 */
	public boolean isInsider() {
		return isInsider;
	}
	/**
	 * @return the isLimitOffsetInDollar
	 */
	public boolean isLimitOffsetInDollar() {
		return isLimitOffsetInDollar;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @return the placementCommission
	 */
	public Double getPlacementCommission() {
		return placementCommission;
	}
	/**
	 * @return the legs
	 */
	public Object[] getLegs() {
		return legs;
	}
	/**
	 * @return the strategyType
	 */
	public String getStrategyType() {
		return strategyType;
	}
	/**
	 * @return the triggerStopPrice
	 */
	public Double getTriggerStopPrice() {
		return triggerStopPrice;
	}
	/**
	 * @return the orderGroupId
	 */
	public int getOrderGroupId() {
		return orderGroupId;
	}
	/**
	 * @return the orderClass
	 */
	public String getOrderClass() {
		return orderClass;
	}
	/**
	 * @return the isCrossZero
	 */
	public boolean isCrossZero() {
		return isCrossZero;
	}
}
