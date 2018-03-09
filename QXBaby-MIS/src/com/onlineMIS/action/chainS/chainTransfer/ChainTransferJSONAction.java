package com.onlineMIS.action.chainS.chainTransfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainMgmtService;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;


/**
 * action to 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainTransferJSONAction extends ChainTransferAction{
	@Autowired
	protected ChainMgmtService chainMgmtService;
	
	private JSONObject jsonObject;
	private String message;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();

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
	 * 单据保存到草稿状态
	 * @return
	 */
	public String saveToDraft(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveToDraft ");
    	
    	Response response = new Response();
    	try {
    		response = chainTransferOrderService.saveOrderToDraft(formBean.getTransferOrder(), userInfor);
    	} catch (Exception e){
    		e.printStackTrace();
    		response.setFail("单据提交失败 : " + e.getMessage());
    	}
		

    	try{
			   jsonObject = JSONObject.fromObject(response);
  
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchInvenOrder");
				loggerLocal.error(e);
			}
		return SUCCESS;
	}
	
	/**
	 * 查找chain transfer orders
	 * @return
	 */
	public String searchTransferOrders(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchTransferOrders : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.searchTransferOrders(formBean, this.getPage(), this.getRows(), this.getSort(), this.getOrder(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			try {
				jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 硬删除单据
	 * @return
	 */
	public String deleteOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteOrder : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.deleteTransferOrder(formBean.getTransferOrder().getId(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 将单据提交给对方连锁店
	 * @return
	 */
	public String postOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"postOrder : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.postTransferOrder(formBean.getTransferOrder(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 将单据退给给对方连锁店
	 * @return
	 */
	public String rejectOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"rejectOrder : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.rejectTransferOrder(formBean.getTransferOrder().getId(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 确认但就
	 * @return
	 */
	public String confirmOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"confirmOrder : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.confirmTransferOrder(formBean.getTransferOrder().getId(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 获取transfer orders' acct flows
	 * @return
	 */
	public String searchAcctFlow(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchAcctFlow : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainTransferOrderService.searchTransferAcctFlow(formBean, this.getPage(), this.getRows(), this.getSort(), this.getOrder(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter()); 
		
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			try {
				jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}

}
