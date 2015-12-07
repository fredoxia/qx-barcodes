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
import com.onlineMIS.ORM.entity.chainS.report.ChainAutoRptRepositoty;

@Repository
public class ChainAutoRptRepositoryDaoImpl   extends BaseDAO<ChainAutoRptRepositoty> {
	
	public Map<Integer, List<ChainAutoRptRepositoty>> getRptRepositoryDateMap(){
		Map<Integer, List<ChainAutoRptRepositoty>> resultMap =  new HashMap<Integer, List<ChainAutoRptRepositoty>>();

		for (int rptType : ChainAutoRptRepositoty.RPT_TYPES){
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainAutoRptRepositoty.class);
			criteria.add(Restrictions.eq("rptId", rptType));
			criteria.addOrder(Order.desc("rptDate"));
			
			List<ChainAutoRptRepositoty> rptRepositoties = this.getByCritera(criteria, 0, 10, true);
			resultMap.put(rptType, rptRepositoties);
		}
		
		return resultMap;
	}
}
