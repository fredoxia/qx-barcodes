package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;


import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;

@Repository
public class ChainInventoryFlowOrderDaoImpl extends BaseDAO<ChainInventoryFlowOrder> {

	/**
	 * to copy the inventory order to another one
	 * @param orderInDB
	 * @return
	 */
	public ChainInventoryFlowOrder copy(ChainInventoryFlowOrder orderInDB) {
		evict(orderInDB);
		
		ChainInventoryFlowOrder order = orderInDB;
		
		order.setId(0);
		
		Set<ChainInventoryFlowOrderProduct> orderProducts = order.getProductSet();
		for (ChainInventoryFlowOrderProduct orderProduct: orderProducts){
			orderProduct.setId(0);
		}
		
		return order;
	}

	public void initialize(ChainInventoryFlowOrder chainInventoryFlowOrder) {
		this.getHibernateTemplate().initialize(chainInventoryFlowOrder.getProductSet());
		this.getHibernateTemplate().initialize(chainInventoryFlowOrder.getToChainStore());
	}
}
