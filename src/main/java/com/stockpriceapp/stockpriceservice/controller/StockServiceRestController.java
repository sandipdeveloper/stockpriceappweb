package com.stockpriceapp.stockpriceservice.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;
import com.stockpriceapp.stockpriceservice.dto.CompanyListDTO;
import com.stockpriceapp.stockpriceservice.dto.CompanyStockPriceHistoryChartDTO;
import com.stockpriceapp.stockpriceservice.dto.CompanyStockPriceHistoryDTO;
import com.stockpriceapp.stockpriceservice.dto.StockPriceDTO;
import com.stockpriceapp.stockpriceservice.dto.StockPriceHistoryDTO;
import com.stockpriceapp.stockpriceservice.service.StockPriceService;

/**
 * Controller for REST services
 * @author sandip
 *
 */
@Controller
public class StockServiceRestController {

	private static final String BASE_PATH_COMPANY = "/company";
	private static final String BASE_PATH_COMPANIES = "/companies";
	
	@Autowired
	private StockPriceService stockPriceService;
	
	@RequestMapping(value = BASE_PATH_COMPANY, method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createCompany(HttpServletRequest httpServletRequest) {
		String companyName = httpServletRequest.getParameter("companyName");
		String symbol = httpServletRequest.getParameter("symbol");
		if(StringUtils.isEmpty(companyName) || StringUtils.isEmpty(symbol)) {
			throw new BadRequestException("Please provide valid companyName and symbol");
		}
		stockPriceService.createCompanyInfo(companyName, symbol.toUpperCase());
	}
	
	@RequestMapping(value = BASE_PATH_COMPANY+"/{symbol}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void deleteCompany(@PathVariable String symbol) {
		stockPriceService.deleteCompanyInfoBySymbol(symbol);
	}	
	
	@RequestMapping(value = BASE_PATH_COMPANIES, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody CompanyListDTO getAllCompanies() {
		CompanyListDTO companyListDTO = new CompanyListDTO();
		companyListDTO.setStockPrices(new ArrayList<StockPriceDTO>());
		List<CompanyInfo> companyList = stockPriceService.getCompayList();
		if(CollectionUtils.isNotEmpty(companyList)) {
			companyList.forEach(company -> {
				StockPriceDTO stockPriceDTO = new StockPriceDTO();
				stockPriceDTO.setCompanyName(company.getName());
				stockPriceDTO.setSymbol(company.getSymbol());
				stockPriceDTO.setLatestQuote(stockPriceService.getLatestQuote(company));
				companyListDTO.getStockPrices().add(stockPriceDTO);
			});
		}
		return companyListDTO;
	}
	
	@RequestMapping(value = BASE_PATH_COMPANY+"/{symbol}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody CompanyStockPriceHistoryDTO getCompanyHistory(@PathVariable String symbol) {
		
		CompanyInfo companyInfo = stockPriceService.getCompayInfo(symbol);
		
		if(companyInfo == null) {
			throw new NotFoundException("company not found");
		}
		
		CompanyStockPriceHistoryDTO companyStockPriceHistoryDTO = new CompanyStockPriceHistoryDTO();
		companyStockPriceHistoryDTO.setCompanyName(companyInfo.getName());
		companyStockPriceHistoryDTO.setSymbol(symbol);
		companyStockPriceHistoryDTO.setStockPriceHistory(new ArrayList<>());
		
		List<StockQuote> stockQuotes = stockPriceService.getStockQuotes(companyInfo);
		
		if(CollectionUtils.isEmpty(stockQuotes)) {
			throw new NotFoundException("company stock prices not available"
					+ ", please try in a few minutes");
		}
		
		stockQuotes.forEach(stockQuote -> {
			StockPriceHistoryDTO stockPriceHistoryDTO = new StockPriceHistoryDTO();
			stockPriceHistoryDTO.setDateOfQuote(stockQuote.getRecordDate());
			stockPriceHistoryDTO.setQuote(stockQuote.getPrice());
			companyStockPriceHistoryDTO.getStockPriceHistory().add(stockPriceHistoryDTO);
		});

		return companyStockPriceHistoryDTO;
	}
	
	@RequestMapping(value = BASE_PATH_COMPANY+"/chart/{symbol}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody CompanyStockPriceHistoryChartDTO getCompanyHistoryInChartFormat(@PathVariable String symbol) {
		
		CompanyInfo companyInfo = stockPriceService.getCompayInfo(symbol);
		
		if(companyInfo == null) {
			throw new NotFoundException("company not found");
		}
		
		CompanyStockPriceHistoryChartDTO companyStockPriceHistoryChartDTO = new CompanyStockPriceHistoryChartDTO();
		companyStockPriceHistoryChartDTO.setCompanyName(companyInfo.getName());
		companyStockPriceHistoryChartDTO.setSymbol(symbol);
		companyStockPriceHistoryChartDTO.setStockPriceHistory(new ArrayList<>());
		
		List<StockQuote> stockQuotes = stockPriceService.getStockQuotes(companyInfo);
		
		if(CollectionUtils.isEmpty(stockQuotes)) {
			throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, "company stock prices not available"
					+ ", please try in a few minutes");
		}
		
		stockQuotes.forEach(stockQuote -> {
			ArrayList<Double> arrayList = new ArrayList<>();
			arrayList.add((double) stockQuote.getRecordDate().getTime());
			arrayList.add(stockQuote.getPrice());
			companyStockPriceHistoryChartDTO.getStockPriceHistory().add(arrayList);
		});

		Collections.sort(companyStockPriceHistoryChartDTO.getStockPriceHistory(), new Comparator<ArrayList<Double>>() {    
	        @Override
	        public int compare(ArrayList<Double> o1, ArrayList<Double> o2) {
	            return o1.get(0).compareTo(o2.get(0));
	        }               
		});
		
		return companyStockPriceHistoryChartDTO;
	}

	public void setStockPriceService(StockPriceService stockPriceService) {
		this.stockPriceService = stockPriceService;
	}
}