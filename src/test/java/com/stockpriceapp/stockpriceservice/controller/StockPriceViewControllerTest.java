package com.stockpriceapp.stockpriceservice.controller;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link StockPriceViewController}
 * @author sandip
 *
 */
public class StockPriceViewControllerTest {

	private StockPriceViewController stockPriceViewController;
	
	@Before
	public void setup() {
		stockPriceViewController = new StockPriceViewController();
	}
	
	@Test
	public void testShowAddCompanyPage() {
		String redierct = stockPriceViewController.showAddCompanyPage(new HashMap<>());
		assertEquals("redirect:/pages/add-company.html", redierct);
	}
	
	@Test
	public void testShowDeleteCompanyPage() {
		String redierct = stockPriceViewController.showDeleteCompanyPage(new HashMap<>());
		assertEquals("redirect:/pages/delete-company.html", redierct);
	}
	
	@Test
	public void testListCompaniesPage() {
		String redierct = stockPriceViewController.listCompaniesPage(new HashMap<>());
		assertEquals("redirect:/pages/list-company.html", redierct);
	}
	
	@Test
	public void testCompanyHistoryPage() {
		String redierct = stockPriceViewController.companyHistoryPage(new HashMap<>());
		assertEquals("redirect:/pages/company-history.html", redierct);
	}
}
