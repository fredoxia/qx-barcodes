package com.onlineMIS.ORM.DAO.chainS.report;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklyHotBrand;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklySales;
import com.onlineMIS.ORM.entity.chainS.sales.ChainDailySales;

@Repository
public class ChainWeeklySalesDaoImpl  extends BaseDAO<ChainWeeklySales> {
	public ChainWeeklySales getWeeklyRankById(java.sql.Date date, int chainId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainWeeklySales.class);
		criteria.add(Restrictions.eq("reportDate", date));
		criteria.add(Restrictions.eq("chainStore.chain_id", chainId));
		
		List<ChainWeeklySales> weeklySales = this.getByCritera(criteria, true);
		if (weeklySales != null && weeklySales.size() > 0){
			return weeklySales.get(0);
		} else 
			return null;
	}
}
