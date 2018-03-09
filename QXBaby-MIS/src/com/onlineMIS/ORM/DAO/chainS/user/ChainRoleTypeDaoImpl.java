package com.onlineMIS.ORM.DAO.chainS.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;

@Repository
public class ChainRoleTypeDaoImpl extends BaseDAO<ChainRoleType> {

	public void initialize(ChainRoleType chainRoleType){
		this.getHibernateTemplate().initialize(chainRoleType.getChainUserFunctionalities());
	}
	
	
   public List<ChainRoleType> getAllChainUserTypes(){
	   DetachedCriteria criteria = DetachedCriteria.forClass(ChainRoleType.class);
	   criteria.addOrder(Order.asc("chainRoleTypeId"));
	   return this.getByCritera(criteria, true);
   }
   
   public List<ChainRoleType> getChainUserTypes(){
	   List<Integer> roleTypeIds = new ArrayList<Integer>();
	   roleTypeIds.add(ChainRoleType.CHAIN_STAFF);
	   roleTypeIds.add(ChainRoleType.CHAIN_LEAD);
	   roleTypeIds.add(ChainRoleType.CHAIN_OWNER);
	   roleTypeIds.add(ChainRoleType.SELF_CHAIN_OWNER);
	   roleTypeIds.add(ChainRoleType.SELE_CHAIN_STAFF);
	   
	   DetachedCriteria criteria = DetachedCriteria.forClass(ChainRoleType.class);
	   criteria.addOrder(Order.asc("chainRoleTypeId"));
	   criteria.add(Restrictions.in("chainRoleTypeId", roleTypeIds));
	   return this.getByCritera(criteria, true);
   }


}
