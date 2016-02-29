package com.stockpriceapp.stockpriceservice.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * StockQuote - stock quote information for a company on a particular day
 * @author sandip
 *
 */
public class StockQuote implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9025137585065832566L;

	private long id;	
	private CompanyInfo companyInfo;	
	private Date recordDate;
	private double price;
	
	public StockQuote() {
		
	}
	
	public StockQuote(CompanyInfo companyInfo, Date recordDate, double price) {
		this.companyInfo = companyInfo;
		this.recordDate = recordDate;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}