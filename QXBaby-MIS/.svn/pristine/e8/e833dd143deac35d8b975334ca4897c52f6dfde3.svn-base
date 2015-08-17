package com.onlineMIS.action.chainS.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
    	
    	
		List<InventoryOrder> orders = purchaseService.searchPurchaseOrders(formBean);
		
		jsonMap.put("orders", orders);
		jsonMap.put("pager", formBean.getPager());
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setExcludes( new String[]{"product_List","product_Set","order_scanner", "order_Keeper","order_Counter","order_Auditor","pdaScanner"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
}
