package com.stockpriceapp.stockpriceservice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.facade.StockPricePullingFacade;

@Component
public class StockPricePullingSchedulerImpl implements StockPricePullingScheduler {

	private static final Logger logger = Logger.getLogger(StockPricePullingSchedulerImpl.class);
	
	private static final long PULL_INTERVAL = 5000L;

	private static final long INITIAL_DELAY = 2000L;
	
	@Autowired
	private StockPriceService stockPriceService;
	
	@Autowired
	private StockPricePullingFacade stockPricePullingFacade;
	
	private Timer timer = new Timer();

	@PostConstruct
	@Override
	public void pullStockPrices() {		
		
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		    	
		    	logger.log(Priority.INFO, "scheduler running");
		    	
		    	List<CompanyInfo> companyList = stockPriceService.getCompayList(); 
		    	
		    	if(CollectionUtils.isNotEmpty(companyList)) {
		    		
		    		companyList.forEach(company -> processStockData(company));
		    	}
		    }

		}, INITIAL_DELAY, PULL_INTERVAL);
	}
	
	@Override
	public void processStockData(CompanyInfo companyInfo) {

		List<StockQuote> stockQuotes = stockPriceService.getStockQuotes(companyInfo);

		List<StockQuote> stockQuotesObtained = null;
		
		try {
			//if there is no stock quote data
			//then 
			if(CollectionUtils.isEmpty(stockQuotes)) {
				Calendar _fromDate = Calendar.getInstance();
				_fromDate.set(2010, 01, 01);
				stockQuotesObtained = stockPricePullingFacade.getStockQuotesForCompanyForDateRange(companyInfo
						, _fromDate.getTime(), new Date());
				if(CollectionUtils.isNotEmpty(stockQuotesObtained)) {
					stockPriceService.addStockQuotes(stockQuotesObtained);
			    	logger.log(Priority.INFO, "scheduler inserted initial data toDate="+new Date()+", for companyInfo="
			    			+companyInfo);
				}			
			} else {
				Date currentQuoteDate = stockPriceService.getLatestQuoteDate(companyInfo);
				Date today = new Date();
				//if latest stock quote date is not today
				//then try to find what quotes available from today to that 
				//particular day
				if(currentQuoteDate.before(today)) {
					stockQuotesObtained = stockPricePullingFacade.getStockQuotesForCompanyForDateRange(companyInfo
							, currentQuoteDate, today);
					//if the stock data returned still does not have any new quote data
					//then skip updating table
					if(CollectionUtils.isNotEmpty(stockQuotesObtained)
							&& !DateUtils.isSameDay(stockQuotesObtained.get(0).getRecordDate(), currentQuoteDate)) {
						stockPriceService.addStockQuotes(stockQuotesObtained);
				    	logger.log(Priority.INFO, "scheduler inserted new data from="+currentQuoteDate+", to="+today);
					}
				}
			}
		
		} catch(Exception exception) {
			logger.log(Priority.ERROR, exception);
			//do not re-throw, otherwise the scheduler thread will stop and
			//might not be able to re-spawn
		}
	}

	public void setStockPriceService(StockPriceService stockPriceService) {
		this.stockPriceService = stockPriceService;
	}

	public void setStockPricePullingFacade(StockPricePullingFacade stockPricePullingFacade) {
		this.stockPricePullingFacade = stockPricePullingFacade;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}