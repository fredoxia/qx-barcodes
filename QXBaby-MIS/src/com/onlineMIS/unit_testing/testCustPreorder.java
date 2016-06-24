package com.onlineMIS.unit_testing;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderProduct;

public class testCustPreorder {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(CustPreOrder.class);
        List<CustPreOrder> objsCustPreOrders = criteria.list();
        for (CustPreOrder order : objsCustPreOrders){
        	System.out.println(order);
//        	Set<CustPreOrderProduct> productSet = order.getProductSet();
//        	
//        	System.out.println(productSet.size());
        }
		transaction.commit();
		session.close();
		
		
		

	}
}
