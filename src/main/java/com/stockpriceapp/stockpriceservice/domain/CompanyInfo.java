package com.stockpriceapp.stockpriceservice.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompanyInfo - comnay information associated with a stock quote
 * @author sandip
 *
 */
public class CompanyInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4906786991460148912L;
	
	private long id;
	private String name;	
	private String symbol;
	
	public CompanyInfo() {
		
	}
	
	public CompanyInfo(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	}
}