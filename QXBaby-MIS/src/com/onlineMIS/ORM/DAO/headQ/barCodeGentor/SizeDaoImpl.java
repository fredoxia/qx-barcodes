package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.common.loggerLocal;

@Repository
public class SizeDaoImpl extends BaseDAO<Size> {
//	@Override
//	public List<Size> getAll(boolean cached){
//		DetachedCriteria criteria = DetachedCriteria.forClass(Size.class);
//		criteria.addOrder(Order.asc("code"));
//		
//		return getByCritera(criteria, cached);
//	}
	
	public int deleteAll() {
		String hql = "delete from Size";
		int count = executeHQLUpdateDelete(hql, new Object[]{}, true);
		loggerLocal.info("deleted " + count +" rows");
		
		return count;
	}

}
