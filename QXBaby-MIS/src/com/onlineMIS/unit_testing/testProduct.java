package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.common.Common_util;

public class testProduct {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int yearId = 3;
		int quarterId = 3;
		String brandName = "皮皮";
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		String hql = "SELECT DISTINCT category.category_ID FROM Product";
		//String ids = "SELECT pb.id FROM ProductBarcode pb JOIN pb.product p WHERE p.year.year_ID =1 AND p.quarter.quarter_ID =1 AND p.brand.brand_ID=100";
		
//		Criteria criteria = session.createCriteria(ProductBarcode.class);
		
		Query criteria = session.createQuery(hql);
		List<Object> result = criteria.list();
		for (Object object : result)
			System.out.println(object);

		transaction.commit();
		session.close();
		

	}

}
