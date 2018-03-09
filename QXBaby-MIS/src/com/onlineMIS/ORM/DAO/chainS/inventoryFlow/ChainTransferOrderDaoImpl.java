package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;




@Repository
public class ChainTransferOrderDaoImpl extends BaseDAO<ChainTransferOrder> {

	public void initialize(ChainTransferOrder chainTransferOrder) {
		if (chainTransferOrder != null){
		   this.getHibernateTemplate().initialize(chainTransferOrder.getProductSet());
		   this.getHibernateTemplate().initialize(chainTransferOrder.getFromChainStore());
		   this.getHibernateTemplate().initialize(chainTransferOrder.getToChainStore());
		}
		
	}

}
