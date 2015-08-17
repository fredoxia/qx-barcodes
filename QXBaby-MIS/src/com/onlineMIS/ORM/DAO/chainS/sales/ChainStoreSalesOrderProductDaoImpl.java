package com.onlineMIS.ORM.DAO.chainS.sales;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;

@Repository
public class ChainStoreSalesOrderProductDaoImpl  extends BaseDAO<ChainStoreSalesOrderProduct> {

	/**
	 * function to delete a order's products
	 * @param id
	 */
	public void deleteOrderProduct(final int orderId) {
		final String hql = "delete from ChainStoreSalesOrderProduct where chainSalesOrder.id =?";
		
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
	            public Object doInHibernate(Session session) throws HibernateException,SQLException{
	            	Query q= session.createQuery(hql);
	    				q.setParameter(0, orderId);
	    			int success = q.executeUpdate();
	    			return success;
	           }
	     });
	}

}
