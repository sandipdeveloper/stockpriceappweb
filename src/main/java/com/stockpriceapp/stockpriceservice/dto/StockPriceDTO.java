package com.stockpriceapp.stockpriceservice.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StockPriceDTO {
	
	private String companyName;
	private String symbol;
	private double latestQuote;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getLatestQuote() {
		return latestQuote;
	}

	public void setLatestQuote(double latestQuote) {
		this.latestQuote = latestQuote;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}