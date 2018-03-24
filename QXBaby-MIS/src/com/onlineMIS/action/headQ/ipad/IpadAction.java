package com.onlineMIS.action.headQ.ipad;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryService;
import com.onlineMIS.ORM.DAO.headQ.ipad.IpadService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.action.BaseAction;

import net.sf.json.JSONObject;


@Controller
public class IpadAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6147106436046927963L;
	protected IpadActionFormBean formBean = new IpadActionFormBean();
	protected IpadActionUIBean uiBean = new IpadActionUIBean();
	
	@Autowired
	protected IpadService ipadService;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();
	
    //for the search by json function
	protected JSONObject jsonObject;
	
	@Autowired
	protected UserInforService userInforService;
	
	public IpadActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(IpadActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	public IpadActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(IpadActionFormBean formBean) {
		this.formBean = formBean;
	}
	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}



}
