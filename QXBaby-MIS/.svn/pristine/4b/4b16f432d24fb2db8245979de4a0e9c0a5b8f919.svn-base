package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;

public class testChainSalesStatisReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date startDate = new Date(112, 6, 1);
		System.out.println(startDate);
		Date endDate = new Date();
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		String sql = "SELECT SUM(quantity),SUM(retailPrice * retailPrice * quantity), SUM(costPrice * quantity), csp.productBarcode.product.year.year_ID, csp.type FROM ChainStoreSalesOrderProduct csp " + 
			         " WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? GROUP BY csp.productBarcode.product.year.year_ID, csp.type ";
		
	    Query query = session.createQuery(sql);
	    query.setParameter(1, startDate);
	    query.setParameter(2, endDate);
	    query.setParameter(0, ChainStoreSalesOrder.STATUS_COMPLETE);
	    List<Object> result = query.list();
	    
	    for (Object resultO : result){
	       Object[] in_out = (Object[])resultO;
		   for (Object object : in_out)
			    System.out.println("-" + object);
	    }
		
		transaction.commit();
		session.close();
	}

}
