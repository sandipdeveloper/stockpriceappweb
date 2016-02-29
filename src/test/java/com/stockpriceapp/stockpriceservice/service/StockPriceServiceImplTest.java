package com.stockpriceapp.stockpriceservice.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.stockpriceapp.stockpriceservice.dao.StockPriceDAO;
import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.facade.StockPricePullingFacade;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.NotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link StockPriceServiceImpl}
 * @author sandip
 *
 */
public class StockPriceServiceImplTest {

	private StockPriceDAO stockPriceDAO;
	private StockPricePullingFacade stockPricePullingFacade;
	private StockPriceServiceImpl stockPriceServiceImpl;
	
	@Before
	public void setup() {
		stockPriceDAO = createMock(StockPriceDAO.class);
		stockPricePullingFacade = createMock(StockPricePullingFacade.class);
		stockPriceServiceImpl = new StockPriceServiceImpl();
		stockPriceServiceImpl.setStockPriceDAO(stockPriceDAO);
		stockPriceServiceImpl.setStockPricePullingFacade(stockPricePullingFacade);
	}
	
	@Test
	public void testCreateCompanyInfo() {
		expect(stockPricePullingFacade.isCompanyExists("AAPL")).andReturn(true);
		expect(stockPriceDAO.getCompanyInfo("AAPL")).andReturn(null);
		stockPriceDAO.saveCompanyInfo(isA(CompanyInfo.class));
		expectLastCall();
		replayAll();
		stockPriceServiceImpl.createCompanyInfo("Apple", "AAPL");
		verifyAll();
	}
	
	@Test (expected = NotFoundException.class)
	public void testCreateCompanyInfoWhenCompanyIsNotListed() {
		expect(stockPricePullingFacade.isCompanyExists("AAPL")).andReturn(false);
		stockPriceServiceImpl.createCompanyInfo("Apple", "AAPL");
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateCompanyInfoWhenRecordAlreadyExists() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		expect(stockPricePullingFacade.isCompanyExists("AAPL")).andReturn(true);
		expect(stockPriceDAO.getCompanyInfo("AAPL")).andReturn(companyInfo);
		stockPriceServiceImpl.createCompanyInfo("Apple", "AAPL");
	}
	
	@Test
	public void testDeleteCompanyInfo() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		stockPriceDAO.deleteCompanyInfo(companyInfo);
		expectLastCall();
		replayAll();
		stockPriceServiceImpl.deleteCompanyInfo(companyInfo);
		verifyAll();
	}
	
	@Test
	public void testGetCompayInfo() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		expect(stockPriceDAO.getCompanyInfo("AAPL")).andReturn(companyInfo);
		replayAll();
		stockPriceServiceImpl.getCompayInfo("AAPL");
		verifyAll();
	}
	
	@Test
	public void testGetCompayList() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<CompanyInfo> companyInfos = new ArrayList<>();
		companyInfos.add(companyInfo);
		expect(stockPriceDAO.getCompanyList()).andReturn(companyInfos);
		replayAll();
		stockPriceServiceImpl.getCompayList();
		verifyAll();
	}
	
	@Test
	public void testAddStockQuotes() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		stockPriceDAO.addStockQuotes(stockQuotes);
		expectLastCall();
		replayAll();
		stockPriceServiceImpl.addStockQuotes(stockQuotes);
		verifyAll();
	}
	
	@Test
	public void testGetStockQuotes() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		List<StockQuote> stockQuotes = new ArrayList<>();
		stockQuotes.add(new StockQuote(companyInfo, new Date(), 4.5));
		expect(stockPriceDAO.getStockQuotes(companyInfo)).andReturn(stockQuotes);
		replayAll();
		List<StockQuote> result = stockPriceServiceImpl.getStockQuotes(companyInfo);
		verifyAll();
		assertEquals(stockQuotes, result);
	}
	
	@Test
	public void testDeleteCompanyInfoBySymbol() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		expect(stockPriceDAO.getCompanyInfo("AAPL")).andReturn(companyInfo);
		stockPriceDAO.deleteCompanyInfo(companyInfo);
		expectLastCall();
		replayAll();
		stockPriceServiceImpl.deleteCompanyInfoBySymbol("AAPL");
		verifyAll();
	}
	
	@Test (expected = NotFoundException.class)
	public void testDeleteCompanyInfoBySymbolWhenCompanyNotFound() {
		expect(stockPriceDAO.getCompanyInfo("AAPL")).andReturn(null);
		stockPriceServiceImpl.deleteCompanyInfoBySymbol("AAPL");
	}
	
	@Test
	public void testGetLatestQuote() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		expect(stockPriceDAO.getLatestQuote(companyInfo)).andReturn(100.0);
		replayAll();
		double result = stockPriceServiceImpl.getLatestQuote(companyInfo);
		verifyAll();
		assertEquals(100.0, result, 0);
	}
	
	@Test
	public void testGetLatestQuoteDate() {
		CompanyInfo companyInfo = new CompanyInfo("Apple", "AAPL");
		expect(stockPriceDAO.getLatestQuoteDate(companyInfo)).andReturn(new Date());
		replayAll();
		Date result = stockPriceServiceImpl.getLatestQuoteDate(companyInfo);
		verifyAll();
		assertNotNull(result);
	}
	
	private void replayAll() {
		replay(stockPriceDAO, stockPricePullingFacade);
	}
	
	private void verifyAll() {
		verify(stockPriceDAO, stockPricePullingFacade);
	}
}
