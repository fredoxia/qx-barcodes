package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.common.Common_util;

public class testChainInOut {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Object[] values = {4, 4};

		String sqlDate= "select i.productBarcode.id, MIN(i.date) from ChainInOutStock i where i.productBarcode.id in (select id from ProductBarcode b where b.product.productId in (select productId from Product where year.year_ID =? and quarter.quarter_ID=?)) group by i.productBarcode.id";

		Query q= session.createQuery(sqlDate);
    	if (values != null && values.length > 0)
    	  for (int i =0; i < values.length; i++)
			  q.setParameter(i, values[i]);
    	q.list();
		transaction.commit();
		session.close();

	}

}
