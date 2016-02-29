package com.stockpriceapp.stockpriceservice.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CompanyListDTO {

	private List<StockPriceDTO>  stockPrices;

	public List<StockPriceDTO> getStockPrices() {
		return stockPrices;
	}

	public void setStockPrices(List<StockPriceDTO> stockPrices) {
		this.stockPrices = stockPrices;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}
