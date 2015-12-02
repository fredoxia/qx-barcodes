package com.onlineMIS.action.chainS.sales;

import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class PurchaseJSPAction extends PurchaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8255734385303129990L;
	/**
	 * the inventory order template
	 */
	private final String templateFileName = "PurchaseOrderTemplate.xls";
	private String excelFileName = "CaiGouDan.xls";
	private InputStream excelStream;
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	/**
	 * pre-search purchase order, to prepare the UI data
	 * @return
	 */
	public String preSearch(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearch");
    	
		purchaseService.prepareSearchUI(formBean, uiBean, userInfor);
		
		return "SearchPurchase";
	}

	
	/**
	 * 获取采购单的详细信息
	 * @return
	 */
	public String getPurchase(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	int order_id = formBean.getOrder().getOrder_ID();
    	
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getPurchase : " + order_id);

		InventoryOrder order = purchaseService.getPurchaseById(order_id, userInfor);
		
		if (order == null)
			addActionError("无法获得采购单据，请重新查询");
		else{
			purchaseService.prepareFormUIBean(formBean, uiBean, order);

		}
		
		return "PurchaseOrderDetail";
	}
	
	/**
	 * 把采购单据导出到excel
	 * @return
	 */
	public String exportPurchaseToExcel(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/");

		boolean displayCost = false;
		if (userInfor.containFunction(ChainUserInfor.SEE_COST_FUN))
			displayCost = true;
		Map<String,Object> map= purchaseService.generateExcelReport(formBean.getOrder(),contextPath + "WEB-INF\\template\\" + templateFileName, displayCost);   
		excelStream=(InputStream)map.get("stream");  
		
		return "excelObject";
	}

}
