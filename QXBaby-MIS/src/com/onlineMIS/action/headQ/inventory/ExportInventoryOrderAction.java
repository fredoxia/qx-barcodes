package com.onlineMIS.action.headQ.inventory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;  

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryService;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ExportInventoryOrderAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4270497219043844133L;
	/**
	 * the inventory order template
	 */
	private final String templateFileName = "InventoryOrderTemplate.xls";
	private String excelFileName = "InventoryOrder.xls";
	
	/**
	 * the barcode file
	 */
	private final String templateJinsuanFileName = "JinSuanOrderTemplate.xls";
	private  String jinsuanFileName = "JinSuanOrder";
	
	/**
	 * the barcode file
	 */
	private final String templatePDAOrderFileName = "PDAOrderTemplate.xls";
	private final String orderFileName = "PDAOrder.xls";
	
	private InputStream excelStream;
	private InventoryOrderActionFormBean formBean = new InventoryOrderActionFormBean();
	
	@Autowired
    private InventoryService inventoryService;


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
	public InventoryOrderActionFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(InventoryOrderActionFormBean formBean) {
		this.formBean = formBean;
	}

	/**
	 * to export the inventory order to excel
	 */
	public String execute() throws Exception {   
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/");  ;   

		Map<String,Object> map= inventoryService.generateExcelReport(formBean.getOrder(),contextPath + "WEB-INF\\template\\" + templateFileName);   
		excelStream=(InputStream)map.get("stream");   
		return SUCCESS;   
	}   

	/**
	 * to export the inventory order's barcode to excel file
	 * @return
	 */
	public String ExportJinSuanOrder(){
		
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		
		Map<String,Object> map= inventoryService.generateJinsuanExcelReport(formBean.getOrder(),contextPath + "WEB-INF\\template\\" + templateJinsuanFileName);     
 
		Object fileExt = map.get("fileExt");
		if (fileExt != null){
			jinsuanFileName += fileExt + ".xls";
		} else 
			jinsuanFileName += ".xls";
		
		excelFileName = jinsuanFileName;
		
		excelStream=(InputStream)map.get("stream");   
		return SUCCESS;   
	}
	
	/**
	 * 下载配货单
	 * @return
	 */
	public String downloadOrder(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		loggerLocal.info("InventoryOrderAction - downloadOrder");
		
		excelFileName = orderFileName;
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		
		Map<String,Object> map= inventoryService.generatePDAOrder(formBean.getOrder(),contextPath + "WEB-INF\\template\\" + templatePDAOrderFileName);     
 
		excelStream=(InputStream)map.get("stream");   
		return SUCCESS;   
		
	}

}
