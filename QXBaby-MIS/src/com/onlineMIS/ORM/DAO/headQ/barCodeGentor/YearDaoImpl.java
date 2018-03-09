package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

@Repository
public class YearDaoImpl extends BaseDAO<Year>{
//	@SuppressWarnings("unchecked")
//	public Year load(final int id){
//		return (Year)this.getHibernateTemplate().execute(new HibernateCallback(){
//            public Object doInHibernate(Session session) throws HibernateException,SQLException{
//            	Year year=(Year)session.load(Year.class,id);
//                Hibernate.initialize(year);
//                return year;
//           }
//     });
//	}
//	
//	public List<Year> loadAll(){
//		return this.getHibernateTemplate().loadAll(Year.class);
//	}
	
	public List<Year> getYear(List<String> years){
		DetachedCriteria criteria = DetachedCriteria.forClass(Year.class);
		criteria.add(Restrictions.in("year", years));
		
		return this.getByCritera(criteria, true);
	}
	
	public Map<String, Year> getYearNameMap(){
		List<Year> years = getAll(true);
		Map<String, Year> yearMap = new HashMap<String, Year>();
		for (Year year: years){
			yearMap.put(year.getYear().trim(), year);
		}
		
		return yearMap;
	}

	public List<Year> getLatestYears() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Year.class);
		criteria.addOrder(Order.desc("year"));

		
		return this.getByCritera(criteria, 0, 3, true);
	}

}
