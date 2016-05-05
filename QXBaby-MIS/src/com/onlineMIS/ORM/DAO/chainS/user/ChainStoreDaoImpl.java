package com.onlineMIS.ORM.DAO.chainS.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;

@Repository
public class ChainStoreDaoImpl extends  BaseDAO<ChainStore>{
	public static final boolean cached = true;
	
	/**
	 * to get the chainStore information by client Id, it is used in the headQ part
	 * @param clientId
	 * @return
	 */
	public ChainStore getByClientId(int clientId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.eq("client_id", clientId));
		
		List<ChainStore> chainStores = getByCritera(criteria, true);
		if (chainStores != null && chainStores.size() > 0)
			return chainStores.get(0);
		else {
			return null;
		}
	}
	
	/**
	 * 获取所有还在运营连锁店的client id
	 * @return
	 */
	public Set<Integer> getAllClientIds(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		
		List<ChainStore> chainStores = getByCritera(criteria, true);
		Set<Integer> clientIds = new HashSet<Integer>();
		for (ChainStore store : chainStores){
			clientIds.add(store.getClient_id());
		}
		
		return clientIds;
	}
	
	public static ChainStore getAllChainStoreObject(){
		ChainStore chainStore = new ChainStore();
		chainStore.setChain_id(Common_util.ALL_RECORD);
		chainStore.setClient_id(Common_util.ALL_RECORD);
		chainStore.setChain_name("所有连锁店");
		chainStore.setAllowChangeSalesPrice(ChainStore.ALLOW_CHANGE_PRICE);
		return chainStore;
	}
	
	public static ChainStore getOutsideStore(){
		ChainStore chainStore = new ChainStore();
		chainStore.setChain_id(0);
		chainStore.setClient_id(0);
		chainStore.setChain_name("非连锁店");
		return chainStore;
	}
}
