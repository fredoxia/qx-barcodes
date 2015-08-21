package com.onlineMIS.ORM.DAO.chainS.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreGroupDaoImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.chainMgmt.ChainMgmtActionUIBean;
import com.onlineMIS.common.Common_util;

@Service
public class ChainStoreService {
	private final boolean cached = true;

	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainUserInforDaoImpl chainUserInforDaoImpl;
	
	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;
	
	@Autowired
	private ChainStoreConfDaoImpl chainStoreConfDaoImpl;
	
	/**
	 * get the chain store list by login user information and the correspondig user
	 * from the Chain UI 
	 * 1. if the user is admin, get all charin store
	 * 2. if the user is chain store head, just get his store
	 * @param loginUser
	 * @return
	 */
	public List<ChainStore> getChainStoreList(ChainUserInfor loginUser) {
		if (ChainUserInforService.isMgmtFromHQ(loginUser))
			return chainStoreDaoImpl.getAll(cached);
		else {
			List<ChainStore> chainStores = new ArrayList<ChainStore>();
			chainStores.add(loginUser.getMyChainStore());
			return chainStores;
		}
	}
	
	/**
	 * to save the chain store information
	 * @param chainStore
	 */
	public Response saveChainStore(ChainStore chainStore){
		Response response = new Response();
		
		//if it is new chain store, need check the duplication of the 
		int chainStoreId  = chainStore.getChain_id();
		if (chainStoreId == 0){
			int clientId = chainStore.getClient_id();
			if (clientId == 0){
				response.setReturnCode(Response.FAIL);
				response.setMessage("精算账户为空");
				return response;
			} else {
				String queryString = "select count(chain_id) from ChainStore where client_id = ?";
				Object[] values = new Object[]{clientId};
				
				int count = chainStoreDaoImpl.executeHQLCount(queryString, values, false);
				
				if (count > 0){
					response.setReturnCode(Response.IN_USE);
					response.setMessage("精算账户(" + clientId + ")已经在使用中");
					return response;
				}
					
			}
			
			String chainName = chainStore.getChain_name();
			String chainPY = Common_util.getPinyinCode(chainName,false);
			chainStore.setPinYin(chainPY);
			
			int priceIncrementId = chainStore.getPriceIncrement().getId();
			if (priceIncrementId == 0)
				chainStore.setPriceIncrement(null);
			
			chainStoreDaoImpl.saveOrUpdate(chainStore, cached);
		} else {
			ChainStore storeInDB = chainStoreDaoImpl.get(chainStoreId, true);
			storeInDB.setAllowChangeSalesPrice(chainStore.getAllowChangeSalesPrice());
			
			String chainName = chainStore.getChain_name();
			String chainPY = Common_util.getPinyinCode(chainName,false);
			
			storeInDB.setPinYin(chainPY);
			storeInDB.setChain_name(chainName);
			storeInDB.setInitialAcctAmt(chainStore.getInitialAcctAmt());
			storeInDB.setOwner_name(chainStore.getOwner_name());
			storeInDB.setStatus(chainStore.getStatus());
			storeInDB.setClient_id(chainStore.getClient_id());
			storeInDB.setAllowAddBarcode(chainStore.getAllowAddBarcode());

			int priceIncrementId = chainStore.getPriceIncrement().getId();
			if (priceIncrementId == 0)
				storeInDB.setPriceIncrement(null);
			else 
				storeInDB.setPriceIncrement(chainStore.getPriceIncrement());
			
			
			//need check whether need change the status
			int status = chainStore.getStatus();
			if (status == ChainStore.STATUS_DISABLED)
				disableChainStore(chainStore.getChain_id());
			
			try {
			    chainStoreDaoImpl.saveOrUpdate(storeInDB, cached);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.setReturnCode(Response.SUCCESS);
		return response;
	}
	
	/**
	 * get the chain store list by login user information and the correspondig user
	 * from the head q ui
	 * @param loginUser
	 * @return
	 */
	public List<ChainStore> getChainStoreList() {
		return chainStoreDaoImpl.getAll(cached);
	}

	public ChainStore getChainStoreByID(int chainStoreId) {
		return chainStoreDaoImpl.get(chainStoreId, cached);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ChainStore> getAvailableClientChainstores(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DISABLED));
		criteria.add(Restrictions.ne("chain_id", ChainStore.CHAIN_ID_TEST_ID));
		
		return chainStoreDaoImpl.getByCritera(criteria, true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ChainStore> getActiveChainstores(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.eq("status", ChainStore.STATUS_ACTIVE));
		
		return chainStoreDaoImpl.getByCritera(criteria, true);
	}
	
	public Integer getNumOfActiveChainStore(){
		String queryString = "select count(chain_id) from ChainStore where status = ?";
		Object[] values = new Object[]{ChainStore.STATUS_ACTIVE};
		
		return chainStoreDaoImpl.executeHQLCount(queryString, values, true);
	}

	/**
	 * this function is to disble one chain with its users
	 * @param chain_id
	 */
	public void disableChainStore(int chain_id) {
		String hql = "UPDATE ChainUserInfor SET resign = " + ChainUserInfor.RESIGNED + " WHERE myChainStore.chain_id = ?";
		Object[] values = new Object[]{chain_id};
		chainUserInforDaoImpl.executeHQLUpdateDelete(hql, values, cached);
//		
//		String hql_c = "UPDATE ChainStore SET status = " + ChainStore.STATUS_DISABLED + " WHERE chain_id = ?";
//		Object[] values_c = new Object[]{chain_id};
//		chainStoreDaoImpl.executeHQLUpdateDelete(hql_c, values_c, cached);
	}
	
	/**
	 * 获取这个用户可以看到的连锁店
	 * @param AccessLevel (连锁店内权限验证)
	 *        1. 严格 只能查看当前登录连锁店
	 *        2. 中等 owner账号可以查看关联连锁店, 其他账号不行
	 *        3. 松 所有账号都可以查看关联连锁店
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response getChainStoreList(ChainUserInfor userInfor, Pager pager,int accessLevel, int indicator) {
		Response response = new Response();
		List<ChainStore> chainStores = new ArrayList<ChainStore>(); 

		
		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			boolean cache = false;
			
			//1. check the pager
			if (pager.getTotalResult() == 0){
				DetachedCriteria criteria = buildChainStoreCriteria();
				criteria.setProjection(Projections.rowCount());
				int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
				pager.initialize(totalRecord);
			} else {
				pager.calFirstResult();
				cache = true;
			}
			
			//2. 获取连锁店列表
			DetachedCriteria searchCriteria = buildChainStoreCriteria();
			searchCriteria.addOrder(Order.asc("pinYin"));
			chainStores = chainStoreDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
			
			//3. 添加所有连锁店
			if (pager.getCurrentPage() == Pager.FIRST_PAGE && indicator != 0){
			    ChainStore allStore = chainStoreDaoImpl.getAllChainStoreObject();
			    chainStores.add(0, allStore);
			}
			
			response.setReturnValue(chainStores);
			response.setReturnCode(Response.SUCCESS);
			
			return response;
		} else {
			int chainId = userInfor.getMyChainStore().getChain_id();
			chainStores = chainStoreGroupDaoImpl.getChainGroupStoreList(chainId, userInfor, accessLevel);
			response.setReturnValue(chainStores);
			response.setReturnCode(Response.WARNING);
		    return response;
		}
	}
	
	/**
	 * 总部用户获取这个用户可以看到的连锁店
	 * @param userInfor
	 * @return
	 */
	public Response getChainStoreListHQ(UserInfor userInfor, Pager pager, boolean isAll, int indicator) {
		Response response = new Response();
		List<ChainStore> chainStores = new ArrayList<ChainStore>(); 

		boolean cache = false;
		
		//1. check the pager
		if (pager.getTotalResult() == 0){
			DetachedCriteria criteria = buildChainStoreHQCriteria(isAll);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
			cache = true;
		}
		
		//2. 获取连锁店列表
		DetachedCriteria searchCriteria = buildChainStoreHQCriteria(isAll);
		searchCriteria.addOrder(Order.asc("pinYin"));
		chainStores = chainStoreDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
		
		//3. 添加所有连锁店
		if (pager.getCurrentPage() == Pager.FIRST_PAGE && indicator != 0){
		    ChainStore allStore = chainStoreDaoImpl.getAllChainStoreObject();
		    chainStores.add(0, allStore);
		}
		
		response.setReturnValue(chainStores);
		response.setReturnCode(Response.SUCCESS);
		
		return response;

	}
	
	private DetachedCriteria buildChainStoreHQCriteria(boolean isAll) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		if (!isAll)
		    criteria.add(Restrictions.ne("status", ChainStore.STATUS_DISABLED));
		return criteria;
	}

	/**
	 * this criteria 用来搜索连锁店
	 * @return
	 */
	private DetachedCriteria buildChainStoreCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DISABLED));
		return criteria;
	}

	/**
	 * 的到这个chain store下面的 chain user
	 * @param chainStoreId
	 * @return
	 */
	public List<ChainUserInfor> getChainStoreSaler(int chainStoreId) {
		List<ChainUserInfor> users = chainUserInforDaoImpl.getActiveChainUsersByChainStore(chainStoreId);
		
		if (users == null)
			users = new ArrayList<ChainUserInfor>();
		
		return users;
	}

	/**
	 * 获取可以修改零售价的所有活跃连锁店
	 * @return
	 */
	@Transactional
	public List<ChainStore> getChainStoreCouldChangePrice(){
		List<ChainStore> chainStores = new ArrayList<ChainStore>();
		
		DetachedCriteria chainCriteria = DetachedCriteria.forClass(ChainStore.class);
		chainCriteria.add(Restrictions.eq("allowChangeSalesPrice", ChainStore.ALLOW_CHANGE_PRICE));
		chainCriteria.add(Restrictions.ne("status", ChainStore.STATUS_DISABLED));
			
		chainStores = chainStoreDaoImpl.getByCritera(chainCriteria, true);
			
		return chainStores;
	}
	
}
