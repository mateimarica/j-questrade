package com.jquestrade;

public class Balances {
	
	public enum Currency { 
		CAD, 
		USD;
	}
	
	private Balance[] perCurrencyBalances;
	private Balance[] combinedBalances;
	private Balance[] sodPerCurrencyBalances;
	private Balance[] sodCombinedBalances;

	/**
	 * @return the perCurrencyBalances
	 */
	public Balance getPerCurrencyBalances(Currency currency) {
		if(currency == Currency.CAD) {
			return perCurrencyBalances[0];
		} 
		return perCurrencyBalances[1];
	}
	
	/**
	 * @return the combinedBalances
	 */
	public Balance getCombinedBalances(Currency currency) {
		if(currency == Currency.CAD) {
			return combinedBalances[0];
		} 
		return combinedBalances[1];
	}
	
	/**
	 * @return the sodPerCurrencyBalances
	 */
	public Balance getSodPerCurrencyBalances(Currency currency) {
		if(currency == Currency.CAD) {
			return sodPerCurrencyBalances[0];
		} 
		return sodPerCurrencyBalances[1];
	}
	
	/**
	 * @return the sodCombinedBalances
	 */
	public Balance getSodCombinedBalances(Currency currency) {
		if(currency == Currency.CAD) {
			return sodCombinedBalances[0];
		} 
		return sodCombinedBalances[1];
	}
	
}