package com.stockpriceapp.stockpriceservice.facade;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;

/**
 * Stock price pulling facade - talks to the quandl external service
 * @author sandip
 *
 */
public interface StockPricePullingFacade {
	/**
	 * Checks if company exists in quandl repo or not
	 * @param symbol
	 * @return true if exists
	 */
	boolean isCompanyExists(String symbol);
	
	/**
	 * Gets stock quote for a company within date range
	 * @param companyInfo
	 * @param fromDate
	 * @param toDate
	 * @return List of StockQuote
	 * @throws ParseException
	 */
	List<StockQuote> getStockQuotesForCompanyForDateRange(CompanyInfo companyInfo, Date fromDate, Date toDate) throws ParseException;
}