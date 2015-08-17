package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.util.ArrayList;
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
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStockId;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.common.Common_util;

@Repository
public class ChainInitialStockDaoImpl extends BaseDAO<ChainInitialStock> {
	/**
	 * get the stock total number 
	 */
	public int getProductStock(String barcode, int clientId, boolean cache){
		ChainInitialStockId id = new ChainInitialStockId(barcode, clientId);
		ChainInitialStock stock = get(id, true);
		
		if (stock != null)
			return stock.getQuantity();
		else 
			return 0;
	}
	
	/**
	 * to get one chain's stock
	 * @param ChainId
	 * @return
	 */
	public List<ChainInitialStock> getChainStockByClientId(int chainId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainInitialStock.class);

		criteria.add(Restrictions.eq("id.clientId", chainId));
		
		return this.getByCritera(criteria, true);
	}

	public int deleteChainStockByClientId(int clientId) {
		Object[] values = new Object[]{clientId};
		String hql = "delete from ChainInitialStock where id.clientId = ?";
		int deletedQ = this.executeHQLUpdateDelete(hql, values, true);
		
		return deletedQ;
	}

	/**
	 * to get the product stock
	 * @param productIds
	 * @return
	 */
//	public Map<String, Integer> getProductsInventoryLevel(Set<String> barcodes, int clientId) {
//		
//		DetachedCriteria criteria = DetachedCriteria.forClass(ChainInitialStock.class);
//		
//		criteria.add(Restrictions.eq("id.clientId", clientId));
//		criteria.add(Restrictions.in("id.barcode", barcodes));
//		
//		List<ChainInitialStock> result = getByCritera(criteria, true);
//		Map<String, Integer> stockMap = new HashMap<String, Integer>();
//		
//		//1. to put the result to stock map
//		for (ChainInitialStock stock : result)
//		  if (stock != null){
//			stockMap.put(stock.getId().getBarcode(), stock.getQuantity());
//		  } 
//
//		return stockMap;
//	}

}
