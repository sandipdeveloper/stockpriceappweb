package com.stockpriceapp.stockpriceservice.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CompanyStockPriceHistoryDTO {

	private String companyName;
	private String symbol;
	private List<StockPriceHistoryDTO> stockPriceHistory;
	
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
	public List<StockPriceHistoryDTO> getStockPriceHistory() {
		return stockPriceHistory;
	}
	public void setStockPriceHistory(List<StockPriceHistoryDTO> stockPriceHistory) {
		this.stockPriceHistory = stockPriceHistory;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}
