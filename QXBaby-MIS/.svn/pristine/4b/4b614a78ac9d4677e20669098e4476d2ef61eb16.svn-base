package com.onlineMIS.action.chainS.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesService;
import com.onlineMIS.ORM.entity.chainS.report.ChainMonthlyHotBrand;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklyHotBrand;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ChainDailySalesChartJSONAction  extends ChainDailySalesChartAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1355361450879993993L;
	private List<Integer> brandIds = new ArrayList<Integer>();


	public List<Integer> getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}
	@Autowired
	private ChainDailySalesService chainDailySalesService;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
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
	 * 获取每周热卖品牌
	 * @return
	 */
	public String genWeeklyHotBrands(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genWeeklyHotBrands : " + formBean);
    	
    	List<ChainWeeklyHotBrand> hotBrands = new ArrayList<ChainWeeklyHotBrand>();
    	
    	hotBrands = chainDailySalesService.genWeeklyHotBrands(formBean.getStartDate(), formBean.getChainStore().getChain_id());
    	
    	try {
    		JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
    		jsonConfig.setExcludes( new String[]{"chainStore"} );
    		
			jsonArray = JSONArray.fromObject(hotBrands, jsonConfig);
    	} catch (Exception e){
    		loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"loginUser");
    		loggerLocal.error(e);
    	}
    	
    	return SUCCESS;
	}
	
	/**
	 * 获取每天的销售额，展现成图表，
	 * 在
	 * @return
	 */
	public String genDailySalesReport(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genDailySalesReport : " + formBean);

    	Response response = new Response();
    	try {
    	     response = chainDailySalesService.generateSalesDailyReport(formBean.getChainStore().getChain_id(), formBean.getStartDate(), formBean.getEndDate());
    	} catch (Exception e) {
    		 loggerLocal.error(e);
			 response.setQuickValue(Response.FAIL, e.getMessage());
		}
    	
    	jsonMap.put("response", response);
    	
    	try {
    		
			jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"genDailySalesReport");
			loggerLocal.error(e);
		}
    	
    	return "successful";
	}
	
	/**
	 * 获取月或者季度热销brand
	 * @return
	 */
	public String genMonthlyHotBrands(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genMonthlyHotBrands : " + formBean);
    	
    	List<ChainMonthlyHotBrand> hotBrands = new ArrayList<ChainMonthlyHotBrand>();
    	
    	hotBrands = chainDailySalesService.genMonthlyHotBrands(formBean.getReportYear(), Common_util.getList(formBean.getMonths()), formBean.getChainStore().getChain_id());
    	
    	try {
    		JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
    		jsonConfig.setExcludes( new String[]{"chainStore"} );
    		
			jsonArray = JSONArray.fromObject(hotBrands, jsonConfig);
    	} catch (Exception e){
    		loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"loginUser");
    		loggerLocal.error(e);
    	}
    	
    	return SUCCESS;
	}
}
