package com.stockpriceapp.stockpriceservice.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.stockpriceapp.stockpriceservice.domain.CompanyInfo;
import com.stockpriceapp.stockpriceservice.domain.StockQuote;

public class StockPriceDAOImpl extends HibernateDaoSupport implements StockPriceDAO {

	@Transactional
	public void saveCompanyInfo(CompanyInfo companyInfo) {
		getHibernateTemplate().save(companyInfo);
	}

	@Transactional
	public void saveStockQuote(StockQuote stockQuote) {
		getHibernateTemplate().save(stockQuote);
	}
	
	@Transactional
	public void deleteCompanyInfo(CompanyInfo companyInfo) {
		getHibernateTemplate().delete(companyInfo);
	}

	@SuppressWarnings("unchecked")
	public CompanyInfo getCompanyInfo(final String symbol) {
		return (CompanyInfo) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery("from CompanyInfo where symbol = ?");
                query.setString(0, symbol);
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
	}

	@Transactional
	public void addStockQuotes(List<StockQuote> quotes) {
		quotes.forEach(quote -> getHibernateTemplate().saveOrUpdate(quote));
	}

	@SuppressWarnings("unchecked")
	public List<StockQuote> getStockQuotes(CompanyInfo companyInfo) {
		return (List<StockQuote>) getHibernateTemplate().find("from StockQuote where companyInfo = ?", companyInfo);
	}

	public List<CompanyInfo> getCompanyList() {
		return (List<CompanyInfo>) getHibernateTemplate().loadAll(CompanyInfo.class);
	}

	@Override
	public void deleteCompanyInfoBySymbol(String symbol) {
		getHibernateTemplate().bulkUpdate("delete from CompanyInfo where symbol="+symbol);
	}

	@Override
	public double getLatestQuote(final CompanyInfo companyInfo) {
		@SuppressWarnings("unchecked")
		StockQuote stockQuote = (StockQuote) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery("from StockQuote where (id, recordDate) in "
                		+ "(select id, max(recordDate) from StockQuote where companyInfo = ? group by id)");
                query.setEntity(0, companyInfo);
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
		
		if(stockQuote != null) {
			return stockQuote.getPrice();
		}
		
		return 0;
	}

	@Override
	public Date getLatestQuoteDate(CompanyInfo companyInfo) {
		@SuppressWarnings("unchecked")
		StockQuote stockQuote = (StockQuote) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery("from StockQuote where (id, recordDate) in "
                		+ "(select id, max(recordDate) from StockQuote where companyInfo = ? group by id)");
                query.setEntity(0, companyInfo);
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
		
		if(stockQuote != null) {
			return stockQuote.getRecordDate();
		}
		
		return null;
	}
}