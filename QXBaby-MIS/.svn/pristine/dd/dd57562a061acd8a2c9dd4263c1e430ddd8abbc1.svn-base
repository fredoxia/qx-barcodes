package com.onlineMIS.ORM.DAO.headQ.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.ClientDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.SaleHistoryDAOImpl;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.SQLServer.SaleHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.loggerLocal;

@Repository
public class InventoryOrderDAOImpl extends BaseDAO<InventoryOrder> {
    @Autowired
    private ClientDAOImpl clientImpl;    
    
    @Autowired
    private SaleHistoryDAOImpl saleHistoryImpl;
    
	@SuppressWarnings("unchecked")
	public List<InventoryOrder> search(DetachedCriteria criteria) {
		List<InventoryOrder> orderList= new ArrayList<InventoryOrder>();
		
	    orderList = getByCritera(criteria,false);

		return orderList;
	}

	public InventoryOrder retrieveOrder(int order_ID) {
        InventoryOrder order =null;

        order = get(order_ID, false);
        
        if (order != null)
            initialize(order);

		return order;
	}
	
	/**
	 * to calculate the statics of the quantity of the products bought before for the customer
	 * @param barcode
	 * @param customerid
	 * @return
	 */
	public int getQuantityBefore(final String barcode,final int client_id){
		long quantity = 0;
		/**
		 * to get the quantity from local db firlsty
		 */
//		try{
//			quantity = (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
//                public Object doInHibernate(Session session) throws HibernateException,SQLException{
//                	String HQL = "select sum(op.quantity) from InventoryOrderProduct op where op.product.barcode=:barcode and op.order.client_id =:client_id and op.order.order_Status = :order_status";
//                	
//                	Query query=session.createQuery(HQL);
//                	query.setInteger("client_id", client_id);
//                	query.setString("barcode", barcode);
//                	query.setInteger("order_status", InventoryOrder.ACCOUNT_COMPLETE);
//
//                    List<Long> quantity = query.list();
//                    if (quantity == null || quantity.get(0) ==null)
//                    	return new Long(0);
//                    else 
//                    	return quantity.get(0);
//               }
//         });
//		} catch (Exception e) {
//			loggerLocal.error(e);
//		}

		/**
		 * to get the quantity from jinsuan system
		 */
		SaleHistory buyHistory = saleHistoryImpl.getSaleHistory(client_id, barcode);
		if (buyHistory != null){
		   quantity = Math.abs((int)buyHistory.getQuantity());
		}
		
		return (int)quantity;
	}
	
	/**
	 * to initialize the order
	 * @param order
	 */
	public void initialize(InventoryOrder order){
		this.getHibernateTemplate().initialize(order.getProduct_Set());
	}

	/**
	 * to copy the inventory order to another one
	 * @param orderInDB
	 * @return
	 */
	public InventoryOrder copy(InventoryOrder orderInDB) {
		evict(orderInDB);
		
		InventoryOrder order = orderInDB;
		
		order.setOrder_ID(0);
		
		Set<InventoryOrderProduct> orderProducts = order.getProduct_Set();
		for (InventoryOrderProduct orderProduct: orderProducts){
			orderProduct.setID(0);
		}
		
		return order;
	}

}
