package com.onlineMIS.action.chainS.chainMgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;


public class ChainMgmtJSONAction extends ChainMgmtAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1795628921324212466L;
	
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private String message;
	
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * in the web page, save the chain store in the headq part
	 * @return
	 */
	public String saveChainStore(){
//    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveChainStore");
    	
		//set the chain store
		ChainStore chainStore = formBean.getChainStore();

		Response response = chainStoreService.saveChainStore(chainStore);
		
		if (response.getReturnCode() != Response.SUCCESS){
			jsonMap.put("error", true);
			jsonMap.put("msg", response.getMessage());
		} else {
			jsonMap.put("error", false);
		    jsonMap.put("chainStore", chainStore);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			   System.out.println(jsonObject.toString());
			} catch (Exception e){
//				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}	
			
		return "successful";
	}
	
	/**
	 * in the headq, the admin save the chain user information
	 * @return
	 */
	public String saveChainUser(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveChainUser");
    	
		int count = chainUserInforService.checkExistOfUser(formBean.getChainUserInfor().getUser_name(),formBean.getChainUserInfor().getUser_id());
		
		if (count > 0){
			jsonMap.put("error", true);
		    jsonMap.put("userName", formBean.getChainUserInfor().getUser_name());
		} else {
			ChainUserInfor chainUserInfor = formBean.getChainUserInfor();
			chainUserInfor.setMyChainStore(formBean.getChainStore());
			
			chainUserInforService.saveUpdateUser(userInfor, chainUserInfor);
			
			jsonMap.put("chainUserInfor", chainUserInfor);
			jsonMap.put("error", false);
		}

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveChainUser");
				loggerLocal.error(e);
			}	
		return "successful";
	}
	
	/**
	 * to get the chain sotre by id
	 * @return
	 */
	public String getChainStore(){
//    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getChainStore");
	
		ChainStore selectedhainStore = chainStoreService.getChainStoreByID(formBean.getChainStore().getChain_id());

		jsonMap.put("chainStoreInfor", selectedhainStore);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","roleType", "chainUserFunctions","createDate"} );
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				e.printStackTrace();
				loggerLocal.error(e);
			}	
			
		return "successful";
	}
	
	/**
	 * to get one chain's chain user list
	 * @return
	 */
	public String getChainStoreUsers(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getChainStoreUsers");
    	
		List<ChainUserInfor> chainUserInfors = chainUserInforService.getChainUserByStoreId(formBean.getChainStore().getChain_id());
		jsonMap.put("chainUsers", chainUserInfors);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","roleType", "chainUserFunctions"} );
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}	
			
		return "successful";
	}

	/**
	 * to get the chain sotre by id
	 * @return
	 */
	public String getChainUser(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getChainUser");
    	
    	ChainUserInfor chainUserInfor = chainUserInforService.getChainUser(formBean.getChainUserInfor().getUser_id());

		jsonMap.put("chainUser", chainUserInfor);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","chainUserFunctions","chainUserFunctionalities"} );
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getChainUser");
				loggerLocal.error(e);
			}	
			
		return "successful";
	}
	
	/**
	 * get the chain store's resource with the same access right
	 * @return
	 */
	public String getChainStoreAdmin(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getChainStoreAdmin");
    	
		List<ChainUserInfor> users = chainUserInforService.getChainStoreUsersInGroup(formBean.getChainStore().getChain_id(), formBean.getChainUserInfor().getRoleType().getChainRoleTypeId());
		
		jsonMap.put("chainUsers", users);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","chainUserFunctions","roleType"} );
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getChainStoreAdmin");
				loggerLocal.error(e);
			}	
			
		return "successful";
	}

	
	/**
	 * check the user name is duplicated or not
	 * @return
	 */
	public String checkUserName(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"checkUserName");
    	
		String userName = formBean.getChainUserInfor().getUser_name();
		int userId = formBean.getChainUserInfor().getUser_id();
		int count = chainUserInforService.checkExistOfUser(userName,userId);
		
		if (count >0){
			jsonMap.put("error", true);
		    jsonMap.put("userName", userName);
		}else {
			jsonMap.put("error", false);
		}

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"checkUserName");
				loggerLocal.error(e);
			}		
		
		return "successful";
	}
	
	/**
	 * to get the barcode for the chain initial stock object
	 * @return
	 */
	public String getBarcodeForChainInitialStock(){
//    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getBarcodeForChainInitialStock");
//    	
		String barcode = formBean.getBarcode();
		
		Response response = chainMgmtService.getChainInitialStock(barcode, formBean.getChainStore().getChain_id());
		if (response.getReturnCode() != Response.SUCCESS){
			jsonMap.put("error", true);
			jsonMap.put("msg", response.getMessage());
		} else {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"date"} );

			jsonMap.put("chainStock", response.getReturnValue());	
		}
		
		jsonMap.put("index", formBean.getIndex());
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				//loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getBarcodeForChainInitialStock");
				loggerLocal.error(e);
			}	
			
		return "successful";
	}
	
	/**
	 * to get one store's chain configuration
	 * @return
	 */
	public String getChainStoreConf(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getChainStoreConf");
    	
		Response response = new Response();
		
		try{
		    response = chainMgmtService.getChainStoreConf(formBean.getChainStoreConf().getChainId());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getChainStoreConf");
				loggerLocal.error(e);
			}	
		return "successful";
	}
	
	/**
	 * 保存chainstoreconfiguration
	 * @return
	 */
	public String saveChainStoreConf(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveChainStoreConf");
    	
		ChainStoreConf chainStoreConf = formBean.getChainStoreConf();
		chainStoreConf.setChainId(formBean.getChainStore().getChain_id());
		Response response = new Response();
		try{
			response = chainMgmtService.saveChainStoreConf(chainStoreConf);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}
		
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveChainStoreConf");
				loggerLocal.error(e);
			}	
		
		return "successful";
	}
	
	/**
	 * trigger by using changing the chain store
	 * 1. change the user
	 * 2. change the chainStore conf
	 * @return
	 */
	public String changeChainStore(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"changeChainStore");
    	
		int chainStoreId = formBean.getChainStore().getChain_id();
		List<ChainUserInfor> users = chainStoreService.getChainStoreSaler(chainStoreId);

	    jsonMap.put("chainUsers", users);
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","roleType", "chainUserFunctions"} );
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"changeChainStore");
				loggerLocal.error(e);
			}
			
		return "successful";
	}
	
	/**
	 * 管理人员修改chain group获取对应chain group信息
	 * @return
	 */
	public String getChainGroup(){
		Response response = new Response();
		
		try {
		    response = chainMgmtService.getChainGroup(formBean.getChainGroup().getId());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   JsonConfig jsonConfig = new JsonConfig();
			   jsonConfig.setExcludes( new String[]{"chainGroup"} );
			
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
			} catch (Exception e){
				//loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"changeChainStore");
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 管理人员修改/增加chain group
	 * @return
	 */
	public String updateChainGroup(){
		Response response = new Response();
		
		try {
		    response = chainMgmtService.updateChainGroup(formBean.getChainGroup(), formBean.getChainStoreIds());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				//loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"changeChainStore");
				loggerLocal.error(e);
			}
		
		return "successful";
	}

	/**
	 * 管理人员删除chain group
	 * @return
	 */
	public String deleteChainGroup(){
		Response response = new Response();
		
		try {
		    response = chainMgmtService.deleteChainGroup(formBean.getChainGroup().getId());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 连锁店查找条码
	 * @return
	 */
	public String searchChainBarcodes(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchChainBarcodes");
    	
    	List<ChainProductBarcodeVO> productBarcodes = chainMgmtService.searchProductBarcode(formBean.getNeedUpdtDate(),formBean.getProductBarcode(), formBean.getChainId(), formBean.getStartDate(), formBean.getEndDate());
    	
    	try {
    		JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
    		
			jsonArray = JSONArray.fromObject(productBarcodes, jsonConfig);
    	} catch (Exception e){
    		loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchChainBarcodes");
    		loggerLocal.error(e);
    	}
		return SUCCESS;
	}
	
	/**
	 * 连锁店更新自己条码
	 * @return
	 */
	public String updateChainBarcode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"updateChainBarcodes");
    	
    	Response response = chainMgmtService.updateChainBarcode(formBean.getChainProductBarcodeVO());
    	
		try{
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 获取所有连锁店涨价模式
	 * @return
	 */
	public String getAllChainPriceIncre(){
    	Response response = chainMgmtService.getAllChainPriceIncre(this.getPage(), this.getRows());
    	
		try{
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
		    jsonObject = JSONObject.fromObject(jsonMap);
		}
		
		return "successful";
	}
	
	/**
	 * 修改price incre
	 * @return
	 */
	public String updatePriceIncre(){
		ChainPriceIncrement priceIncrement = formBean.getPriceIncrement();
		Response response = new Response();
		
		try {
		    response = chainMgmtService.updatePriceIncrement(priceIncrement);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   jsonMap.put("response", response);
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 保存连锁店的配置信息
	 * @return
	 */
	public String updateQxbabyConf(){
		Response response = new Response();
		
		try {
		    chainMgmtService.updateQxbabyConf(formBean.getQxbabyConf());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 永久删除这个chain store, 需要非常小心
	 * @return
	 */
	public String deleteChainStore(){
		Response response = new Response();
		
		try {
			response = chainMgmtService.deleteChainStore(formBean.getChainStore().getChain_id());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}

		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return "successful";
	}
}
