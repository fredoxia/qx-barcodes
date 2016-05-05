package com.onlineMIS.ORM.DAO.chainS.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.naming.java.javaURLContextFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesOrderDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceBillImpl;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainLoginStatisticInforVO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserFunctionality;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserStoreRelationship;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.user.ChainUserUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemFunctionChainMapping;
import com.onlineMIS.filter.SystemFunctionHeadQMapping;
import com.onlineMIS.filter.SystemParm;
import com.opensymphony.xwork2.ActionContext;


@Service
public class ChainUserInforService {
	private final boolean cached = true;
	
	@Autowired
	private ChainUserInforDaoImpl chainUserInforDaoImpl;
	
	@Autowired
	private ChainRoleTypeDaoImpl chainRoleTypeDaoImpl;
	
	@Autowired
	private ChainUserFunctionalityDaoImpl chainUserFunctionalityDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainStoreSalesOrderDaoImpl chainStoreSalesOrderDaoImpl;
	
	@Autowired
	private ChainInventoryFlowOrderDaoImpl chainInventoryFlowOrderDaoImpl;
	
	@Autowired
	private FinanceBillImpl financeBillImpl;
	
	@Transactional
	public Response validateUser(String userName, String password, boolean addFunction){
		Response response = new Response();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		Criterion c1 = Restrictions.eq("user_name", userName);
		Criterion c2 = Restrictions.eq("password", password);
		criteria.add(c1).add(c2);
		
		List<ChainUserInfor> user_list = null;
		try{
		    user_list = chainUserInforDaoImpl.getByCritera(criteria, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user_list != null && user_list.size() != 0){
			ChainUserInfor user = user_list.get(0);
			if (user.getResign() == UserInfor.RESIGNED || user.getMyChainStore().getStatus() == ChainStore.STATUS_DELETE)
				response.setQuickValue(Response.ERROR, "你的账户已经停用，请联系管理员");
			else {
				if (addFunction){
					chainUserInforDaoImpl.initialize(user);
				
					//to set the user functions
					setFunctions(user);
				}
				
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(user);
			}
		} else {
			response.setQuickValue(Response.ERROR, "登陆名或者密码不正确");
		}

		return response;
	}
	
	/**
	 * set the user functions to the objec
	 * @param user
	 */
	public static void setFunctions(ChainUserInfor user) {
		Iterator<ChainUserFunctionality> userIterator = user.getRoleType().getChainUserFunctionalities().iterator();
		List<Integer> functionIds = new ArrayList<Integer>();
		
		while (userIterator.hasNext()){
			functionIds.add(userIterator.next().getFunctionId());
		}
		
		user.setChainUserFunctions(SystemFunctionChainMapping.getFunctionMapping(functionIds));
	}
	
	/**
	 * to get the chain users by store id
	 * @param chainStoreId
	 * @return
	 */
	public List<ChainUserInfor> getChainUserByStoreId(int chainStoreId){
    	DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);

		criteria.add(Restrictions.eq("myChainStore.chain_id", chainStoreId));
		criteria.addOrder(Order.asc("myChainStore.chain_id"));
		
		return chainUserInforDaoImpl.getByCritera(criteria, cached);
	}
	
	
	/**
	 * This functino is to get the chain user by click the "员工信息"
	 * The logic is to get all particular store's chain users
	 * @return
	 */
	public List<ChainUserInfor> getChainUsers(ChainUserInfor loginUser, Pager pager) {
		boolean cache = false;
		List<ChainUserInfor> chainUsers = new ArrayList<ChainUserInfor>();
		
		/**
		 * 1. check the pager
		 */
		if (pager.getTotalResult() == 0){
			DetachedCriteria criteria = buildGetChainUsersCriteria(loginUser);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(chainUserInforDaoImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
		}

		DetachedCriteria searchCriteria = buildGetChainUsersCriteria(loginUser);
		chainUsers = chainUserInforDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);

		return chainUsers;
	}
	
	private DetachedCriteria buildGetChainUsersCriteria(ChainUserInfor loginUser){
    	ChainStore chainStore = loginUser.getMyChainStore();
      	DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);

