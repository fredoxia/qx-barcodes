package com.onlineMIS.unit_testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserStoreRelationship;
import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationItem;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalItemMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.SQLServer.SaleHistory;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.sorter.ChainLevelTwoInventoryItemSort;


public class testChainStoreSales {
	public static  ChainLevelTwoInventoryItem combine(
			ChainLevelTwoInventoryItem inoutItem,
			ChainLevelTwoInventoryItem initialItem) {
//		if (inoutItem != null)
//			return inoutItem.combine(initialItem);
//		else if (initialItem != null)
//			return initialItem.combine(inoutItem);
		
		return null;
	}

	public static void setChainLevelOneInventoryItem(ChainStore chainStore, Set<Integer> keys, Map<Integer, ChainLevelTwoInventoryItem> dataMap, List<Object> src){
	    if (src != null){
			for (Object object: src){
					Object[] object2 = (Object[])object;
					int yearId = Integer.parseInt(object2[0].toString());
					String yearS = String.valueOf(object2[1]);
					Year year = new Year(yearId, yearS);
					
					int quarterId = Integer.parseInt(object2[2].toString());
					String quarterS = String.valueOf(object2[3]);
					Quarter quarter = new Quarter(quarterId, quarterS);
					
					int quantity = Integer.parseInt(object2[4].toString());
					double costTotal = Double.parseDouble(object2[5].toString());
					
					ChainLevelTwoInventoryItem levelOneInventoryItem = new ChainLevelTwoInventoryItem();
					levelOneInventoryItem.setChainStore(chainStore);
					levelOneInventoryItem.setYear(year);
					levelOneInventoryItem.setQuarter(quarter);
					levelOneInventoryItem.setTotalQuantity(quantity);
					levelOneInventoryItem.setTotalCostAmt(Common_util.roundDouble(costTotal,2));
					
					dataMap.put(levelOneInventoryItem.getKey(), levelOneInventoryItem);
					keys.add(levelOneInventoryItem.getKey());
				}
	    }
	}
	
	public static ChainInventoryItem calculateLevelOneSubTotal(
			List<ChainLevelTwoInventoryItem> levelOneInventoryItems, ChainStore chainStore) {
		ChainInventoryItem totalElement = new ChainInventoryItem();
		int totalQ = 0;
		double totalCost = 0;
		
		Map<String, ChainLevelTwoInventoryItem> subTotalMap = new HashMap<String, ChainLevelTwoInventoryItem>();
		if (levelOneInventoryItems != null){
			for (ChainLevelTwoInventoryItem levelOneInventoryItem : levelOneInventoryItems){
				String year = levelOneInventoryItem.getYear().getYear();
				if (subTotalMap.containsKey(year)){
					subTotalMap.get(year).combine(levelOneInventoryItem);
				} else {
					ChainLevelTwoInventoryItem levelOneInventoryItem2 = levelOneInventoryItem.clone();
					levelOneInventoryItem2.setQuarter(null);
					subTotalMap.put(year, levelOneInventoryItem2);
				}
				
				totalQ += levelOneInventoryItem.getTotalQuantity();
				totalCost += levelOneInventoryItem.getTotalCostAmt();
			}
			
			totalElement.setTotalQuantity(totalQ);
			totalElement.setTotalCostAmt(totalCost);
			totalElement.setChainStore(chainStore);
			
			Collection<ChainLevelTwoInventoryItem> subTotals = subTotalMap.values();
			levelOneInventoryItems.addAll(subTotals);
		}
		
		return totalElement;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<ChainLevelTwoInventoryItem> levelOneInventoryItems = new ArrayList<ChainLevelTwoInventoryItem>();
		
	    String sql_in_out = "SELECT c.productBarcode.product.year.year_ID,c.productBarcode.product.year.year,c.productBarcode.product.quarter.quarter_ID,c.productBarcode.product.quarter.quarter_Name, SUM(c.quantity), SUM(c.costTotal) FROM ChainInOutStock c WHERE c.clientId = ? AND c.productBarcode.product.category.category_ID !=? GROUP BY c.productBarcode.product.year.year_ID, c.productBarcode.product.quarter.quarter_ID";
	    String sql_initial = "SELECT c.productBarcode.product.year.year_ID,c.productBarcode.product.year.year,c.productBarcode.product.quarter.quarter_ID,c.productBarcode.product.quarter.quarter_Name, SUM(c.quantity), SUM(c.costTotal) FROM ChainInitialStock c WHERE c.id.clientId = ? AND c.productBarcode.product.category.category_ID !=? GROUP BY c.productBarcode.product.year.year_ID, c.productBarcode.product.quarter.quarter_ID";
	    
	    Query query = session.createQuery(sql_in_out);
	    query.setParameter(0, 2009);
	    query.setParameter(1, Brand.BRAND_NOT_COUNT_INVENTORY[0]);
	    List<Object> in_out = query.list();
	    
	    Query query2 = session.createQuery(sql_initial);
	    query2.setParameter(0, 2009);
	    query2.setParameter(1, Brand.BRAND_NOT_COUNT_INVENTORY[0]);
	    List<Object> initial = query2.list();
	    
	    Map<Integer, ChainLevelTwoInventoryItem> in_outMap = new HashMap<Integer, ChainLevelTwoInventoryItem>();
	    Map<Integer, ChainLevelTwoInventoryItem> initialMap = new HashMap<Integer, ChainLevelTwoInventoryItem>();
	    Set<Integer> keys = new HashSet<Integer>();
	    
	    /**
	     * 1. to set the in out keys
	     */
	    setChainLevelOneInventoryItem(null,keys, in_outMap, in_out); 
	    
	    /**
	     * 2. to set the in out keys
	     */
	    setChainLevelOneInventoryItem(null,keys, initialMap, initial); 
	    
	    for (int key: keys){
	    	ChainLevelTwoInventoryItem inoutItem = in_outMap.get(key);
	    	ChainLevelTwoInventoryItem initialItem = initialMap.get(key);
	    	
	    	ChainLevelTwoInventoryItem item = combine(inoutItem, initialItem);
	    	if (item != null)
	    		levelOneInventoryItems.add(item);
	    }
	    
	    /**
	     * 3. to set the sub total and total elements
	     */
	    ChainInventoryItem inventoryItem = calculateLevelOneSubTotal(levelOneInventoryItems, null);

	    Collections.sort(levelOneInventoryItems, new ChainLevelTwoInventoryItemSort());
	    for (ChainLevelTwoInventoryItem item : levelOneInventoryItems)
	    	System.out.println(item);
	    
	    System.out.println(inventoryItem);
		
		transaction.commit();
		session.close();


	}

}