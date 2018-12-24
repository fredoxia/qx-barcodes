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
			return chainStoreDaoImpl.getAllParentStores();
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
			
			//验证parent store 不是自己，自己和parent store 之间没有循环
			ChainStore parentStore = chainStore.getParentStore();
			if (parentStore.getParentStore() != null && parentStore.getParentStore().getChain_id() != 0){
				response.setFail("父连锁店 上面还有一层连锁店，请检查");
				return response;
				
			//检查选择的父连锁店是否已经存在其他的子连锁店，否则不允许。当前只允许一个子连锁店
			} else {
				DetachedCriteria criteriaCheck = DetachedCriteria.forClass(ChainStore.class);
				criteriaCheck.add(Restrictions.eq("parentStore.chain_id", parentStore.getChain_id()));
				
				List<ChainStore> stores = chainStoreDaoImpl.getByCritera(criteriaCheck, true);

				if (stores.size() >0){
					response.setFail("你所选择的父连锁店 已经包含一个子连锁店请检查 : " + stores.get(0).getChain_name());
					return response;
				}
			}
			
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
			storeInDB.setPrintHeader(chainStore.getPrintHeader());

			int priceIncrementId = chainStore.getPriceIncrement().getId();
			if (priceIncrementId == 0)
				storeInDB.setPriceIncrement(null);
			else 
				storeInDB.setPriceIncrement(chainStore.getPriceIncrement());
			
			ChainStore parentStore = chainStore.getParentStore();
			if (parentStore == null || parentStore.getChain_id() == 0){
				storeInDB.setParentStore(null);
			} else {
				//验证parent store 不是自己，自己和parent store 之间没有循环
				if (parentStore.getChain_id() == storeInDB.getChain_id()){
					response.setFail("父连锁店不能是当前连锁店");
					return response;
				} else if (parentStore.getParentStore() != null && parentStore.getParentStore().getChain_id() != 0){
					response.setFail("父连锁店 上面还有一层连锁店，请检查");
					return response;
				} else {
					//检查当前连锁店不是某个连锁店的父连锁店
					DetachedCriteria criteriaCheckThisStore = DetachedCriteria.forClass(ChainStore.class);
					criteriaCheckThisStore.add(Restrictions.eq("parentStore.chain_id", storeInDB.getChain_id()));
					List<ChainStore> stores2 = chainStoreDaoImpl.getByCritera(criteriaCheckThisStore, true);
					if (stores2.size() > 0){
						response.setFail("当前连锁店已经是一个连锁店的父连锁店，不能再添加父连锁店在当前连锁店 : " + stores2.get(0).getChain_name());
						return response;
					}
					
					
					//检查选择的父连锁店是否已经存在其他的子连锁店，否则不允许。当前只允许一个子连锁店
					DetachedCriteria criteriaCheck = DetachedCriteria.forClass(ChainStore.class);
					criteriaCheck.add(Restrictions.ne("chain_id", storeInDB.getChain_id()));
					criteriaCheck.add(Restrictions.eq("parentStore.chain_id", parentStore.getChain_id()));
					
					List<ChainStore> stores = chainStoreDaoImpl.getByCritera(criteriaCheck, true);

					if (stores.size() >0){
						response.setFail("你所选择的父连锁店 已经包含一个子连锁店请检查 : " + stores.get(0).getChain_name());
						return response;
					}
				}
				storeInDB.setParentStore(parentStore);
			}

			
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
//	public List<ChainStore> getChainStoreList() {
//		return chainStoreDaoImpl.getAll(cached);
//	}
	public List<ChainStore> getAllChainStoreList() {

		return chainStoreDaoImpl.getAllChainStoreList();
	}

	@Transactional
	public ChainStore getChainStoreByID(int chainStoreId) {
		return chainStoreDaoImpl.get(chainStoreId, cached);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ChainStore> getAvailableParentChainstores(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		criteria.add(Restrictions.ne("chain_id", ChainStore.CHAIN_ID_TEST_ID));
		criteria.add(Restrictions.isNull("parentStore.chain_id"));
		criteria.addOrder(Order.asc("pinYin"));
		
		return chainStoreDaoImpl.getByCritera(criteria, true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ChainStore> getAvailableClientChainstores(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		criteria.add(Restrictions.ne("chain_id", ChainStore.CHAIN_ID_TEST_ID));
		
		return chainStoreDaoImpl.getByCritera(criteria, true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ChainStore> getActiveChainstoresWithOrder(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.eq("status", ChainStore.STATUS_ACTIVE));
		criteria.add(Restrictions.ne("chain_id", ChainStore.CHAIN_ID_TEST_ID));
		criteria.addOrder(Order.asc("pinYin"));
		
		return chainStoreDaoImpl.getByCritera(criteria, true);
	}
	
	public Integer getNumOfActiveChainStore(){
		String queryString = "select count(chain_id) from ChainStore where status = ? and chain_id !=?";
		Object[] values = new Object[]{ChainStore.STATUS_ACTIVE, ChainStore.CHAIN_ID_TEST_ID};
		
		return chainStoreDaoImpl.executeHQLCount(queryString, values, true);
	}

	
	/**
	 * 获取这个用户可以看到的连锁店
	 * isAll : 1 是否列举所有关系的父子连锁店，0 或者只列举 父亲连锁店， -1 只列举儿子连锁店      
	 * @param AccessLevel (连锁店内权限验证)
	 *        1. 严格 只能查看当前登录连锁店
	 *        2. 中等 owner账号可以查看关联连锁店, 其他账号不行
	 *        3. 松 所有账号都可以查看关联连锁店
	 *        4. 严格 只能查看当前登录连锁店和子连锁店
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
				DetachedCriteria criteria = buildChainStoreCriteria(accessLevel);
				criteria.setProjection(Projections.rowCount());
				int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
				pager.initialize(totalRecord);
			} else {
				pager.calFirstResult();
				cache = true;
			}
			
			//2. 获取连锁店列表
			DetachedCriteria searchCriteria = buildChainStoreCriteria(accessLevel);
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
	 * includeAllChain : 1 包含 所有连锁店 选项在第一页, 0不包含
	 * isAll : 1 是否列举所有的连锁店，0 或者只列举 父亲连锁店， -1 只列举儿子连锁店
	 * @param userInfor
	 * @return
	 */
	public Response getChainStoreListHQ(UserInfor userInfor, Pager pager, int isAll, int includeAllChain) {
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
		if (pager.getCurrentPage() == Pager.FIRST_PAGE && includeAllChain != 0){
		    ChainStore allStore = chainStoreDaoImpl.getAllChainStoreObject();
		    chainStores.add(0, allStore);
		}
		
		response.setReturnValue(chainStores);
		response.setReturnCode(Response.SUCCESS);
		
		return response;

	}
	
	private DetachedCriteria buildChainStoreHQCriteria(int isAll) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		//获取父亲连锁店
		if (isAll == 0){
			criteria.add(Restrictions.isNull("parentStore.chain_id"));
		//获取儿子连锁店
		} else if (isAll == -1){
			criteria.add(Restrictions.isNotNull("parentStore.chain_id"));
		}
		return criteria;
	}

	/**
	 * this criteria 用来搜索连锁店
	 * @return
	 */
	private DetachedCriteria buildChainStoreCriteria(int accessLevel) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		//只获取父亲连锁店
		if (accessLevel != 4){
			criteria.add(Restrictions.isNull("parentStore.chain_id"));
		}
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
		chainCriteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
			
		chainStores = chainStoreDaoImpl.getByCritera(chainCriteria, true);
			
		return chainStores;
	}

	/**
	 * 获取当前连锁店的子连锁店,默认只取一个
	 * @param chain_id
	 * @return
	 */
	public ChainStore getChildChainStore(int chain_id) {
		List<ChainStore> chainStores = new ArrayList<ChainStore>();
		
		DetachedCriteria chainCriteria = DetachedCriteria.forClass(ChainStore.class);
		chainCriteria.add(Restrictions.eq("parentStore.chain_id", chain_id));
			
		chainStores = chainStoreDaoImpl.getByCritera(chainCriteria, true);
		if (chainStores.size() > 0)
			return chainStores.get(0);
		else 
			return null;
	}


	
}
