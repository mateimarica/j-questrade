package com.jquestrade;

public class Account {
	private String type;
	private String number;
	private String status;
	private boolean isPrimary;
	private boolean isBilling;
	private String clientAccountType;
	private int userId;
	
	
	public String getType() {
		return type;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getStatus() {
		return status;
	}
	
	public boolean isPrimary() {
		return isPrimary;
	}
	
	public boolean isBilling() {
		return isBilling;
	}
	
	public String getClientAccountType() {
		return clientAccountType;
	}
	
	public int getUserId() {
		return userId;
	}
}
