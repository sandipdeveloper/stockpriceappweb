package com.stockpriceapp.stockpriceservice.service;

import java.util.Date;
import java.util.List;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;

/**
 * Service layer for stock price app
 * @author sandip
 *
 */
public interface StockPriceService {
	/**
	 * Creates company info
	 * @param companyName
	 * @param symbol
	 */
	void createCompanyInfo(String companyName, String symbol);
	
	/**
	 * Deletes company info
	 * @param companyInfo
	 */
	void deleteCompanyInfo(CompanyInfo companyInfo);
	/**
	 * Get companyInfo
	 * @param symbol
	 * @return
	 */
	CompanyInfo getCompayInfo(String symbol);
	
	/**
	 * Get companyList
	 * @param symbol
	 * @return
	 */
	List<CompanyInfo> getCompayList();
	
	/**
	 * Adds stock quotes for a company
	 * @param quotes
	 */
	void addStockQuotes(List<StockQuote> quotes);
	
	/**
	 * Get stock quotes for a company
	 * @param companyInfo
	 * @return
	 */
	List<StockQuote> getStockQuotes(CompanyInfo companyInfo);
	
	/**
	 * Delete a company by symbol
	 *
	 */
	void deleteCompanyInfoBySymbol(String symbol); 
	
	/**
	 * Get latest quote for a company
	 * 
	 * @param companyInfo
	 * @return
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
