package com.onlineMIS.action.headQ.inventory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class InventoryOrderAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 806062472607522010L;
	
	protected InventoryOrderActionFormBean formBean = new InventoryOrderActionFormBean();
	
	protected InventoryOrderActionUIBean uiBean = new InventoryOrderActionUIBean();

	@Autowired
	protected InventoryService inventoryService;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();
	
    //for the search by json function
	protected JSONObject jsonObject;
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public InventoryOrderActionFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(InventoryOrderActionFormBean formBean) {
		this.formBean = formBean;
	}

	public InventoryOrderActionUIBean getUiBean() {
		return uiBean;
	}

	public void setUiBean(InventoryOrderActionUIBean uiBean) {
		this.uiBean = uiBean;
	}

	protected String logInventory(String className, String action, Object clientId, Object orderId, String uuid){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		String log = className + "." + action +","  + clientId + "," + orderId  + "," + loginUserInfor.getUser_name()+ "," + uuid;
		return log;		
	}
}
