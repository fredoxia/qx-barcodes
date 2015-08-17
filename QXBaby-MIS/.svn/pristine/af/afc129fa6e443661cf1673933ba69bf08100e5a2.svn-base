package com.onlineMIS.unit_testing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;

public class testStoreProcedure {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		SQLQuery  q= session.createSQLQuery("{Call Ts_W_GetCanSaleQty(?,?,?,?,?)}");
    	   q.setParameter(0, 2);
    	   q.setParameter(1, 20694);
    	   q.setParameter(2, 0);
    	   q.setParameter(3, 0);
    	   q.setParameter(4, 0);
    	
		List<Object> result = q.list();
		
System.out.println(result);

result = q.list();

System.out.println(result);result = q.list();

System.out.println(result);
		transaction.commit();
		session.close();

	}

}
