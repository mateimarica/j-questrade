package com.jquestrade;

/** Represents an order. 
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders">
 * The order properties documentation</a>
 */
public class Order {
	private Order() {}
	
	/** The state of an order. */
	public enum OrderState {
		/** Any order, open or closed. */
		All,
		
		/** Only open orders. */
		Open,
		
		/** Only closed orders. */
		Closed;
	}
	
	private int id;
	private String symbol;
	private int symbolId;
	private int totalQuantity;
	private int openQuantity;
	private Integer filledQuantity;
	private String side;
	private String orderType;
	private double limitPrice;
	private Double stopPrice;
	private boolean isAllOrNone;
	private boolean isAnonoymous;
	private Integer icebergQuantity;
	private Integer minQuantity;
	private Double avgExecPrice;
	private Double lastExecPrice;
	private String source;
	private String timeInForce;
	private String gtdDate;
	private String state;
	private String rejectionReason;
	private int chainId;
	private String creationTime;
	private String updateTime;
	private String notes;
	private String primaryRoute;
	private String secondaryRoute;
	private String orderRoute;
	private String venueHoldingOrder;
	private Double comissionCharged;
	private String exchangeOrderId;
	private boolean isSignificantShareHolder;
	private boolean isInsider;
	private boolean isLimitOffsetInDollar;
	private int userId;
	private Double placementCommission;
	private Object[] legs;
	private String strategyType;
	private Double triggerStopPrice;
	private int orderGroupId;
	private String orderClass;
	private boolean isCrossZero;

	/** Returns the internal order identifier.
	 * @return The internal order identifier.
	 */
	public int getId() {
		return id;
	}
	
	/** Returns the security symbol that follows Questrade symbology (e.g., "TD.TO").
	 * @return The symbol.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/** Returns the internal symbol identifier.
	 * @return The internal symbol identifier.
	 */
	public int getSymbolId() {
		return symbolId;
	}
	
	/** Returns the total number of shares in the order.
	 * @return The total number of shares in the order.
	 */
	public int getTotalQuantity() {
		return totalQuantity;
	}
	
	/** Returns the unfilled portion of the order quantity.
	 * @return The unfilled portion of the order quantity.
	 */
	public int getOpenQuantity() {
		return openQuantity;
	}
	
	/** Returns the filled portion of the order quantity.
	 * @return The filled portion of the order quantity.
	 */
	public Integer getFilledQuantity() {
		return filledQuantity;
	}
	
	/** Returns the client side of the order. 
	 * Example values: Buy, Sell, Short, BTO, etc..
	 * @return The client side of the order to which the execution belongs.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#order-side">
	 * The Client Order Side section</a>
	 * for all possible values.
	 */
	public String getSide() {
		return side;
	}
	
	/** Returns the order type,  (e.g., "Buy-To-Open")
	 * @return The order type.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#order-type">
	 * The Order Type section</a>
	 * for all possible values.
	 */
	public String getOrderType() {
		return orderType;
	}
	
	/** Returns the limit price (if applicable).
	 * @return The limit price.
	 */
	public double getLimitPrice() {
		return limitPrice;
	}
	
	/** Returns the stop price (if applicable).
	 * @return The stop price.
	 */
	public Double getStopPrice() {
		return stopPrice;
	}
	
	/** Returns the all-or-none special instruction.
	 * @return The all-or-none special instruction.
	 */
	public boolean isAllOrNone() {
		return isAllOrNone;
	}
	
	/** Returns the "anonymous" special instruction.
	 * @return The "anonymous" special instruction.
	 */
	public boolean isAnonoymous() {
		return isAnonoymous;
	}
	
	/** Returns the quantity of the iceberg special instruction.
	 * @return The iceberg instruction
	 */
	public Integer getIcebergQuantity() {
		return icebergQuantity;
	}
	
	/** Specifies the minimum special instruction.
	 * @return The minimum special instruction.
	 */
	public Integer getMinQuantity() {
		return minQuantity;
	}
	
	/** Returns the average price of all executions received for this order.
	 * @return The average price of all executions received for this order.
	 */
	public Double getAvgExecPrice() {
		return avgExecPrice;
	}
	
	/** Returns the price of the last execution received for the order in question.
	 * @return the price of the last execution received for the order in question.
	 */
	public Double getLastExecPrice() {
		return lastExecPrice;
	}
	
