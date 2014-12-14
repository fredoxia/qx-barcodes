package com.onlineMIS.ORM.DAO.chainS.report;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;

@Repository
public class ChainBatchRptRepositotyDaoImpl   extends BaseDAO<ChainBatchRptRepositoty> {
	
	public Map<Integer, List<ChainBatchRptRepositoty>> getRptRepositoryDateMap(){
		Map<Integer, List<ChainBatchRptRepositoty>> resultMap =  new HashMap<Integer, List<ChainBatchRptRepositoty>>();

		for (int rptType : ChainBatchRptRepositoty.RPT_TYPES){
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainBatchRptRepositoty.class);
			criteria.add(Restrictions.eq("rptId", rptType));
			criteria.addOrder(Order.desc("rptDate"));
			
			List<ChainBatchRptRepositoty> rptRepositoties = this.getByCritera(criteria, 0, 10, true);
			resultMap.put(rptType, rptRepositoties);
		}
		
		return resultMap;
	}
	
	public ChainBatchRptRepositoty getByPk(int rptType, java.sql.Date rptDate){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainBatchRptRepositoty.class);
		criteria.add(Restrictions.eq("rptId", rptType));
		criteria.add(Restrictions.eq("rptDate", rptDate));
		
		List<ChainBatchRptRepositoty> rptRepositoties = this.getByCritera(criteria, 0, 10, true);
		if (rptRepositoties.size() > 0)
			return rptRepositoties.get(0);
		else 
			return null;
	}
}
