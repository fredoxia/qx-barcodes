package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class testInventoryHibernate {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HeadQSalesHistory salesHistory = new HeadQSalesHistory(37912,2528,23.4, 34.5, 55, 5, 5, 5);
		 
		HeadQSalesHistory salesHistory2 = new HeadQSalesHistory(37912,2528,23.4, 34.5, 5, 57, 56, 5);
		 
		
		Set<HeadQSalesHistory> historySet = new HashSet<HeadQSalesHistory>();
		 System.out.println(historySet.add(salesHistory));
		 System.out.println(historySet.add(salesHistory2));
		 Iterator<HeadQSalesHistory> historyIt = historySet.iterator();
		 while (historyIt.hasNext()){
			 System.out.println(historyIt.next().getId());
		 }
		

	}
}
