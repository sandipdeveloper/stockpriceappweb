package com.stockpriceapp.stockpriceservice.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Frequency;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.SearchRequest;
import com.jimmoores.quandl.SearchResult;
import com.jimmoores.quandl.TabularResult;
import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;

@Component
public class StockPricePullingFacadeImpl implements StockPricePullingFacade {

	private static final int CLOSE_COLUMN = 3;
	private static final String DATE_FORMT = "YYYY-MM-DD";	
	private static final String DATABASE_CODE = "WIKI";
	
	@Value("${quandl.api.key}")
	private String apiKey;
	
	private QuandlSession session = null;
	
	@PostConstruct
	public void setSession() {
		session = QuandlSession.create(apiKey);
	}
	
	@Override
	public boolean isCompanyExists(String symbol) {
		boolean stockFound = false;
		SearchResult searchResult = session.search(new SearchRequest.Builder().withDatabaseCode(DATABASE_CODE).withQuery(symbol).withMaxPerPage(1).build());
		if(searchResult != null && searchResult.getTotalDocuments() > 0) {
			stockFound = true;
		}
		return stockFound;
	}

	@Override
	public List<StockQuote> getStockQuotesForCompanyForDateRange(CompanyInfo companyInfo, Date fromDate, Date toDate)
			throws ParseException {
		List<StockQuote> stockQuotes = new ArrayList<>();
		Calendar _fromDate = Calendar.getInstance();
		_fromDate.setTime(fromDate);
		Calendar _toDate = Calendar.getInstance();
		_toDate.setTime(toDate);
    	TabularResult tabularResult = session.getDataSet(
    	  DataSetRequest.Builder
    	    .of(DATABASE_CODE+"/"+companyInfo.getSymbol())
    	    .withFrequency(Frequency.DAILY)
    	    .withColumn(CLOSE_COLUMN)
    	    .withEndDate(LocalDate.of(_toDate.get(Calendar.YEAR), _toDate.get(Calendar.MONTH), _toDate.get(Calendar.DATE)))
    	    .withStartDate(LocalDate.of(_fromDate.get(Calendar.YEAR), _fromDate.get(Calendar.MONTH), _fromDate.get(Calendar.DATE)))
    	    .build());

    	if(tabularResult != null) {
	    	Iterator<Row> iterator = tabularResult.iterator();
	    	
	    	while(iterator.hasNext()) {
	    		Row row = iterator.next();
	    		stockQuotes.add(new StockQuote(companyInfo
	    				, new SimpleDateFormat(DATE_FORMT).parse(row.getString(0))
	    				, row.getDouble(1)));
	    	}
    	}
    	return stockQuotes;
    }

	public void setSession(QuandlSession session) {
		this.session = session;
	}
}
