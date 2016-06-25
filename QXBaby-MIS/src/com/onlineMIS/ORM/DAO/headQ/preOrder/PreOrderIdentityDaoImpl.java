package com.onlineMIS.ORM.DAO.headQ.preOrder;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreorderIdentity;

@Repository
public class PreOrderIdentityDaoImpl extends BaseDAO<CustPreorderIdentity> {
	
	public List<CustPreorderIdentity> getTop(int topNum){
		DetachedCriteria criteria = DetachedCriteria.forClass(CustPreorderIdentity.class);
		criteria.addOrder(Order.desc("orderIdentity"));
		
		if (topNum <= 0 )
			topNum = 10;
		
		return this.getByCritera(criteria, 0, topNum, true); 
	}

}
