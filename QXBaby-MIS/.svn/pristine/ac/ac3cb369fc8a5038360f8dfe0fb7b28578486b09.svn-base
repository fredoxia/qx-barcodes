package com.onlineMIS.ORM.DAO.headQ.user;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.user.News;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;


@Repository
public class NewsDaoImpl extends BaseDAO<News>{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		UserInfor userInfor = (UserInfor)session.get(UserInfor.class, 1);

//		Iterator<UserFunctionality> functions= userInfor.getUserFunction_Set().iterator();
//		
//		System.out.println(functions.hasNext());
		
		transaction.commit();
		
		session.close();
	}


}
