package com.onlineMIS.unit_testing;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.headQ.SQLServer.ProductsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ColorGroup;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ColorGroups;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.SizeGroup;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.SizeGroups;

public class testSizeColor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

        Object[] values = new Object[]{new Integer(20),new Integer(200)};
		
		String hql = "select colorGroups from ColorGroups colorGroups where colorGroupId = ? and colorId <> ?";
		
		Query query = session.createQuery(hql);
		query.setParameter(0, 20);
		query.setParameter(1, 200);
		
		List<ColorGroups> colorGroups = query.list();
		for (ColorGroups cGroups: colorGroups)
			System.out.println(cGroups);
        transaction.commit();
		session.close();
	}

}
