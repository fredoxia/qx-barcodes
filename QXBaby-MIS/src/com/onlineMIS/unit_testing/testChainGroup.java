package com.onlineMIS.unit_testing;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;

public class testChainGroup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

//        ChainStoreGroupElement ele1 = new ChainStoreGroupElement();
//        ele1.setChainId(2);
//        ele1.setGroupId(1);
//        
//        session.save(ele1);
//		ChainStoreGroup group = new ChainStoreGroup();
//		group.setGroupName("龚建清连锁");
//		session.save(group);
		
		ChainStoreGroup group2 = (ChainStoreGroup) session.get(ChainStoreGroup.class, new Integer(1));
		for (ChainStoreGroupElement ele : group2.getChainStoreGroupElementSet())
			System.out.println(ele.getChainId());
		
		transaction.commit();
		session.close();

	}

}
