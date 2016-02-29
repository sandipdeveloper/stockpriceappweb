package com.stockpriceapp.stockpriceservice.facade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.HeaderDefinition;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.SearchRequest;
import com.jimmoores.quandl.SearchResult;
import com.jimmoores.quandl.TabularResult;
import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;

/**
 * Unit test for {@link StockPricePullingFacadeImpl}
 * @author sandip
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({QuandlSession.class, SearchResult.class})
public class StockPricePullingFacadeImplTest {

	private QuandlSession quandlSession;
	private StockPricePullingFacadeImpl stockPricePullingFacadeImpl;
	private SearchResult searchResult;
	
	@Before
	public void setup() {
		quandlSession = mock(QuandlSession.class);
		searchResult = mock(SearchResult.class);
		stockPricePullingFacadeImpl = new StockPricePullingFacadeImpl();
		stockPricePullingFacadeImpl.setSession(quandlSession);
	}
	
	@Test
	public void testIsCompanyExists() throws JSONException {
		SearchRequest searchRequest = new SearchRequest.Builder().withDatabaseCode("WIKI").withQuery("AAPL").withMaxPerPage(1).withPageNumber(1).build();
		Mockito.when(searchResult.getTotalDocuments()).thenReturn(1);
		Mockito.when(quandlSession.search(searchRequest)).thenReturn(searchResult);
		assertFalse(stockPricePullingFacadeImpl.isCompanyExists("AAPL"));
	}
	
	@Test
	public void testGetStockQuotesForCompanyForDateRange() throws ParseException {
		DataSetRequest dataSetRequest = DataSetRequest.Builder.of("AAPL").build();
		List<Row> rows = new ArrayList<>();
		Mockito.when(quandlSession.getDataSet(dataSetRequest)).thenReturn(TabularResult.of(HeaderDefinition.of("column"), rows));
		assertNotNull(stockPricePullingFacadeImpl.getStockQuotesForCompanyForDateRange(new CompanyInfo("Apple", "AAPL"), new Date(), new Date()));
	}
}
