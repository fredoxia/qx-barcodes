package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.common.Common_util;

@Repository
public class ChainInOutStockDaoImpl extends BaseDAO<ChainInOutStock> {
	/**
	 * get the stock number 
	 */
	public int getProductStock(String barcode, int clientId, boolean cache){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
		
		criteria.add(Restrictions.eq("barcode", barcode));
		criteria.add(Restrictions.eq("clientId", clientId));
		
		criteria.setProjection(Projections.sum("quantity"));
		
		List<Object> result = getByCriteriaProjection(criteria, cache);

        return Common_util.getProjectionSingleValue(result);
	}

	/**
	 * to get the product stock
	 * @param productIds
	 * @return
	 */
	public Map<String, Integer> getProductsInventoryLevel(Set<String> barcodes, int clientId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
		
		criteria.add(Restrictions.in("barcode", barcodes));
		criteria.add(Restrictions.eq("clientId", clientId));
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("barcode"));
		projList.add(Projections.sum("quantity"));
		criteria.setProjection(projList);
		
		List<Object> result = getByCriteriaProjection(criteria, false);
		Map<String, Integer> stockMap = new HashMap<String, Integer>();
		
		//1. to put the result to stock map
		for (Object object : result)
		  if (object != null){
			Object[] recordResult = (Object[])object;
			String barcode = (String)recordResult[0];
			int stock =  (Integer)recordResult[1];
			stockMap.put(barcode, stock);
		  } 

		return stockMap;
	}
	
	/**
	 * 删除initial stock所引入的stock flow
	 * @param clientId
	 */
	public int deleteInitialStockFlow(int clientId){
		String hql = "DELETE FROM ChainInOutStock WHERE clientId =? AND orderId=? AND type=?";
		Object[] values = new Object[]{clientId, ChainInOutStock.DEFAULT_INITIAL_STOCK_ORDER_ID,ChainInOutStock.TYPE_INITIAL_STOCK};
		
		int count = this.executeHQLUpdateDelete(hql, values, false);
		
		return count;
	}
}