    	if (ChainUserInforService.isMgmtFromHQ(loginUser)){
    		criteria.add(Restrictions.ne("myChainStore.chain_id", ChainStore.HEADQ_MGMT_ID));
    		criteria.addOrder(Order.asc("myChainStore.chain_id"));
    	} else if (chainStore != null){
    		criteria.add(Restrictions.eq("myChainStore.chain_id", chainStore.getChain_id()));
    		criteria.addOrder(Order.asc("myChainStore.chain_id"));
    	} else 
    		criteria = null;
      	
    	return criteria;
	}
	
	/**
	 * this function is to get the chain user by id.
	 * make user only the valid user could view the chain user belong to him/her
	 * 1. user_id = login_user.user_id
	 * 2. selected user's chain store = login_user.chain store and login_user is the manager of the chain store
	 * 3. login user is admin
	 * @param loginUser
	 * @param user_id
	 * @return
	 */
//	@Transactional
	public ChainUserInfor getChainUserByID(ChainUserInfor loginUser, int user_id) {
		if (loginUser.getUser_id() == user_id){
			return loginUser;
		} else if (ChainUserInforService.isMgmtFromHQ(loginUser)){
			ChainUserInfor chainUserInfor =  chainUserInforDaoImpl.get(user_id, cached);

			return chainUserInfor;
		} else {
			ChainUserInfor chainUserInfor = chainUserInforDaoImpl.get(user_id, cached);
			
			if (chainUserInfor.getMyChainStore().getChain_id() == loginUser.getMyChainStore().getChain_id()){

				return chainUserInfor;
			}
		}
		return null;
	}
	
	/**
	 * get chain user by id
	 * @param user_id
	 * @return
	 */
	@Transactional
	public ChainUserInfor getChainUser(int user_id) {
		ChainUserInfor chainUserInfor =  chainUserInforDaoImpl.get(user_id, true);
		chainUserInforDaoImpl.initialize(chainUserInfor);
		
		return chainUserInfor;
	}
	
	/*
	 * to dave or update user
	 */
	
	public void saveUpdateUser(ChainUserInfor loginUser, ChainUserInfor userInfor) {
		if (userInfor.getUser_id() != 0){
		   ChainUserInfor user = getChainUserByID(loginUser, userInfor.getUser_id());

		   //the update content
		   user.update(userInfor);
		
		   chainUserInforDaoImpl.saveOrUpdate(user, cached);
		} else {
			chainUserInforDaoImpl.saveOrUpdate(userInfor, cached);
		}
		
	}
	
	/**
	 * to check the chain user name
	 * 1. if it is new, just checkt the user_name
	 * 2. if it is update, new check the user_name together with user_id
	 * @param userInfor
	 * @return
	 */
	public boolean validateChainUsername(ChainUserInfor userInfor){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		if (userInfor.getUser_id() == 0){
    		criteria.add(Restrictions.eq("user_name", userInfor.getUser_name()));
		} else{
			criteria.add(Restrictions.eq("user_name", userInfor.getUser_name()));
			criteria.add(Restrictions.ne("user_id", userInfor.getUser_id()));
		}
		
		List<ChainUserInfor> users = chainUserInforDaoImpl.getByCritera(criteria, cached);
		
		if (users == null || users.size() ==0)
			return true;
		else
			return false;
	}

	/**
	 * get chain store's admin
	 * @param chain_id
	 * @return
	 */
	public List<ChainUserInfor> getChainStoreUsersInGroup(int chain_id,
			int chainRoleTypeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		 criteria.add(Restrictions.eq("myChainStore.chain_id", chain_id));
		 criteria.add(Restrictions.eq("roleType.chainRoleTypeId", chainRoleTypeId));
		 
		return chainUserInforDaoImpl.getByCritera(criteria, cached);
	}


	/**
	 * check the existence of the user name
	 * @param userName
	 * @return
	 */
	public int checkExistOfUser(String userName, int userId) {
		String hql = "select count(*) from ChainUserInfor where user_name =? and user_id<>?";
		Object[] values = new Object[]{userName, userId};
		return chainUserInforDaoImpl.executeHQLCount(hql, values, cached);

	}


	/**
	 * to prepare the chain user functionality ui bean
	 * @param uiBean
	 */
	public void prepareChainUserFunUI(ChainUserUIBean uiBean){
		List<ChainRoleType> chainUserTypes = chainRoleTypeDaoImpl.getAllChainUserTypes();
		uiBean.setChainRoleTypes(chainUserTypes);
		
	}
	
	/**
	 * this function is to get the user functionality by the role type
	 * @param roleTypeId
	 * @return
	 */
	public List<ChainUserFunctionality> getChainFunctionByRoleType(int roleTypeId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserFunctionality.class);
		criteria.add(Restrictions.eq("chainRoleTypeId",roleTypeId));

		return chainUserFunctionalityDaoImpl.getByCritera(criteria, true);
		
	}

	/**
	 * it is to update the role type functions
	 * @param roleTypeId
	 * @param functions
	 * @return
	 */
	@Transactional
	public boolean updateRoleTypeFunctions(int roleTypeId,
			List<Integer> functions) {
		try{
			/**
			 * remove the original functions
			 */
			chainUserFunctionalityDaoImpl.deleteFunctionsByRoleId(roleTypeId);
			
			/**
			 * add the new function
			 */
			for (int function : functions){
				ChainUserFunctionality userFunctionality = new ChainUserFunctionality(roleTypeId,function);
				chainUserFunctionalityDaoImpl.save(userFunctionality, true);
			}
		} catch (Exception e) {
			loggerLocal.error(e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * is the management from headQ
	 * @param userInfor
	 * @return
	 */
	public static boolean isMgmtFromHQ(ChainUserInfor userInfor){
		ChainRoleType roleType = userInfor.getRoleType();
		return (roleType.isAdmin() || roleType.isMgmt());
	}

	/**
	 * the ui for the update chain users
	 * @param uiBean
	 * @param loginUser
	 */
	public void prepareChainUserUpdateUI(ChainUserUIBean uiBean, ChainUserInfor loginUser, ChainUserInfor editUser) {
		List<ChainStore> chainStores = chainStoreService.getChainStoreList(loginUser);
        uiBean.setChainStores(chainStores);
        if (editUser != null)
           uiBean.setChainUserInfor(editUser);
        
        uiBean.setChainRoleTypes(chainRoleTypeDaoImpl.getChainUserTypes());
		
	}

	/**
	 * user update my acct
	 * @param loginUser
	 * @param user
	 */
	public Response saveUpdateMyAcct(ChainUserInfor loginUser, ChainUserInfor user, String password) {
		Response response = new Response();
		
		if (!password.equals("") && !loginUser.getPassword().equals(password.trim())){
			response.setReturnCode(Response.FAIL);
			response.setMessage("原始密码错误");			
		} else if (loginUser.getUser_id() == ChainUserInfor.CHAIN_ADMIN_USER_ID){
			response.setReturnCode(Response.FAIL);
			response.setMessage("总部系统管理员账号不能更新");
		} else {
			loginUser.setName(user.getName());
			loginUser.setMobilePhone(user.getMobilePhone());
			
			if (!password.equals("") && !user.getPassword().equals(""))
			   loginUser.setPassword(user.getPassword().trim());
			
			chainUserInforDaoImpl.update(loginUser, true);
			
			response.setReturnCode(Response.SUCCESS);
		}
		
		return response;
	}

	public Response validateOwnerLogin(ChainUserInfor chainUserInfor) {
		Response response = new Response();
		
		String userName = chainUserInfor.getUser_name();
		String password = chainUserInfor.getPassword();
		int chainId = chainUserInfor.getMyChainStore().getChain_id();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		Criterion c1 = Restrictions.eq("user_name", userName);
		Criterion c2 = Restrictions.eq("password", password);
		criteria.add(c1).add(c2);
		
		List<ChainUserInfor> user_list = null;
		try{
		    user_list = chainUserInforDaoImpl.getByCritera(criteria, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (user_list != null && user_list.size() != 0){
			ChainUserInfor user = user_list.get(0);
			if (user.getMyChainStore().getChain_id() != chainId){
				response.setQuickValue(Response.ERROR, "登陆信息错误");
			} else if (user.getResign() == UserInfor.RESIGNED || user.getMyChainStore().getStatus() == ChainStore.STATUS_DELETE)
				response.setQuickValue(Response.ERROR, "此账户已经停用，请联系管理员");
			else {
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(user);
			}
		} else {
			response.setQuickValue(Response.ERROR, "登陆名或者密码不正确");
		}
		
		
		return response;
	}

	/**
	 * 把这个连锁店里面所有活跃boss账号找出来
	 * @param chain_id
	 * @return
	 */
	public List<ChainUserInfor> getBossInChain(int chain_id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		Criterion c1 = Restrictions.eq("myChainStore.chain_id", chain_id);
		Criterion c2 = Restrictions.eq("roleType.chainRoleTypeId", ChainRoleType.CHAIN_OWNER);
		
		criteria.add(c1).add(c2);
		
		List<ChainUserInfor> user_list = chainUserInforDaoImpl.getByCritera(criteria, false);
		
		return user_list;
		
	}

	/**
	 * 1. 多少草稿销售单未过账
	 * 2. 多少报损单据
	 * 3. 多少报益单据
	 * 4. 多少调货调出单
	 * 5. 多少调货调入单
	 * 6. 多少采购单据未确认
	 * 7. 多少总部财务单据
	 * @param userInfor
	 * @param response
	 */
	public void getOrderStatisticInformation(ChainUserInfor userInfor,
			Response response) {
		Map<String, Object> statisMap = new HashMap<String, Object>();
		List<ChainLoginStatisticInforVO> statisEle =  new ArrayList<ChainLoginStatisticInforVO>();
		
		int chainId = userInfor.getMyChainStore().getChain_id();
		int clientId = userInfor.getMyChainStore().getClient_id();
		int statisDays = Integer.parseInt(SystemParm.getParm("CHAIN_ORDER_STATISTICS_DAYS"));
		java.util.Date today = Common_util.getToday();
		java.util.Date startDate = Common_util.formStartDate(Common_util.calcualteDate(today, statisDays * -1));
		java.util.Date endDate = Common_util.formEndDate(today);
		
		statisDays++;
		
		//1. 获草稿零售单未过账的
		DetachedCriteria draftOrderCriteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		draftOrderCriteria.add(Restrictions.between("orderDate", startDate, endDate));
		draftOrderCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_DRAFT));
		draftOrderCriteria.add(Restrictions.eq("chainStore.chain_id", chainId));
		draftOrderCriteria.setProjection(Projections.rowCount());
		int draftOrderCount = Common_util.getProjectionSingleValue(chainStoreSalesOrderDaoImpl.getByCriteriaProjection(draftOrderCriteria, true));
		ChainLoginStatisticInforVO draftOrderVo = new ChainLoginStatisticInforVO("近"+ statisDays + "天未过账的草稿零售单", draftOrderCount);
		statisEle.add(draftOrderVo);
		
		//2. 获取未过账的预存金单据
//		DetachedCriteria prePaidOrderCriteria = DetachedCriteria.forClass(ChainVIPPrepaidFlow.class);
//		prePaidOrderCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_DRAFT));
//		prePaidOrderCriteria.add(Restrictions.eq("chainStore.chain_id", chainId));
//		prePaidOrderCriteria.add(Restrictions.between("orderCreateDate", startDate, endDate));
//		prePaidOrderCriteria.setProjection(Projections.rowCount());
//		int prepaidOrderCount = Common_util.getProjectionSingleValue(chainStoreSalesOrderDaoImpl.getByCriteriaProjection(draftOrderCriteria, true));
//		ChainLoginStatisticInforVO prepaidOrderVo = new ChainLoginStatisticInforVO("近"+ statisDays + "天未过账的草稿零售单", draftOrderCount);
//		statisEle.add(draftOrderVo);
		
		//3. 获取报损单报益单
		//以后可能有性能问题
 		int overFlow = 0;
 		int flowLoss = 0;
 		int transferIn = 0 ;
 		int transferOut = 0;
		DetachedCriteria invenOrderCriteria = DetachedCriteria.forClass(ChainInventoryFlowOrder.class);
		invenOrderCriteria.add(Restrictions.between("orderDate", startDate, endDate));
 		List<ChainInventoryFlowOrder> inventoryFlowOrders = chainInventoryFlowOrderDaoImpl.getByCritera(invenOrderCriteria, true);
 		for (ChainInventoryFlowOrder order : inventoryFlowOrders){
 			int orderChainId = order.getChainStore().getChain_id();
 			if (orderChainId == chainId){
 				switch (order.getType()) {
					case ChainInventoryFlowOrder.OVER_FLOW_ORDER: overFlow++; break;
					case ChainInventoryFlowOrder.FLOW_LOSS_ORDER: flowLoss++; break;
					case ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER: 
						if (order.getFromChainStore() != null && order.getFromChainStore().getChain_id() == chainId)
							transferOut++;
						else if (order.getToChainStore() != null && order.getToChainStore().getChain_id() == chainId)
							transferIn++;
						break;
					default:
						break;
					}
 			} else if (order.getStatus() == ChainInventoryFlowOrder.STATUS_COMPLETE){
				if (order.getFromChainStore() != null && order.getFromChainStore().getChain_id() == chainId)
					transferOut++;
				else if (order.getToChainStore() != null && order.getToChainStore().getChain_id() == chainId)
					transferIn++;
 			}
 		}
		ChainLoginStatisticInforVO overFlowOrderVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天 创建的报溢单", overFlow);
		statisEle.add(overFlowOrderVO);
		ChainLoginStatisticInforVO flowLossOrderVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天 创建的报损单", flowLoss);
		statisEle.add(flowLossOrderVO);
		ChainLoginStatisticInforVO tansferInVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天 创建的调货单(调入)", transferIn);
		statisEle.add(tansferInVO);
		ChainLoginStatisticInforVO transferOutVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天  创建的调货单(调出)", transferOut);
		statisEle.add(transferOutVO);
		
 		
 		
 		//4. 获取未确认的采购单
		DetachedCriteria purchaseOrderCriteria = DetachedCriteria.forClass(InventoryOrder.class);
		purchaseOrderCriteria.add(Restrictions.eq("order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
		purchaseOrderCriteria.add(Restrictions.ne("chainConfirmStatus", InventoryOrder.STATUS_CHAIN_CONFIRM));
		purchaseOrderCriteria.add(Restrictions.eq("client_id", clientId));
		purchaseOrderCriteria.add(Restrictions.between("order_EndTime", startDate, endDate));
		purchaseOrderCriteria.setProjection(Projections.rowCount());
		int purchaseOrderCount = Common_util.getProjectionSingleValue(chainStoreSalesOrderDaoImpl.getByCriteriaProjection(purchaseOrderCriteria, true));
		ChainLoginStatisticInforVO purchaseOrderVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天总部下账,但未确认收货的采购单", purchaseOrderCount);
		statisEle.add(purchaseOrderVO);
		
 		//5. 获取财务单据
		DetachedCriteria financeOrderCriteria = DetachedCriteria.forClass(FinanceBill.class);
		financeOrderCriteria.add(Restrictions.eq("status", FinanceBill.STATUS_COMPLETE));
		financeOrderCriteria.add(Restrictions.eq("chainStore.chain_id", chainId));
		financeOrderCriteria.add(Restrictions.between("createDate", startDate, endDate));
		financeOrderCriteria.setProjection(Projections.rowCount());
		int financeOrderCount = Common_util.getProjectionSingleValue(financeBillImpl.getByCriteriaProjection(financeOrderCriteria, true));
		ChainLoginStatisticInforVO financeOrderVO = new ChainLoginStatisticInforVO("近"+ statisDays + "天总部下账的财务单据", financeOrderCount);
		statisEle.add(financeOrderVO);
		
		statisMap.put("rows", statisEle);
		
		response.setReturnValue(statisMap);
		
	}


}
