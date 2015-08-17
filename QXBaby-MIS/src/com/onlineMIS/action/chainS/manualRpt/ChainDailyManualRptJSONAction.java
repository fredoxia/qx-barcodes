package com.onlineMIS.action.chainS.manualRpt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.manualRpt.ChainDailyManualRptService;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRpt;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

public class ChainDailyManualRptJSONAction extends ChainDailyManualRptAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5112833159482229751L;
	protected JSONObject jsonObject;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();

	@Autowired
	private ChainDailyManualRptService chainDailyManualRptService;
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public String saveChainDailyManualRpt(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		ChainDailyManualRpt rpt = formBean.getRpt();
		
		Response response = chainDailyManualRptService.saveManualRpt(rpt,userInfor);
		
		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveChainDailyManualRpt");
				loggerLocal.error(e);
			}
		
		return "successful";
	}
}
