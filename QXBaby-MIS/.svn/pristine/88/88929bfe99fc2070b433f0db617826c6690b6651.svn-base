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
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ChainMonthlySalesChartJSONAction  extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1355361450879993993L;
	protected ChainSalesChartFormBean formBean = new ChainSalesChartFormBean();
	protected ChainSalesChartUIBean uiBean = new ChainSalesChartUIBean();
	
	

	public ChainSalesChartUIBean getUiBean() {
		return uiBean;
	}

	public void setUiBean(ChainSalesChartUIBean uiBean) {
		this.uiBean = uiBean;
	}

	public ChainSalesChartFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(ChainSalesChartFormBean formBean) {
		this.formBean = formBean;
	}
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

	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}


	/**
	 * 获取月或者季度热销brand
	 * @return
	 */
	public String genMonthlyHotBrands(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genMonthlyHotBrands : " + formBean);
    	
    	List<ChainMonthlyHotBrand> hotBrands = new ArrayList<ChainMonthlyHotBrand>();
    	
    	hotBrands = chainDailySalesService.genMonthlyHotBrands(formBean.getReportYear(), new ArrayList<Integer>(), formBean.getChainStore().getChain_id());
    	
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
