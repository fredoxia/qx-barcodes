package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;



import java.util.List;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;




@Repository
public class ChainInventoryFlowOrderProductDaoImpl extends BaseDAO<ChainInventoryFlowOrderProduct> {
	@SuppressWarnings("unchecked")
	public void deleteProducts(final List<Integer> ids) {
		if (ids != null && ids.size() > 0){
			String parameter = "?";
			for (int i =1;i<ids.size();i++)
				parameter +=",?";
			final String hql = "delete from ChainInventoryFlowOrderProduct p where p.id in ("+parameter+")";
			
			Object[] values = new Object[ids.size()];
			for (int i = 0; i < ids.size(); i++){
				values[i] = ids.get(i);
			}
			executeHQLUpdateDelete(hql, values, true);
			
		
		}
	}

}
