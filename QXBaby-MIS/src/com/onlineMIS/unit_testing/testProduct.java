package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
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
		
		Criteria criteria = session.createCriteria(ProductBarcode.class);
		Set<String> keys = new HashSet<String>();
		keys.add("193000112580");
		keys.add("128000109469");
		keys.add("165000109457");
		
		criteria.add(Restrictions.in("barcode", keys));

		List<ProductBarcode> result = criteria.list();
		
		for (ProductBarcode pb: result)
			System.out.println(pb.getBarcode() + "," +  pb.getId());
		
		transaction.commit();
		session.close();
		

	}

}
