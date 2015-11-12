package com.onlineMIS.ORM.DAO.headQ.user;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainRoleTypeDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.HttpUtil;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemFunctionHeadQMapping;
import com.onlineMIS.filter.SystemParm;
import com.opensymphony.xwork2.ActionContext;


@Service
public class UserInforService {
	public final static int LOGIN_SUCCESS = 0;
	public final static int PASSWORD_WRONG = 1;
	public final static int ACCOUNT_DISABLED = 2;
	
	@Autowired
	private UserInforDaoImpl userInforDaoImpl;
	
	@Autowired
	private ChainRoleTypeDaoImpl chainRoleTypeDaoImpl;
	
	@Transactional
	public Response validateUser(String userName, String password){
		Response response = new Response();
		if (userName == null || password == null || userName.equals("") || password.equals("")){
			response.setQuickValue(Response.FAIL, "用户名和密码不能为空");
			return response;
		} 
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class);
		Criterion c1 = Restrictions.eq("user_name", userName);
		Criterion c2 = Restrictions.eq("password", password);
		criteria = criteria.add(c1).add(c2);
		
		List<UserInfor> user_list = userInforDaoImpl.getByCritera(criteria, false);
		if (user_list != null && user_list.size() != 0){
			UserInfor user = user_list.get(0);
			if (user.getResign() == UserInfor.RESIGNED){
				response.setQuickValue(Response.FAIL, "你的账户已经停用，请联系管理员");
				return response;
			}else {
				userInforDaoImpl.initialize(user);
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(user);
			    return response;
			}
		} else {
			response.setQuickValue(Response.FAIL, "登陆用户名或者密码不正确");
			return response;
		}

	}
	
	/**
	 * function to validate the user id and password
	 * @param userId
	 * @param password
	 * @return
	 */
	@Transactional
	public Response validateUser(int userId, String password) {
		Response response = new Response();
		
		if (userId == 0 || password.equals("")){
			response.setQuickValue(Response.FAIL, "用户ID和密码不能为空");
			return response;
		} 
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class);
		Criterion c1 = Restrictions.eq("user_id", userId);
		Criterion c2 = Restrictions.eq("password", password);
		criteria = criteria.add(c1).add(c2);
		
		List<UserInfor> user_list = userInforDaoImpl.getByCritera(criteria, false);
		if (user_list != null && user_list.size() != 0){
			UserInfor user = user_list.get(0);
			if (user.getResign() == UserInfor.RESIGNED){
				response.setQuickValue(Response.FAIL, "你的账户已经停用，请联系管理员");
				return response;
			}else {
				userInforDaoImpl.initialize(user);
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(user);
			    return response;
			}
		} else {
			response.setQuickValue(Response.FAIL, "登陆ID或者密码不正确");
			return response;
		}

	}
	
	public List<UserInfor> getUsersByDept(String deptCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class,"user");
		criteria.add(Restrictions.eq("user.department", deptCode));
		criteria.addOrder(Order.asc("pinyin"));
		
		List<UserInfor> user_list = userInforDaoImpl.getByCritera(criteria, true);
		
		return user_list;
	}

	/**
	 * function to get all user which is normal like not-disabled
	 * @return
	 */
	public List<UserInfor> getAllNormalUsers() {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class);	
		criteria.add(Restrictions.ne("user_name", "admin"));
		criteria.add(Restrictions.eq("resign", UserInfor.NORMAL_ACCOUNT));
		
		criteria.addOrder(Order.asc("pinyin"));
		
		List<UserInfor> user_list = userInforDaoImpl.getByCritera(criteria, true);
		
		return user_list;
	}
	
	/**
	 * function to get all users including resgined account
	 * @return
	 */
	public List<UserInfor> getAllUsers() {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class);	
		criteria.add(Restrictions.ne("user_name", "admin"));	
		criteria.addOrder(Order.asc("pinyin"));
		List<UserInfor> user_list = userInforDaoImpl.getByCritera(criteria, true);
		
		return user_list;
	}

	public boolean saveOrUpdate(UserInfor userInfor) {
		boolean isSuccess = true;
		String userName = userInfor.getUser_name();
		
		String userNamePinyin = Common_util.getPinyinCode(userName, false);
		userInfor.setPinyin(userNamePinyin);
		
		//add new user
		if (userInfor.getUser_id() == 0){
			userInforDaoImpl.save(userInfor, true);
		//update user
		} else {
			userInforDaoImpl.update(userInfor, true);
		}
		return isSuccess;
	}

	@Transactional
	public UserInfor getUser(int user_id, boolean initialize) {

		UserInfor userInfor = userInforDaoImpl.get(user_id, true);
		
		if (initialize){
			userInforDaoImpl.initialize(userInfor);
		}
		
		return userInfor;
	}

	@Transactional
	public boolean updateUserFunctions(int user_id,List<Integer> functionalities) {
		try{
			/**
			 * remove the original functions
			 */
			userInforDaoImpl.deleteFunctionByUserID(user_id);
			
			/**
			 * add the new function
			 */
			List<UserFunctionality> userFunctionalities = new ArrayList<UserFunctionality>();
			for (int i = 0; i < functionalities.size(); i++){
				UserFunctionality userFunctionality = new UserFunctionality();
				userFunctionality.setUser_id(user_id);
				userFunctionality.setFunction_id(functionalities.get(i));
				userFunctionalities.add(userFunctionality);
			}
			userInforDaoImpl.addFunctions(userFunctionalities);
		} catch (Exception e) {
			loggerLocal.error(e);
			return false;
		}
		
		return true;
	}

	/**
	 * to check whether the user name is used before
	 * @param userInfor
	 * @return false：not used; true:used
	 */
	public boolean checkUserName(UserInfor userInfor) {
	    int user_id = userInfor.getUser_id();
	    String user_Name = userInfor.getUser_name();
	    
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class,"user");
		criteria.add(Restrictions.eq("user_name", user_Name));
		
		if (user_id != 0){
			criteria.add(Restrictions.ne("user_id", user_id));	
		}
		
		List<UserInfor> users = userInforDaoImpl.getByCritera(criteria, true);
		
		if (users == null || users.size() == 0)
	       return false;
		else {
			return true;
		}
	}

	/**
	 * UI
	 * to get the users who are evaluaters
	 * 1. all the evaluaters
	 * @return
	 */
	public List<UserInfor> getAvailableEvaluaters() {
        String HQL = "select user from UserInfor user join user.userFunction_Set f where user.user_name <> ? and user.resign =? and f.function_id=? ";

		Object[] values = new Object[]{"admin",UserInfor.NORMAL_ACCOUNT,UserFunctionality.CREATE_EVALUATION};
		

		List<UserInfor> user_list = userInforDaoImpl.getByHQL(HQL, values, true);
		
		return user_list;
	}
	


	/**
	 * to get the normal users with ids 
	 * @param ids
	 */
	public List<UserInfor> getUsers(List<Integer> ids) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInfor.class);
		criteria.add(Restrictions.in("user_id", ids));
		criteria.add(Restrictions.eq("resign", UserInfor.NORMAL_ACCOUNT));
		
		return userInforDaoImpl.getByCritera(criteria, true);
	}


	/**
	 * set the user functions to the objec
	 * @param user
	 */
	public void setFunctions(UserInfor user) {
		Iterator<UserFunctionality> userIterator = user.getUserFunction_Set().iterator();
		List<Integer> functionIds = new ArrayList<Integer>();
		
		while (userIterator.hasNext()){
			functionIds.add(userIterator.next().getFunction_id());
		}
		
		user.setFunctions(SystemFunctionHeadQMapping.getFunctionMapping(functionIds));
	}

	/**
	 * function to update my account from ui
	 * @param loginUser
	 * @param userInfor
	 */
	public UserInfor updateMyAcct(UserInfor logInfor, UserInfor userInfor) {
//		String sql = "update UserInfor u set homePhone=?, mobilePhone=?,idNumber=?,birthday=?, password=? where user_id=?";
//		Object[] parms = new Object[6];
//		parms[0] = userInfor.getHomePhone();
//		parms[1] = userInfor.getMobilePhone();
//		parms[2] = userInfor.getIdNumber();
//		parms[3] = userInfor.getBirthday();
//		parms[4] = userInfor.getPassword();
//		parms[5] = userInfor.getUser_id();
		
//		UserInfor userBean = userInforDaoImpl.get(userInfor.getUser_id(), false);
		
		logInfor.setHomePhone(userInfor.getHomePhone());
		logInfor.setMobilePhone(userInfor.getMobilePhone());
		logInfor.setIdNumber(userInfor.getIdNumber());
		logInfor.setBirthday(userInfor.getBirthday());
		logInfor.setPassword(userInfor.getPassword());

		userInforDaoImpl.update(logInfor, true);
		
		return logInfor;
	}
	
	/**
	 * to create the chainUserInfor by headq
	 * @param loginUserInfor
	 * @return
	 */
	@Transactional
	public ChainUserInfor transferHeadUserToChainUser(UserInfor loginUserInfor) {
		ChainUserInfor chainUserInfor = new ChainUserInfor();
		chainUserInfor.setMobilePhone(loginUserInfor.getMobilePhone());
		chainUserInfor.setName(loginUserInfor.getName());
		chainUserInfor.setUser_name(loginUserInfor.getUser_name());
		
		int chainFunctionType = getUserChainRoleType(loginUserInfor);
		ChainRoleType adminRoleType = new ChainRoleType();
		if (chainFunctionType == UserFunctionality.CHAIN_ADMIN_FUNCTION_TYPE)
			adminRoleType = chainRoleTypeDaoImpl.get(ChainRoleType.CHAIN_ADMIN, true);
		else if (chainFunctionType == UserFunctionality.CHAIN_MGMT_FUNCTION_TYPE)
			adminRoleType = chainRoleTypeDaoImpl.get(ChainRoleType.CHAIN_MGMT, true);
		
		chainRoleTypeDaoImpl.initialize(adminRoleType);
		
		chainUserInfor.setRoleType(adminRoleType);
		chainUserInfor.setUser_id(ChainUserInfor.CHAIN_ADMIN_USER_ID);
		chainUserInfor.setMyChainStore(ChainStoreDaoImpl.getAllChainStoreObject());
		
		ChainUserInforService.setFunctions(chainUserInfor);
		
		return chainUserInfor;
	}

	/**
	 * if the user is chain admin, use it
	 * else if the user is chain manager, use it
	 * or return 0
	 * @param loginUserInfor
	 * @return
	 */
	private int getUserChainRoleType(UserInfor loginUserInfor) {
		Set<UserFunctionality> userFunctionalities = loginUserInfor.getUserFunction_Set();
		Iterator<UserFunctionality> userFuncIterator = userFunctionalities.iterator();
		int functionRoleType = 0;
		while (userFuncIterator.hasNext()){
			UserFunctionality functionality = userFuncIterator.next();
			int functionid = functionality.getFunction_id();
			if (functionid == UserFunctionality.CHAIN_ADMIN_FUNCTION_TYPE)
				return functionid;
			else if (functionid == UserFunctionality.CHAIN_MGMT_FUNCTION_TYPE)
				functionRoleType = functionid;
		}
		
		return functionRoleType;
	}

	public Response validateUser4Chain(String userName, String password) {
		Response response = new Response();
		if (userName == null || password == null || userName.equals("") || password.equals("")){
			response.setQuickValue(Response.FAIL, "用户名和密码不能为空");
			return response;
		} 
		
		String url = SystemParm.getParm("SYSTEM_LOGIN_SERVICE") ;
        // 要传的参数
        String s = null;
        String result = null;
        try {
			s= URLEncoder.encode("formBean.chainUserInfor.user_name", "UTF-8") + "=" +
			           URLEncoder.encode(userName, "UTF-8");
	        s += "&" + URLEncoder.encode("formBean.chainUserInfor.password", "UTF-8") + "=" +
	                URLEncoder.encode(password, "UTF-8");
	        result = HttpUtil.callRemoteService(url, s);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, "登陆失败 : " + e.getMessage());
			return response;
		}				

		try {
			JSONObject jsonObject = JSONObject.fromObject(result);

			Response loginResponse = (Response)JSONObject.toBean(jsonObject, Response.class);
			if (loginResponse.getReturnCode() == Response.SUCCESS){
				Object returnValue = loginResponse.getReturnValue();
				jsonObject = JSONObject.fromObject(returnValue);
				ChainUserInfor userInfor = (ChainUserInfor)JSONObject.toBean(jsonObject, ChainUserInfor.class);

				if (userInfor.getMyChainStore().getAllowAddBarcode() != ChainStore.ALLOW_ADD_BARCODE){
					loginResponse.setQuickValue(Response.FAIL, "你的连锁店没有制作条码的权限，请向总部申请");
				} else {
					if (userInfor.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_OWNER && userInfor.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_LEAD){
						loginResponse.setQuickValue(Response.FAIL, "你的账户没有制作条码的权限，必须是经营者或者店长权限");
					} else 
						loginResponse.setReturnValue(userInfor);
				}
			}
			
			return loginResponse;
		 } catch (Exception e) {
	            e.printStackTrace();
				response.setQuickValue(Response.FAIL, "登陆失败 : " + e.getMessage());
				return response;
		}
		
		
	}



}
