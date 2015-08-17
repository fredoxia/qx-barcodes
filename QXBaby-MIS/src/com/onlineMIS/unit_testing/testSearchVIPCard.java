package com.onlineMIS.unit_testing;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.common.Common_util;

public class testSearchVIPCard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		ChainVIPCard vipCard = new ChainVIPCard();
		Pager pager = new Pager();
		
		vipCard.setStatus(ChainVIPCard.STATUS_GOOD);
		
		List<ChainVIPCard> chainVIPCards = new ArrayList<ChainVIPCard>();
		
		Criteria criteria = session.createCriteria(ChainVIPCard.class);
		String cardNo = vipCard.getVipCardNo();
		if (cardNo != null && !cardNo.trim().equals("")){
			criteria.add(Restrictions.eq("vipCardNo", cardNo.trim()));
		} else {
			if (vipCard.getStatus() != Common_util.ALL_RECORD)
				criteria.add(Restrictions.eq("status", vipCard.getStatus()));
			
			String customerName = vipCard.getCustomerName();
			if (customerName != null && !customerName.trim().equals(""))
				criteria.add(Restrictions.like("customerName", customerName.trim()));
			
			String telephone = vipCard.getTelephone();
			if (telephone != null && !telephone.trim().equals(""))
				criteria.add(Restrictions.eq("telephone", telephone.trim()));
			
//			if (vipCard.getIssueChainStore().getChain_id() != Common_util.ALL_RECORD)
//				criteria.add(Restrictions.eq("issueChainStore.chain_id", vipCard.getIssueChainStore().getChain_id()));
		}
		

		//1. check the pager
		if (pager.getTotalResult() == 0){
			criteria.setProjection(Projections.count("vipCardNo"));
		    int totalRecord = Common_util.getProjectionSingleValue(criteria.list());
			pager.initialize(totalRecord);
			criteria.setProjection(null);
		} else {
			pager.calFirstResult();
		}

		chainVIPCards = criteria.list();
		

		transaction.commit();
		session.close();
		

	}

}
