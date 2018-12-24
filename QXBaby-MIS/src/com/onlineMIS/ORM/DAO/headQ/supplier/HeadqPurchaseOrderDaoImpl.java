package com.onlineMIS.ORM.DAO.headQ.supplier;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;

import supplier.HeadqPurchaseOrder;

@Repository
public class HeadqPurchaseOrderDaoImpl extends BaseDAO<HeadqPurchaseOrder> {
	
	public HeadqPurchaseOrder getById(int id, boolean beInitialized){
		HeadqPurchaseOrder order = this.get(id, true);
		
		if (beInitialized)
			initialize(order);
		
		return order;
	}
	
	public void initialize(HeadqPurchaseOrder headqPurchaseOrder){
	      this.getHibernateTemplate().initialize(headqPurchaseOrder.getProductSet());
	}

}
