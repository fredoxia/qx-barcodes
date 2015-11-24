package com.onlineMIS.action.chainS.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.report.ChainReport;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class ChainReportJSONAction extends ChainReportAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1559114302026107288L;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
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
	public String generateSalesReportByHQ(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesReportByHQ : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainReportService.generateSalesReport(formBean, this.getPage(), this.getRows(), this.getSort(), this.getOrder());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
			try {
				jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询连锁店的销售分析报表
	 * @return
	 */
	public String generateSalesAnalysisReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesAnalysisReport : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainReportService.generateSalesAnalysisReport(formBean, this.getPage(), this.getRows(), this.getSort(), this.getOrder(),userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"reportDate","createDate"} );
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		    jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
		    return SUCCESS;
		} else 
			return ERROR;
		
		
	}
	/**
	 * the action to generate the 销售报表 report
	 * @return
	 */
	public String generateSalesReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesReport : " + formBean);
    	
		formBean.setReportType(ChainReport.TYPE_SALES_REPORT);
		Response response = new Response();
		try {
		    response = chainReportService.generateChainReport(formBean);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		try{
			ChainReport value = (ChainReport) response.getReturnValue();
			jsonMap.put("report", value);
			jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"generateSalesReport");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * the action to generate the purchase report
	 * @return
	 */
	public String generatePurchaseReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generatePurchaseReport : " + formBean);
    	
		formBean.setReportType(ChainReport.TYPE_PURCHASE_REPORT);
		Response response = new Response();
		try {
		    response = chainReportService.generateChainReport(formBean);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		try{
			jsonMap.put("report", response.getReturnValue());

			jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"generatePurchaseReport");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * the action to generate the finance report
	 * @return
	 */
	public String generateFinanceReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateFinanceReport : " + formBean);
    	
		formBean.setReportType(ChainReport.TYPE_FINANCE_REPORT);
		Response response = new Response();
		try {
		    response = chainReportService.generateChainReport(formBean);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		try{
			jsonMap.put("report", response.getReturnValue());

			jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"generateFinanceReport");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	public String generateSalesReportBySaler(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesReportBySaler : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainReportService.generateSalesReportBySaler(formBean, this.getPage(), this.getRows(), this.getSort(), this.getOrder(), userInfor);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();

			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"roleType","myChainStore","chainUserFunctions"} );
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
			
			try{
			   jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"generateSalesReportBySaler");
				loggerLocal.error(e);
			}

		}
		
		return SUCCESS;
	}
	
	public String generateVIPConsumpReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateVIPConsumpReport : " + formBean);
    	
		Response response = new Response();
		try {
		    response = chainReportService.generateVIPConsumpReport(formBean, this.getPage(), this.getRows(), null, null);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
    		
			//jsonConfig.setExcludes( new String[]{"issueChainStore"} );
			try {
		       jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	

}
