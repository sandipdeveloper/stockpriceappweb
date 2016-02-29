package com.stockpriceapp.stockpriceservice.service;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;

/**
 * Scheduled thread to run on interval and look for new stock data
 * @author sandip
 *
 */
public interface StockPricePullingScheduler {

	/**
	 * Pulls stock prices from qualdl service and inserts/updates in database
	 */
	void pullStockPrices();

	/**
	 * Process company stock data
	 * 
	 * @param companyInfo
	 */
	void processStockData(CompanyInfo companyInfo);
}