	/** Returns the source of the order, i.e. what Questrade platform the order the placed on.
	 * Example: The source of an order placed on the mobile app would be "QuestradeIQMobile".
	 * @return The source of the order.
	 */
	public String getSource() {
		return source;
	}
	
	/** Returns the order order time-in-force.
	 * @return The order order time-in-force.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#order-time-in-force">
	 * The Order Time-In-Force section</a>
	 * for all possible values.
	 */
	public String getTimeInForce() {
		return timeInForce;
	}
	
	/** Returns the good-till-date marker and date parameter.
	 * @return The good-till-date marker and date parameter.
	 */
	public String getGtdDate() {
		return gtdDate;
	}
	
	/** Returns the order state.
	 * @return The order state.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#order-state">
	 * The Order State section</a>
	 * for all possible values.
	 */
	public String getState() {
		return state;
	}
	
	/** Returns the human readable order rejection reason message.
	 * @return The order rejection reasonh.
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}
	
	/** Returns the internal identifier of a chain to which the order belongs.
	 * @return The internal identifier of a chain to which the order belongs.
	 */
	public int getChainId() {
		return chainId;
	}
	
	/** Returns the time that the order was created.
	 * @return The time that the order was created.
	 */
	public String getCreationTime() {
		return creationTime;
	}
	
	/** Returns the time of the order's last update.
	 * @return The time of the order's last update.
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	
	/** Returns notes that may have been manually added by Questrade staff.
	 * @return Notes that may have been manually added by Questrade staff.
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
	
	/** Returns the order route name.
	 * @return The order route name.
	 */
	public String getOrderRoute() {
		return orderRoute;
	}
	
	/** Returns the venue where non-marketable portion of the order was booked.
	 * @return The venue where non-marketable portion of the order was booked.
	 */
	public String getVenueHoldingOrder() {
		return venueHoldingOrder;
	}
	
	/** Returns the total commission amount charged for this order.
	 * @return The total commission amount charged for this order.
	 */
	public Double getComissionCharged() {
		return comissionCharged;
	}
	
	/** Returns the identifier assigned to this order by exchange where it was routed.
	 * @return The identifier assigned to this order by exchange where it was routed.
	 */
	public String getExchangeOrderId() {
		return exchangeOrderId;
	}
	
	/** Returns whether or not the user that placed the order is a significant shareholder.
	 * @return Whether the user that placed the order is a significant shareholder.
	 */
	public boolean isSignificantShareHolder() {
		return isSignificantShareHolder;
	}
	
	/** Returns whether or not the user that placed the order is an insider.
	 * @return Whether the user that placed the order is an insider.
	 */
	public boolean isInsider() {
		return isInsider;
	}
	
	/** Returns whether or not the limit offset is specified in dollars (vs. percent).
	 * @return Whether the limit offset is specified in dollars (vs. percent).
	 */
	public boolean isLimitOffsetInDollar() {
		return isLimitOffsetInDollar;
	}
	
	/** Returns the internal identifier of user that placed the order.
	 * @return internal identifier of user that placed the order.
	 */
	public int getUserId() {
		return userId;
	}
	
	/** Returns the commission for placing the order via the Trade Desk over the phone.
	 * @return The commission for placing the order via the Trade Desk over the phone.
	 */
	public Double getPlacementCommission() {
		return placementCommission;
	}
	
	/** No idea what this is. Unimplemented
	 * @return the legs
	 */
	@Deprecated
	public Object[] getLegs() {
		return legs;
	}
	
	/** Returns the multi-leg strategy to which the order belongs.
	 * @return The multi-leg strategy.
	 */
	public String getStrategyType() {
		return strategyType;
	}
	
	/** Returns the stop price at which order was triggered.
	 * @return The stop price at which order was triggered.
	 */
	public Double getTriggerStopPrice() {
		return triggerStopPrice;
	}
	
	/** Returns the internal identifier of the order group.
	 * @return The internal identifier of the order group.
	 */
	public int getOrderGroupId() {
		return orderGroupId;
	}
	/** Returns the Bracket Order class. Possible values: Primary, Profit or Loss.
	 * @return The bracket order class, or {@code null} if not applicable.
	 */
	public String getOrderClass() {
		return orderClass;
	}
	
	/** Don't know what this is, go look it up.
	 * @return  isCrossZero
	 */
	public boolean isCrossZero() {
		return isCrossZero;
	}
}
