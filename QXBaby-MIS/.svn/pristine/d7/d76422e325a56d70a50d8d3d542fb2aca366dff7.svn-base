package com.onlineMIS.ORM.DAO.chainS.sales;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;

@Repository
public class ChainStoreSalesOrderDaoImpl extends BaseDAO<ChainStoreSalesOrder> {
	
	/**
	 * to initialize the sales order
	 */
	public void initialize(ChainStoreSalesOrder salesOrder){
		this.getHibernateTemplate().initialize(salesOrder.getProductSet());
	}

}
