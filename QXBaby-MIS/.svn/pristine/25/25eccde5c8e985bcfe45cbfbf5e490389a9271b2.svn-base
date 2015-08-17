package com.onlineMIS.unit_testing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
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
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;



public class testUserInfor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

		UserInfor userInfor = (UserInfor) session.get(UserInfor.class, new Integer(1));
		

		System.out.println("-------" + userInfor.containFunction(1111));
		
		Iterator<UserFunctionality> functionIterator = userInfor.getUserFunction_Set().iterator();
		while (functionIterator.hasNext())
			System.out.println(functionIterator.next().getFunction_id());
		
//		Hibernate.initialize(userInfor.getEmployeeUnder_Set());
		
		transaction.commit();
		session.close();
		
//		Set<UserFunctionality> functions = new UserFunctionalitySet();
//		UserFunctionality functionality = new UserFunctionality();
//		functionality.setFunction_id(1);
//		
//		UserFunctionality functionality2 = new UserFunctionality();
//		functionality2.setFunction_id(2);	
//		
//		functions.add(functionality);
//		functions.add(functionality2);
//		
//		
//		Iterator<UserFunctionality> functionIterator = functions.iterator();
//		while (functionIterator.hasNext())
//			System.out.println(functionIterator.next().getFunction_id());

	}

}