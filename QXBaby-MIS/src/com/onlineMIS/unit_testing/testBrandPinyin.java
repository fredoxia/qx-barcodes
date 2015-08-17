package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.common.Common_util;

public class testBrandPinyin {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		
		Criteria criteria = session.createCriteria(Brand.class);
		Transaction transaction = session.beginTransaction();
		List<Brand> brands = criteria.list();
		for (Brand brand: brands){
			String brandName = brand.getBrand_Name();
			String pinyin = Common_util.getPinyinCode(brandName,true);
			brand.setPinyin(pinyin);
			
			session.update(brand);
		}
		transaction.commit();
	    session.close();
	}
}
