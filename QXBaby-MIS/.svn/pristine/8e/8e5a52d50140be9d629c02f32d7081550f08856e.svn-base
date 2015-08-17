package com.onlineMIS.action.headQ.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBillItem;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class FinanceJSONAction extends FinanceAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -507154282584622240L;
	protected JSONObject jsonObject;
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * search the finance bills 
	 * @return
	 */
	public String searchFHQBill(){
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
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * json 保存草稿
	 * @return
	 */
	public String saveFHQToDraft(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		Response response = new Response();
		
		if (validateFinanceHQBill(response)){
			formBean.getFinanceBill().setCreatorHq(loginUserInfor);
			formBean.getFinanceBill().setChainStore(formBean.getChainStore());
			
			response = financeService.saveFHQToDraft(formBean.getFinanceBill());
		}
		
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
		
	}
	
	/**
	 * 单据过账action
	 * @return
	 */
	public String postFHQBill(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		Response response = new Response();
		
		if (validateFinanceHQBill(response)){
			formBean.getFinanceBill().setCreatorHq(loginUserInfor);
			formBean.getFinanceBill().setChainStore(formBean.getChainStore());
			try {
			    response = financeService.postFHQBill(formBean.getFinanceBill());
			} catch (Exception e) {
				response.setQuickValue(Response.ERROR, e.getMessage());
				loggerLocal.error(e);
			}
		} 
		
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
		
	}
	
	/**
	 * 取得连锁店当前欠款
	 * @return
	 */
	public String getChainAcctFinance(){
		int chainId = formBean.getFinanceBill().getChainStore().getChain_id();
		Response response = new Response();
		
		try {
		    response = financeService.getChainCurrentFinance(chainId);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, "获取欠款发生错误，" + e.getMessage());
		}
		
		jsonMap.put("response", response);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * to validate the finance bill
	 * @return
	 */
	private boolean validateFinanceHQBill(Response response){
		FinanceBill bill = formBean.getFinanceBill();
		for (FinanceBillItem billItem: bill.getFinanceBillItemList()){
			if (billItem.getTotal() < 0){
				response.setQuickValue(Response.FAIL,"金额必须是大于或等于零的数字");
				return false;
			}   
		}
		return true;
	}
	
	/**
	 * 查询帐户流水
	 * @return
	 */
	public String searchAcctFlow(){
  
		Date startDate = formBean.getSearchStartTime();
		Date endDate = formBean.getSearchEndTime();
		int chainId = formBean.getChainStore().getChain_id();
		
		Response response = new Response();
		
		try {
			response = financeService.searchAcctFlow(startDate, endDate, chainId, false);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap =  (Map<String, Object>)response.getReturnValue();
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  

		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
}
