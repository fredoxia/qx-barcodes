package com.onlineMIS.action.headQ.inventory;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

public class InventoryOrderUploadFileAction extends InventoryOrderAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1720262593350813729L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * upload the file to system from jinsuan order to import to system
	 * @return
	 */
	public String uploadFile(){

		InventoryOrder order  = null;
		try{
			order = inventoryService.transferJinSuanToObject(formBean.getOrderExcel());
			
			List<InventoryOrderProduct> orderProducts = order.getProduct_List();
			
			jsonMap.put("products", orderProducts);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"order"} );
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
			jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
		} catch (Exception e){
			loggerLocal.error(e);
			jsonMap.put("error", "文件导入错误,请检查后重新导入");
			jsonObject = JSONObject.fromObject(jsonMap);
		}
			
		message = jsonObject.toString();
		
		return SUCCESS;
	}
	
}
