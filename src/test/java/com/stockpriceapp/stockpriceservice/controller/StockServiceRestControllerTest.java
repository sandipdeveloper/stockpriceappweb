package com.stockpriceapp.stockpriceservice.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.dto.CompanyListDTO;
import com.stockpriceapp.stockpriceservice.dto.CompanyStockPriceHistoryChartDTO;
import com.stockpriceapp.stockpriceservice.dto.CompanyStockPriceHistoryDTO;
import com.stockpriceapp.stockpriceservice.service.StockPriceService;

/**
 * unit test for {@link StockServiceRestController}
 * @author sandip
 *
 */
public class StockServiceRestControllerTest {

	private StockPriceService stockPriceService;
	private StockServiceRestController stockServiceRestController;
	
	@Before
	public void before() {
		stockPriceService = createMock(StockPriceService.class);
		stockServiceRestController = new StockServiceRestController();
		stockServiceRestController.setStockPriceService(stockPriceService);
	}
	
	@Test
	public void testCreateCompany() {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		httpServletRequest.setParameter("companyName", "Apple");
		httpServletRequest.setParameter("symbol", "AAPL");
		stockPriceService.createCompanyInfo("Apple", "AAPL");
		expectLastCall();
		replayAll();
		stockServiceRestController.createCompany(httpServletRequest);
		verifyAll();
	}
	
	@Test (expected = BadRequestException.class)
	public void testCreateCompanyWithoutCompanyName() {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		httpServletRequest.setParameter("symbol", "AAPL");
		stockServiceRestController.createCompany(httpServletRequest);
	}
	
	@Test (expected = BadRequestException.class)
	public void testCreateCompanyWithoutCompanySymbol() {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		httpServletRequest.setParameter("symbol", "AAPL");
		stockServiceRestController.createCompany(httpServletRequest);
	}
	
	@Test
	public void testDeleteCompany() {
		stockPriceService.deleteCompanyInfoBySymbol("AAPL");
		expectLastCall();
		replayAll();
		stockServiceRestController.deleteCompany("AAPL");
		verifyAll();
	}
	
	@Test
	public void testGetAllCompanies() {
		List<CompanyInfo> companyInfos = new ArrayList<>();
		companyInfos.add(new CompanyInfo("Apple", "AAPL"));
		companyInfos.add(new CompanyInfo("Microsoft", "MSFT"));
		expect(stockPriceService.getCompayList()).andReturn(companyInfos);
		for(CompanyInfo companyInfo : companyInfos) {
			expect(stockPriceService.getLatestQuote(companyInfo)).andReturn(2.5);
		}
		replayAll();
		CompanyListDTO companyListDTO = stockServiceRestController.getAllCompanies();
		verifyAll();
		assertNotNull(companyListDTO);
		assertTrue(CollectionUtils.isNotEmpty(companyListDTO.getStockPrices()));
	}
	
	@Test
	public void testGetCompanyHistory() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(companyInfo);
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(stockQuotes);
		replayAll();
		CompanyStockPriceHistoryDTO companyStockPriceHistoryDTO = stockServiceRestController.getCompanyHistory("AAPL");
		verifyAll();
		assertNotNull(companyStockPriceHistoryDTO);
		assertTrue(CollectionUtils.isNotEmpty(companyStockPriceHistoryDTO.getStockPriceHistory()));
	}
	
	@Test (expected = NotFoundException.class)
	public void testGetCompanyHistoryWhenNoCompanyFound() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(null);
		stockServiceRestController.getCompanyHistory("AAPL");
	}
	
	@Test (expected = NotFoundException.class)
	public void testGetCompanyHistoryWhenStockDataNotFound() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(companyInfo);
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(null);
		stockServiceRestController.getCompanyHistory("AAPL");
	}
	
	@Test
	public void testGetCompanyHistoryInChartFormat() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(companyInfo);
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(stockQuotes);
		replayAll();
		CompanyStockPriceHistoryChartDTO companyStockPriceHistoryDTO = stockServiceRestController.getCompanyHistoryInChartFormat("AAPL");
		verifyAll();
		assertNotNull(companyStockPriceHistoryDTO);
		assertTrue(CollectionUtils.isNotEmpty(companyStockPriceHistoryDTO.getStockPriceHistory()));
	}
	
	@Test (expected = NotFoundException.class)
	public void testGetCompanyHistoryInChartFormatWhenNoCompanyFound() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(null);
		stockServiceRestController.getCompanyHistoryInChartFormat("AAPL");
	}
	
	@Test (expected = NotFoundException.class)
	public void testGetCompanyHistoryInChartFormatWhenStockDataNotFound() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceService.getCompayInfo("AAPL")).andReturn(companyInfo);
		expect(stockPriceService.getStockQuotes(companyInfo)).andReturn(null);
		stockServiceRestController.getCompanyHistoryInChartFormat("AAPL");
	}
	
	private void replayAll() {
		replay(stockPriceService);
	}
	
	private void verifyAll() {
		verify(stockPriceService);
	}
}
