package com.onlineMIS.ORM.DAO.headQ.preOrder;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;

@Repository
public class PreOrderDaoImpl extends BaseDAO<CustPreOrder> {
	
	public void initialize(CustPreOrder custPreOrder){
	      this.getHibernateTemplate().initialize(custPreOrder.getProductSet());
	}

}
