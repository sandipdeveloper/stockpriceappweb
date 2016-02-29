package com.stockpriceapp.stockpriceservice.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.facade.StockPricePullingFacade;
/**
 * Unit test for {@link StockPricePullingSchedulerImpl}
 * @author sandip
 *
 */
public class StockPricePullingSchedulerImplTest {

	private StockPriceService stockPriceService;
	private StockPricePullingFacade stockPricePullingFacade;
	private StockPricePullingSchedulerImpl stockPricePullingSchedulerImpl;
	
	@Before
	public void setup() {
		stockPriceService = createMock(StockPriceService.class);
		stockPricePullingFacade = createMock(StockPricePullingFacade.class);
		stockPricePullingSchedulerImpl = new StockPricePullingSchedulerImpl();
		stockPricePullingSchedulerImpl.setStockPricePullingFacade(stockPricePullingFacade);
		stockPricePullingSchedulerImpl.setStockPriceService(stockPriceService);
	}
	
	@Test
	public void testProcessStockData() throws ParseException {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(null);
		expect(stockPricePullingFacade.getStockQuotesForCompanyForDateRange(eq(companyInfo), isA(Date.class), isA(Date.class)))
		.andReturn(stockQuotes);
		stockPriceService.addStockQuotes(isA(List.class));
		expectLastCall();
		replayAll();
		stockPricePullingSchedulerImpl.processStockData(companyInfo);
		verifyAll();
	}
	
	@Test
	public void testProcessStockDataWhenInitialDataAlreadyThere() throws ParseException {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(stockQuotes);
		expect(stockPriceService.getLatestQuoteDate(companyInfo)).andReturn(new Date());
		expect(stockPricePullingFacade.getStockQuotesForCompanyForDateRange(isA(CompanyInfo.class)
				, isA(Date.class), isA(Date.class))).andReturn(stockQuotes);
		replayAll();
		stockPricePullingSchedulerImpl.processStockData(companyInfo);
		verifyAll();
	}
	
	@Test
	public void testProcessStockDataWhenInitialDataAlreadyThereButAreOld() throws ParseException {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, DateUtils.addDays(new Date(), -10), 4.5));
		
		List<StockQuote> _stockQuotes = new ArrayList<>();
		_stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(stockQuotes);
		expect(stockPriceService.getLatestQuoteDate(companyInfo)).andReturn(DateUtils.addDays(new Date(), -10));
		expect(stockPricePullingFacade.getStockQuotesForCompanyForDateRange(isA(CompanyInfo.class)
				, isA(Date.class), isA(Date.class))).andReturn(_stockQuotes);
		stockPriceService.addStockQuotes(isA(List.class));
		expectLastCall();
		replayAll();
		stockPricePullingSchedulerImpl.processStockData(companyInfo);
		verifyAll();
	}
	
	private void replayAll() {
		replay(stockPriceService, stockPricePullingFacade);
	}
	
	private void verifyAll() {
		verify(stockPriceService, stockPricePullingFacade);
	}
}