package com.onlineMIS.ORM.DAO.headQ.inventory;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreorderIdentity;
import com.onlineMIS.common.loggerLocal;

@Repository
public class InventoryOrderProductDAOImpl extends BaseDAO<InventoryOrderProduct> {
	@SuppressWarnings("unchecked")
	public boolean deleteInventoryProducts(final Set<Integer> delete_id) {
		String parameter = "?";
		for (int i =1;i<delete_id.size();i++)
			parameter +=",?";
		final String hql = "delete from InventoryOrderProduct p where p.ID in ("+parameter+")";
		try{
			this.getHibernateTemplate().execute(new HibernateCallback(){
	            public Object doInHibernate(Session session) throws HibernateException,SQLException{
	            	Query q= session.createQuery(hql);
	            	int i = 0;
	    			for(Integer id: delete_id) {
	    				q.setParameter(i,id);
	    				i++;
	    			}
	    			int success = q.executeUpdate();
	    			return success;
	           }
	     });
		} catch (Exception e) {
			loggerLocal.error(e);
		    return false;
		}
		return true;
	}
	
	public InventoryOrderProduct getByOrderIdProductId(int orderId, int productId){
		DetachedCriteria criteria = DetachedCriteria.forClass(InventoryOrderProduct.class);
		criteria.add(Restrictions.eq("order.order_ID", orderId));
		criteria.add(Restrictions.eq("productBarcode.id", productId));
		
		List<InventoryOrderProduct> products = this.getByCritera(criteria, true); 
		if (products.size() > 0)
			return products.get(0);
		else 
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteInventoryProducts(int orderId) {
		Object[] values = new Object[]{orderId};
		String hql = "delete from InventoryOrderProduct p where p.order.order_ID = ?";
		
		this.executeHQLUpdateDelete(hql, values, true);
		
		return true;
	}
	
	public int checkBarcodeInOrder(int productBarcodeId) {
        String hql = "select count(p.ID) from InventoryOrderProduct p where p.productBarcode.id = ?";
        int count = 0;
        try{
        	Object[] values = new Object[]{productBarcodeId};
        	count = this.executeHQLCount(hql, values, true);
		} catch (Exception e){
			loggerLocal.error(e);
		}
		
		return count;
	}


}
