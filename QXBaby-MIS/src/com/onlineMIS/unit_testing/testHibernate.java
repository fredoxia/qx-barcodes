package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationItem;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalItemMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.SQLServer.SaleHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;


public class testHibernate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();

		Transaction transaction = session.beginTransaction();
		int id = 15619;
		Object[] values = new Object[]{id};
		String hql = "select count(*) from InventoryOrderProduct as p where p.productBarcode.id = 15619";
		//String hql = "select count(*) from PeopleEvalMark as pem where pem.peopleEvaluation.evaluationYear=2012 and pem.peopleEvaluation.evaluationMonth=10 and pem.evaluater.user_id = 1 and pem.peopleEvaluation.evaluatee.user_id =3";
		
		Query q= session.createQuery(hql);
//    	if (values != null && values.length > 0)
//    	  for (int i =0; i < values.length; i++)
//			  q.setParameter(i, values[i]);
    	
		List<Object> result = q.list();
		int count = ((Long)result.get(0)).intValue();
		
		transaction.commit();
		session.close();
		
		
		

	}

}