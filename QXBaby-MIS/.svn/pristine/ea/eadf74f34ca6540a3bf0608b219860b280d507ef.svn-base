package com.onlineMIS.action.chainS.charts;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesService;
import com.onlineMIS.ORM.entity.chainS.report.ChainMonthlyHotProduct;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklyHotProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainDailySalesChartJSPAction extends ChainDailySalesChartAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -106981278476644202L;
	
	@Autowired
	private ChainDailySalesService chainDailySalesService;

	

	public String preGenChart(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preGenChart");
    	
		chainDailySalesService.prepareGenChartUI(uiBean, formBean, loginUser);
		
		return "preGenChart";
	}
	
	/**
	 * 生成每周热销品牌的界面
	 * @return
	 */
	public String preGenWeeklyHotBrand(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preGenWeeklyHotBrand");
    	
		chainDailySalesService.prepareGenWeeklyHotBrandUI(uiBean, formBean, loginUser);
				
		return "preGenWeeklyHotBrand";
	}
	
	/**
	 * 生成每月热销品牌的界面
	 * @return
	 */
	public String preGenMonthlyHotBrand(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preGenMonthlyHotBrand");
    	
		chainDailySalesService.prepareGenMonthlyHotBrandUI(uiBean, formBean, loginUser);
				
		return "preGenMonthlyHotBrand";
	}
	
	/**
	 * 获取某个热销品牌下地热销货品
	 * @return
	 */
	public String genWeeklyHotProductsInBrand(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genWeeklyHotProductsInBrand  : " + formBean);
    	
    	Response response = new Response();
    	
    	try {
    		response = chainDailySalesService.genWeeklyHotProductInBrand(formBean.getChainStore().getChain_id(), formBean.getBrandId(), formBean.getStartDate());
    	} catch (Exception e) {
			loggerLocal.errorB(e);
		}
    	
    	List<ChainWeeklyHotProduct> hotProducts = new ArrayList<ChainWeeklyHotProduct>();
    	if (response.getReturnCode() == Response.SUCCESS){
    		hotProducts = (List<ChainWeeklyHotProduct>)response.getReturnValue();
    	}
    	uiBean.setHotProducts(hotProducts);
    	
    	return "genHotProducts";
	}
	
	/**
	 * 获取某个月/季度热销品牌下地热销货品
	 * @return
	 */
	public String genMonthlyHotProductsInBrand(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genMonthlyHotProductsInBrand  : " + formBean);
    	
    	Response response = new Response();
    	
    	try {
    		response = chainDailySalesService.genMonthlyHotProductInBrand(formBean.getChainStore().getChain_id(), formBean.getBrandId(), formBean.getReportYear(), Common_util.getList(formBean.getMonths()));
    	} catch (Exception e) {
			loggerLocal.errorB(e);
		}
    	
    	List<ChainMonthlyHotProduct> hotProducts = new ArrayList<ChainMonthlyHotProduct>();
    	if (response.getReturnCode() == Response.SUCCESS){
    		hotProducts = (List<ChainMonthlyHotProduct>)response.getReturnValue();
    	}
    	uiBean.setHotMProducts(hotProducts);
    	
    	return "genMonthlyHotProducts";
	}
}
