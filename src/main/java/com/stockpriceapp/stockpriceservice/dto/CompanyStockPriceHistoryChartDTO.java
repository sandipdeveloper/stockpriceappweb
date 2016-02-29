package com.stockpriceapp.stockpriceservice.dto;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CompanyStockPriceHistoryChartDTO {

	private String companyName;
	private String symbol;
	private ArrayList<ArrayList<Double>> stockPriceHistory;
	
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
	public ArrayList<ArrayList<Double>> getStockPriceHistory() {
		return stockPriceHistory;
	}
	public void setStockPriceHistory(ArrayList<ArrayList<Double>> stockPriceHistory) {
		this.stockPriceHistory = stockPriceHistory;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}
