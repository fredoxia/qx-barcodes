package com.onlineMIS.action.headQ.preOrder;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.common.loggerLocal;

import com.onlineMIS.converter.JSONUtilDateConverter;

public class PreOrderJSONAction extends PreOrderAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7912317956070555651L;
	protected JSONObject jsonObject;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	/**
	 * the action to generate the 销售报表 report
	 * @return
	 */
	public String searchPreOrders(){
		
		Response response = new Response();
		try {
		    response = preOrderHQService.searchOrders(formBean.getOrder().getClient_id(), formBean.getOrder().getOrderIdentity(), this.getPage(), this.getRows(), this.getSort(), this.getOrder());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.setExcludes(new String[]{"productSet","productList","priceIncrement","activeDate"});
    		
			try {
				jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
}
