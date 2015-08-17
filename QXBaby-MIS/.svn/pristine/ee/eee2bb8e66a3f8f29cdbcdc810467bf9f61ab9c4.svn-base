package com.onlineMIS.ORM.DAO.chainS.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserFunctionality;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserStoreRelationship;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.user.ChainUserUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemFunctionChainMapping;
import com.onlineMIS.filter.SystemFunctionHeadQMapping;
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
			if (user.getResign() == UserInfor.RESIGNED || user.getMyChainStore().getStatus() == ChainStore.STATUS_DISABLED)
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
			} else if (user.getResign() == UserInfor.RESIGNED || user.getMyChainStore().getStatus() == ChainStore.STATUS_DISABLED)
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


}
