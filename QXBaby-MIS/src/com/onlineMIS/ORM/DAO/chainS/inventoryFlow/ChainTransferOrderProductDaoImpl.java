package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderProduct;




@Repository
public class ChainTransferOrderProductDaoImpl extends BaseDAO<ChainTransferOrderProduct> {

	public void deleteProducts(int orderId, List<Integer> ids) {
		if (ids != null && ids.size() > 0){
			String parameter = "?";
			for (int i =1;i<ids.size();i++)
				parameter +=",?";
			final String hql = "DELETE FROM ChainTransferOrderProduct p WHERE transferOrder.id=? AND productBarcode.id IN ("+parameter+")";
			
			Object[] values = new Object[ids.size() + 1];
			values[0] = orderId;
			for (int i = 0; i < ids.size(); i++){
				values[i + 1] = ids.get(i);
			}
			executeHQLUpdateDelete(hql, values, true);
			
		
		}
		
	}

}
