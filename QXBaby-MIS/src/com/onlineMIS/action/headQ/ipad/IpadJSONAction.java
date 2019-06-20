package com.onlineMIS.action.headQ.ipad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

import com.opensymphony.xwork2.ActionContext;

@Controller
public class IpadJSONAction extends IpadAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155815720822402087L;

	public String searchClientByPY(){
		loggerLocal.info("IpadJSONAction-searchClientByPY" );
		List<ClientsMS> clients = ipadService.getClients(formBean.getPinyin());
		if (clients == null)
			clients = new ArrayList<ClientsMS>();

		Response response = new Response();
		response.setReturnValue(clients);
		
	
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String chooseCust(){
		loggerLocal.info("IpadJSONAction-chooseCust" );
		int clientId = formBean.getClientId();
		
		ClientsMS clientsMS = ipadService.getClientById(clientId);
		
		Response response = new Response();
		
		if (clientsMS != null){
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME, clientsMS.getName() +" " + clientsMS.getRegion().getName());
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID, clientId);
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID, null);
		} else 
			response.setFail("无法找到这个客户");
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String searchByProductCode(){
		loggerLocal.info("IpadJSONAction-searchByProductCode" );
		Response response = new Response();
		
		Object clientIdObj = ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_CLIENT_ID);
		Object orderIdObj =  ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		Integer clientId = null;
		Integer orderId = null;
		
		if (clientIdObj != null)
			clientId = (Integer)clientIdObj;
		
		if (orderIdObj != null)
			orderId =  (Integer)orderIdObj;
		
		response = ipadService.searchByProductCode(formBean.getProductCode(),formBean.getStoreId(), clientId, orderId);
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String orderByBarcode(){
		loggerLocal.info("IpadJSONAction-orderByBarcode" );
        Response response = new Response();
		
		Object clientIdObj = ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_CLIENT_ID);
		Object orderIdObj =  ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		if (clientIdObj == null){
			response.setFail("请先输入客户后再选货");
		} else {
			try {
		       response = ipadService.orderProduct(clientIdObj, orderIdObj, formBean.getBarcode(), formBean.getStoreId(), formBean.getQuantity(), loginUser);
		       
		       if (response.isSuccess()){
		    	   Object returnValue = response.getReturnValue();
		    	   if (returnValue != null){
		    		   try {
		    			  Map<String, Integer> result = (Map)returnValue;
		    			  Integer orderId = result.get("orderId");
		    		      ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID, orderId);
		    		   } catch (Exception e2){
		   				response.setFail(e2.getMessage());
						loggerLocal.error(e2);
		    		   }
		    	   }
		       }
			} catch (Exception e){
				response.setFail(e.getMessage());
				loggerLocal.error(e);
			}
		}
		
		try{
			
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String orderProduct(){
		Response response = new Response();
		
		Object clientIdObj = ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_CLIENT_ID);
		Object orderIdObj =  ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		if (clientIdObj == null){
			response.setFail("请先输入客户后再选货");
		} else {
			try {
		       response = ipadService.orderProduct(clientIdObj, orderIdObj, formBean.getPbId(), formBean.getQuantity(), loginUser);
		       
		       if (response.isSuccess()){
		    	   Object returnValue = response.getReturnValue();
		    	   if (returnValue != null){
		    		   try {
		    			  Map<String, Integer> result = (Map)returnValue;
		    			  Integer orderId = result.get("orderId");
		    		      ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID, orderId);
		    		   } catch (Exception e2){
		   				response.setFail(e2.getMessage());
						loggerLocal.error(e2);
		    		   }
		    	   }
		       }
			} catch (Exception e){
				response.setFail(e.getMessage());
				loggerLocal.error(e);
			}
		}
		
		try{
			
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String clearOrders(){
		Response response = new Response();
		
		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID,null);
		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID,null);
		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME,null);
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String submitOrder(){
		Response response = new Response();
		
		Object clientIdObj = ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_CLIENT_ID);
		Object orderIdObj =  ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		if (clientIdObj == null){
			response.setFail("客户信息为空");
		} else if (orderIdObj == null){
			response.setFail("当前订单为空");
		} else {
			try {
		       response = ipadService.submitOrder(clientIdObj, orderIdObj, loginUser);
		       
		       if (response.isSuccess()){
		   		  ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID,null);
				  ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID,null);
				  ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME,null); 
		       }
			} catch (Exception e){
				response.setFail(e.getMessage());
				loggerLocal.error(e);
			}
		}
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String editOrder(){
		Response response = new Response();
		
		int orderId = formBean.getOrderId();
		
		response = ipadService.getOrderHeadInfo(orderId);
		
		InventoryOrder order = (InventoryOrder)response.getReturnValue();
		
		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID,order.getOrder_ID());
 		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID,order.getClient_id());
		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME,order.getClient_name()); 
		
		response.setReturnValue("");
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String deleteOrder(){
		Response response = new Response();
		
		int orderId = formBean.getOrderId();
		
		response = ipadService.deleteOrder(orderId);
		
		if (response.isSuccess()){
		   Object orderIdObjSession = ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		   if (orderIdObjSession != null && (Integer)orderIdObjSession == orderId){
				ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID,null);
		 		ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID,null);
				ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME,null); 
		   }
		}
 		
		response.setReturnValue("");
		
		try{
		    jsonObject = JSONObject.fromObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
}
