package com.onlineMIS.action.chainS.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.action.headQ.finance.FinanceJSONAction;
import com.onlineMIS.action.headQ.finance.FinanceJSPAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class FinanceChainJSONAction extends FinanceJSONAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -507154282584622240L;
	/**
	 * search the finance bills 
	 * @return
	 */
	public String searchFHQBill(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ ".searchFHQBill : " + formBean);
    	
		formBean.getFinanceBill().setStatus(FinanceBill.STATUS_COMPLETE);
		List<FinanceBill> fianBills = financeService.searchFHQBills(formBean);
		
		jsonMap.put("bills", fianBills);
		jsonMap.put("pager", formBean.getPager());
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setExcludes( new String[]{"financeBillItemSet","financeBillItemList","creatorHq"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchFHQBill");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 查询帐户流水
	 * @return
	 */
	public String searchAcctFlow(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchAcctFlow : " + formBean);
    	
		Date startDate = formBean.getSearchStartTime();
		Date endDate = formBean.getSearchEndTime();
		int chainId = formBean.getChainStore().getChain_id();
		
		Response response = new Response();
		
		try {
			response = financeService.searchAcctFlow(startDate, endDate, chainId, true);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap =  (Map<String, Object>)response.getReturnValue();
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter()); 
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
}
