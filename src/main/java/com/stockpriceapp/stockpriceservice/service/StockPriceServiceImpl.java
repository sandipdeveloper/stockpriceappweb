package com.stockpriceapp.stockpriceservice.service;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockpriceapp.stockpriceservice.dao.StockPriceDAO;
import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.facade.StockPricePullingFacade;

@Service
public class StockPriceServiceImpl implements StockPriceService {

	@Autowired
	private StockPriceDAO stockPriceDAO;
	
	@Autowired
	private StockPricePullingFacade stockPricePullingFacade;
	
	@Transactional
	@Override
	public void createCompanyInfo(String companyName, String symbol) {
		if(!stockPricePullingFacade.isCompanyExists(symbol)){
			throw new NotFoundException("symbol does not match with any valid company listing");
		}
		
		CompanyInfo companyInfo = stockPriceDAO.getCompanyInfo(symbol);
		
		if(companyInfo != null) {
			throw new RuntimeException(new NamingException("a company with same symbol already exists"));
		}
		
		stockPriceDAO.saveCompanyInfo(new CompanyInfo(companyName, symbol));
	}
	
	@Transactional
	@Override
	public void deleteCompanyInfo(CompanyInfo companyInfo) {
		stockPriceDAO.deleteCompanyInfo(companyInfo);
	}

	@Override
	public CompanyInfo getCompayInfo(String symbol) {
		return stockPriceDAO.getCompanyInfo(symbol);
	}

	@Override
	public List<CompanyInfo> getCompayList() {
		return stockPriceDAO.getCompanyList();
	}
	
	@Transactional
	@Override
	public void addStockQuotes(List<StockQuote> quotes) {
		stockPriceDAO.addStockQuotes(quotes);
	}

	@Override
	public List<StockQuote> getStockQuotes(CompanyInfo companyInfo) {
		return stockPriceDAO.getStockQuotes(companyInfo);
	}

	@Override
	public void deleteCompanyInfoBySymbol(String symbol) {
		CompanyInfo companyInfo = stockPriceDAO.getCompanyInfo(symbol);
		if(companyInfo == null) {
			throw new NotFoundException("company listing does not exist");
		}
		stockPriceDAO.deleteCompanyInfo(companyInfo);
	}

	@Override
	public double getLatestQuote(CompanyInfo companyInfo) {
		return stockPriceDAO.getLatestQuote(companyInfo);
	}
	
	@Override
	public Date getLatestQuoteDate(CompanyInfo companyInfo) {
		return stockPriceDAO.getLatestQuoteDate(companyInfo);
	}

	public StockPriceDAO getStockPriceDAO() {
		return stockPriceDAO;
	}

	public void setStockPriceDAO(StockPriceDAO stockPriceDAO) {
		this.stockPriceDAO = stockPriceDAO;
	}

	public StockPricePullingFacade getStockPricePullingFacade() {
		return stockPricePullingFacade;
	}

	public void setStockPricePullingFacade(StockPricePullingFacade stockPricePullingFacade) {
		this.stockPricePullingFacade = stockPricePullingFacade;
	}
}