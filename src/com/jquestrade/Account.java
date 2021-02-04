package com.jquestrade;

/** Represents a trading account. Could be a TFSA, RRSP, cash, etc. 
 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/account-calls/accounts">
 * The account properties documentation</a>
 */
public class Account {
	
	private Account() {}
	
	private String type;
	private String number;
	private String status;
	private boolean isPrimary;
	private boolean isBilling;
	private String clientAccountType;
	private int userId;

	/**	Returns the type of the account (e.g., "Cash", "Margin").
	 * @return The type of account.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#account-type">
	 * The User Account Type section</a> 
	 * for all possible values.
	 */
	public String getType() {
		return type;
	}
	
	/** Returns the eight-digit account number (e.g., "26598145").
	 * @return The eight-digit account number.
	 */
	public String getNumber() {
		return number;
	}
	
	/** Returns the status of the account (e.g., Active).
	 * @return Status of the account.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#account-status">
	 * The Account Status section</a> 
	 * for all possible values.
	 */
	public String getStatus() {
		return status;
	}
	
	/** Returns whether or not this is a primary account for the holder.
	 * @return Whether this is a primary account for the holder.
	 */
	public boolean isPrimary() {
		return isPrimary;
	}
	
	/** Returns whether or not this account is one that gets billed for various 
	 * expenses such as fees, market data, etc.
	 * @return Whether this account is the account that gets billed for expenses/fees.
	 */
	public boolean isBilling() {
		return isBilling;
	}
	
	/** Returns the type of client holding the account (e.g., "Individual").
	 * @return The type of client holding the account.
	 * @see <a href="https://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations#client-account-type">
	 * The client Account Type section</a> 
	 * for all possible values.
	 */
	public String getClientAccountType() {
		return clientAccountType;
	}
	
	/** Returns the internal identifier of the user making the request.
	 * @return The internal identifier of the user making the request.
	 */
	public int getUserId() {
		return userId;
	}
	
	/** Must be default modifier. Set user id after creation. Called from {@link Questrade#getAccounts}*/
	void setUserId(int userId) {
		this.userId = userId;
	}
}
