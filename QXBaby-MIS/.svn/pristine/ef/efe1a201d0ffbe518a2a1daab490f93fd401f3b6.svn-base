package com.onlineMIS.ORM.DAO.headQ.SQLServer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAOMS;
import com.onlineMIS.ORM.entity.headQ.SQLServer.SaleHistory;
import com.onlineMIS.common.loggerLocal;

@Repository
public class SaleHistoryDAOImpl extends BaseDAOMS<SaleHistory> {
	/**
	 * the two can be a PK for the buy history
	 * @param client_id
	 * @param product_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SaleHistory getSaleHistory(int client_id, String barcode){
		Object[] values = new Object[2];

		values[0] = new Integer(client_id);
		values[1] = barcode;
		String sql = "from SaleHistory h where h.client.client_id =? and h.product.serial_number=? and h.unit_id=h.product.unit_id";
		List<SaleHistory> saleHistories = null;
		try{
			saleHistories = getHibernateTemplateMS().find(sql, values) ;
		} catch (Exception e) {
			e.printStackTrace();
			loggerLocal.error(e);
		}
		
		if (saleHistories != null && saleHistories.size()>0)
			return saleHistories.get(0);
		else {
			return null;
		}
	}

}
