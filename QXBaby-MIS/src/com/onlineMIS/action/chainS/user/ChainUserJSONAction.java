package com.onlineMIS.action.chainS.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserFunctionality;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class ChainUserJSONAction extends ChainUserAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8204078532414802422L;
    private Map<String,Object> jsonMap = new HashMap<String, Object>();

	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public String login(){
		Response response = new Response();
		
		String userName = formBean.getChainUserInfor().getUser_name();
		String password = formBean.getChainUserInfor().getPassword();
		
		if (userName == null || password == null || userName.equals("") || password.equals("")){
			response.setQuickValue(Response.FAIL, "用户名和密码不能为空");
		} else {
			try {
			     response = chainUserInforService.validateUser(userName, password, true);
                 
			     //set session
			     ChainUserInfor user = (ChainUserInfor)response.getReturnValue();
			     ActionContext.getContext().getSession().put(Common_util.LOGIN_CHAIN_USER, user);
			     
			     response.setReturnValue(null);
			} catch (Exception e) {
				 response.setQuickValue(Response.ERROR, "系统错误，请联系管理员");
			}
		}

		
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
    	
    	return SUCCESS;
	}
	
	/**
	 * 提供一个json服务给其他应用，比如连锁店条码制作功能
	 * @return
	 */
	public String loginService(){
	Response response = new Response();
		
		String userName = formBean.getChainUserInfor().getUser_name();
		String password = formBean.getChainUserInfor().getPassword();
		
		if (userName == null || password == null || userName.equals("") || password.equals("")){
			response.setQuickValue(Response.FAIL, "用户名和密码不能为空");
		} else {
			try {
			     response = chainUserInforService.validateUser(userName, password, false);
			     loggerLocal.info(response.toString());
			} catch (Exception e) {
				 response.setQuickValue(Response.ERROR, "系统错误，请联系管理员");
			}
		}
		
		JsonConfig jsonConfig = new JsonConfig();

		//to excludes the set and list inforamtion
		jsonConfig.setExcludes( new String[]{"chainUserFunctionalities"} );
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(response, jsonConfig);
//			   loggerLocal.info(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.error(e);
			}
    	
    	return SUCCESS;
	}
	
	/**
	 * after user key up, check the chain user name
	 * @return
	 */
	public String checkChainUsername(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	ChainUserInfor user = formBean.getChainUserInfor();
    	boolean result = chainUserInforService.validateChainUsername(user);

		jsonMap.put("result", result);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}
    	
    	return SUCCESS;
	}
	
	/**
	 * to get the functions by the user type
	 * @return
	 */
	public String getUserTypeFunctions(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	List<ChainUserFunctionality> functions = chainUserInforService.getChainFunctionByRoleType(formBean.getRoleType().getChainRoleTypeId());
		
		jsonMap.put("functions", functions);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			   System.out.println(jsonObject);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}
    	
    	return SUCCESS;
	}
	
	/**
	 * save the user functions
	 * @return
	 */
	public String saveRoleTypeFunctions(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
		int roleTypeId = formBean.getRoleType().getChainRoleTypeId();
		
		boolean isSuccess = chainUserInforService.updateRoleTypeFunctions(roleTypeId, formBean.getFunctions());

		if (isSuccess)
			jsonMap.put("isSuccess", true);
		else {
			jsonMap.put("isSuccess", false);
		}
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 店主账号登陆
	 * @return
	 */
	public String ownerLogin(){
		Response response = chainUserInforService.validateOwnerLogin(formBean.getChainUserInfor());
		response.setReturnValue(null);
		jsonMap.put("response", response);
		
		if (response.getReturnCode() == Response.SUCCESS){
			ActionContext.getContext().getSession().put(Common_util.IS_CHAIN_BOSS, true);
		} else {
			ActionContext.getContext().getSession().remove(Common_util.IS_CHAIN_BOSS);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 清除店主登陆记录
	 * @return
	 */
	public String destoryOwnerLogin(){
		System.out.println("--------- destoryOwnerSession--------");
		
		ActionContext.getContext().getSession().remove(Common_util.IS_CHAIN_BOSS);
		
		return SUCCESS;
	}
	
	/**
	 * 连锁店在首页刷新这几天的单据信息
	 *
	 * @return
	 */
	public String refreshOrderStatisticsInformation(){
		Response response = new Response();
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
    		try {
    		     chainUserInforService.getOrderStatisticInformation(userInfor, response);
    		} catch (Exception e){
    			 response.setFail(e.getMessage());
    		}
    	} else {
    		response.setReturnCode(Response.WARNING);
    	}
    	
		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
}
