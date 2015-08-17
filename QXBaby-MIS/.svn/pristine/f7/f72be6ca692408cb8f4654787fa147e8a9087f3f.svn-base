package com.onlineMIS.unit_testing;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class testFinance {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

      
        FinanceBill bill = (FinanceBill)session.get(FinanceBill.class, 1);
        bill.putSetToList();
        System.out.println(bill.getFinanceBillItemList().get(0).getComment());
        
		
		transaction.commit();
		session.close();
		
		
		

	}
}
