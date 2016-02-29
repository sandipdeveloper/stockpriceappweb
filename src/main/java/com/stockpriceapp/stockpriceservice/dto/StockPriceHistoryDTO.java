package com.stockpriceapp.stockpriceservice.dto;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StockPriceHistoryDTO {

	private Date dateOfQuote;
	private double quote;
	public Date getDateOfQuote() {
		return dateOfQuote;
	}
	public void setDateOfQuote(Date dateOfQuote) {
		this.dateOfQuote = dateOfQuote;
	}
	public double getQuote() {
		return quote;
	}
	public void setQuote(double quote) {
		this.quote = quote;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}
