package com.onlineMIS.ORM.DAO.headQ.finance;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;

@Repository
public class FinanceBillImpl extends BaseDAO<FinanceBill> {
	
	public void initialize(FinanceBill financeBill){
	      this.getHibernateTemplate().initialize(financeBill.getFinanceBillItemSet());
	}

}
