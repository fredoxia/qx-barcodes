package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;



import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferLog;




@Repository
public class ChainTransferLogDaoImpl extends BaseDAO<ChainTransferLog> {

	public List<ChainTransferLog> getOrderLogs(int orderId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainTransferLog.class);
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.addOrder(Order.desc("logTime"));
		
		return getByCritera(criteria, 0, 5, true);
	}


}
