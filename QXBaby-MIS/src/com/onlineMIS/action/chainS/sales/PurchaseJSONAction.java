package com.onlineMIS.action.chainS.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

public class PurchaseJSONAction extends PurchaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2414126206232576165L;
	private JSONObject jsonObject;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * to search the purchase order by search criteria
	 * @return
	 */
	public String searchOrders(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"searchOrders : " + formBean);

		Response response = purchaseService.searchPurchaseOrders(formBean);
		
		List<InventoryOrder> orders = null;
		if (response.isSuccess())
		    orders = (List<InventoryOrder>)response.getReturnValue();
				 
		jsonMap.put("orders", orders);
		jsonMap.put("pager", formBean.getPager());
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		
		//jsonConfig.setExcludes( new String[]{"product_List","product_Set","order_scanner", "order_Keeper","order_Counter","order_Auditor","pdaScanner"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 提供接口给总部获取当前的订单在连锁店是否已经确认收货了以及备注
	 * @return
	 */
	public String chainInventoryService(){

		Response response = new Response();
		try {
		    response = purchaseService.getChainConfirmInfor(formBean.getOrder().getOrder_ID());	
		} catch (Exception e){
			e.printStackTrace();
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		
		JsonConfig jsonConfig = new JsonConfig();

		//to excludes the set and list inforamtion
		jsonConfig.setExcludes( new String[]{"startTime","completeTime"} );
		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 某些情况下，客户没有清点就直接确认了，后来发现开单错误，总部需要回滚这个单子状态和库存
	 * @return
	 */
	public String headqResetPurchaseOrderStatus(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = new Response();
		try {
		    purchaseService.resetPurchaseOrderStatus(formBean.getOrder(),loginUser, response);	
		} catch (Exception e){
			loggerLocal.error(e);
			response.setFail(e.getMessage());
		}

		//to excludes the set and list inforamtion
		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		return SUCCESS;
	}
	
	/**
	 * 连锁店更新purchase order的状态
	 * @return
	 */
	public String chainUpdatePurchaseOrderStatus(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = new Response();
		try {
		    purchaseService.updatePurchaseOrderStatus(formBean.getOrder(),loginUser, response);	
		} catch (Exception e){
			loggerLocal.error(e);
			response.setFail(e.getMessage());
		}

		//to excludes the set and list inforamtion
		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		return SUCCESS;
	}

}
