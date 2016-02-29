package com.stockpriceapp.stockpriceservice.dao;

import java.util.Date;
import java.util.List;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;

/**
 * Data access layer for stock price service
 * @author sandip
 *
 */
public interface StockPriceDAO {

	/**
	 * Saves companyInfo
	 * 
	 * @param companyInfo
	 */
	void saveCompanyInfo(CompanyInfo companyInfo);
	
	/**
	 * Saves a stock quote of a company
	 * @param stockQuote
	 */
	void saveStockQuote(StockQuote stockQuote);
	
	/**
	 * Deletes a companyInfo
	 * 
	 * @param companyInfo
	 */
	void deleteCompanyInfo(CompanyInfo companyInfo);
	
	/**
	 * Returns a companyInfo for a stock symbol
	 * @param symbol
	 * @return
	 */
	CompanyInfo getCompanyInfo(String symbol);
	
	/** 
	 * Saves stock quote for a company in bulk
	 * 
	 * @param quotes
	 */
	void addStockQuotes(List<StockQuote> quotes); 
	
	/**
	 * Returns all the stock quotes available
	 * @param companyInfo
	 * @return List of stock quotes
	 */
	List<StockQuote> getStockQuotes(CompanyInfo companyInfo);
	
	/**
	 * Returns list of companies available in database
	 * 
	 * @return List of company info
	 */
	List<CompanyInfo> getCompanyList();
	
	
	/**
	 * Deletes a company by stock symbol
	 * 
	 * @param symbol
	 */
	void deleteCompanyInfoBySymbol(String symbol);
	
	/**
	 * Returns latest quote available for a company 
	 * @param companyInfo
	 * @return latest quote of a company 
	 */
	double getLatestQuote(CompanyInfo companyInfo);
	
	/**
	 * Returns latest quote date of a company
	 * 
	 * @param companyInfo
	 * @return
	 */
	Date getLatestQuoteDate(CompanyInfo companyInfo);
}
