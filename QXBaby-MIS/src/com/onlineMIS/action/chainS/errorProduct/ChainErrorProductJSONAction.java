package com.onlineMIS.action.chainS.errorProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

public class ChainErrorProductJSONAction extends ChainErrorProductAction {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6614237635805549673L;

	protected JSONObject jsonObject;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	
}
