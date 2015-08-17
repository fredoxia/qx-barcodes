package com.onlineMIS.ORM.DAO.chainS.vip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;

@Repository
public class ChainVIPCardImpl extends BaseDAO<ChainVIPCard> {
	
	
	
	@Override
	public void saveOrUpdate(ChainVIPCard entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);

		updateBirthDayMonth(entity);
		
		getHibernateTemplate().saveOrUpdate(entity);

	}
	
	@Override
	public void save(ChainVIPCard entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);
		
		updateBirthDayMonth(entity);
		
		getHibernateTemplate().save(entity);

	}

	@Override
	public void update(ChainVIPCard entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);
		
		updateBirthDayMonth(entity);
		
		getHibernateTemplate().update(entity);

	}

	public static Map<Integer, String> getStatusMap(){
		Map<Integer, String> statusMap = new HashMap<Integer, String>();
		statusMap.put(ChainVIPCard.STATUS_GOOD, "正常");
		statusMap.put(ChainVIPCard.STATUS_STOP, "停用");
		statusMap.put(ChainVIPCard.STATUS_LOST, "挂失");
		return statusMap;
	}
	
	private void updateBirthDayMonth(ChainVIPCard vipCard){
		if (vipCard != null && vipCard.getCustomerBirthday() != null){
			java.util.Date birthday = vipCard.getCustomerBirthday();
			int birthMonth = birthday.getMonth() + 1;
			int birthDay = birthday.getDate();
			vipCard.setBirthDay(birthDay);
			vipCard.setBirthMonth(birthMonth);
		}
	}
	
	public static boolean isEmptyVIPCard(ChainVIPCard vipCard){
		if (vipCard != null && vipCard.getId() != 0)
			return false;
		
		return true;
	}
}
