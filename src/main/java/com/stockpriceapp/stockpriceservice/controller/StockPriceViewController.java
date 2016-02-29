package com.stockpriceapp.stockpriceservice.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for the web pages
 * @author sandip
 *
 */
@Controller
public class StockPriceViewController {

	@RequestMapping(value = {"/stockpriceapp/company/add"}, method = RequestMethod.GET)
	public String showAddCompanyPage(Map<String, Object> model) {
	    return "redirect:/pages/add-company.html";   
	}
	
	@RequestMapping(value = {"/stockpriceapp/company/delete"}, method = RequestMethod.GET)
	public String showDeleteCompanyPage(Map<String, Object> model) {
	    return "redirect:/pages/delete-company.html";   
	}
	
	@RequestMapping(value = {"/stockpriceapp/companies"}, method = RequestMethod.GET)
	public String listCompaniesPage(Map<String, Object> model) {
	    return "redirect:/pages/list-company.html";  
	}
	
	@RequestMapping(value = {"/stockpriceapp/company/chart"}, method = RequestMethod.GET)
	public String companyHistoryPage(Map<String, Object> model) {
	    return "redirect:/pages/company-history.html";  
	}
}

