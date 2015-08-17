package com.onlineMIS.unit_testing;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRpt;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRptConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class testManualRpt {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

        ChainDailyManualRpt rpt = new ChainDailyManualRpt();
        
        
		
		transaction.commit();
		session.close();
		
		
		

	}
}
