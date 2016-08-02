package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;


@Repository
public class ChainStoreGroupDaoImpl extends BaseDAO<ChainStoreGroup> {
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	
	public ChainStoreGroup getChainStoreBelongGroup(int chainId){
		Object[] values = new Object[]{chainId};
		String sql = "SELECT C FROM ChainStoreGroup C JOIN C.chainStoreGroupElementSet S WHERE S.chainId = ?";
		
		List<ChainStoreGroup> groups = this.getByHQL(sql, values, true);
		if (groups != null && groups.size() >0)
			return groups.get(0);
		else 
			return null;
	}
	
	public List<ChainStore> getChainGroupStoreList(int myChainId, ChainUserInfor loginUser, int accessLevel){
		List<ChainStore> chainStores = new ArrayList<ChainStore>();
		
		if (accessLevel == Common_util.CHAIN_ACCESS_LEVEL_4){
			chainStores = chainStoreDaoImpl.getStoreAndChildren(myChainId);
			return chainStores;
		} else {
		
			ChainStoreGroup chainGroup = null;
			
			if (accessLevel == Common_util.CHAIN_ACCESS_LEVEL_3 || (accessLevel == Common_util.CHAIN_ACCESS_LEVEL_2  && loginUser.getRoleType().getChainRoleTypeId() == ChainRoleType.CHAIN_OWNER)){
				chainGroup = this.getChainStoreBelongGroup(myChainId);
			}
			
			if (chainGroup != null){
				this.initialize(chainGroup.getChainStoreGroupElementSet());
				Set<ChainStoreGroupElement> chainGroupElements = chainGroup.getChainStoreGroupElementSet();
				if (chainGroupElements != null){
					for (ChainStoreGroupElement ele : chainGroupElements){
						int chainId = ele.getChainId();
						ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
						if (chainStore != null)
							chainStores.add(chainStore);
					}
				}
				return chainStores;
			} 
	
			
			ChainStore chainStore = chainStoreDaoImpl.get(myChainId, true);
			if (chainStore != null)
				chainStores.add(chainStore);
	
			return chainStores;
		}
	}

	public Set<Integer> getChainGroupStoreIdList(int myChainId,
			ChainUserInfor loginUser,  int accessLevel) {
		Set<Integer> chainStoreIds = new HashSet<Integer>();
		
		ChainStoreGroup chainGroup = null;
		
		if (accessLevel == Common_util.CHAIN_ACCESS_LEVEL_3 || (accessLevel == Common_util.CHAIN_ACCESS_LEVEL_2  && loginUser.getRoleType().getChainRoleTypeId() == ChainRoleType.CHAIN_OWNER)){
			chainGroup = this.getChainStoreBelongGroup(myChainId);
		}
		
		if (chainGroup != null){
			this.initialize(chainGroup.getChainStoreGroupElementSet());
			Set<ChainStoreGroupElement> chainGroupElements = chainGroup.getChainStoreGroupElementSet();
			if (chainGroupElements != null){
				for (ChainStoreGroupElement ele : chainGroupElements){
					int chainId = ele.getChainId();
					chainStoreIds.add(chainId);
				}
			}
		} 

		chainStoreIds.add(myChainId);
		
		return chainStoreIds;
	}
}
