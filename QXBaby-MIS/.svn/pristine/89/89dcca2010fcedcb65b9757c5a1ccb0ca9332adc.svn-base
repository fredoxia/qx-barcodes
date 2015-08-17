package com.onlineMIS.ORM.DAO.headQ.user;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAOMS;
import com.onlineMIS.ORM.entity.headQ.HR.MagerEmployeeRelationship;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.inject.util.FinalizablePhantomReference;

@Repository
public class UserInforDaoImpl extends BaseDAO<UserInfor>{


	public void initialize(UserInfor userInfor) {
		this.getHibernateTemplate().initialize(userInfor.getEmployeeUnder_Set());
		this.getHibernateTemplate().initialize(userInfor.getUserFunction_Set());
	}
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



	public void deleteFunctionByUserID(final int user_id) {
		this.getHibernateTemplate().setCacheQueries(true);
		final String hql = "delete from UserFunctionality u where u.user_id = ?";
		
		try{
			this.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
	            public Integer doInHibernate(Session session) throws HibernateException,SQLException{
	        		
	        		Query q= session.createQuery(hql);
	        		
	        		q.setParameter(0, user_id);
	        		
	    			int success = q.executeUpdate();
	    			return success;
	           }
	     });
		} catch (Exception e) {
			loggerLocal.error(e);
		}
	}

	public void addFunctions(List<UserFunctionality> userFunctionalities) {
		this.getHibernateTemplate().setCacheQueries(true);
		for (int i =0; i < userFunctionalities.size(); i++){
			UserFunctionality functionality = userFunctionalities.get(i);
			this.getHibernateTemplate().save(functionality);
		}
		
	}





}
