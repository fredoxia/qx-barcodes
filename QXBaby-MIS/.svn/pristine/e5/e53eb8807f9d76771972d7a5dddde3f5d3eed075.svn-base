package com.onlineMIS.action.headQ.chain;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.chain.ChainSalesService;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportActionFormBean;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportActionUIBean;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportBean;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Repository
public class ChainSalesAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8535488608190687809L;
	@Autowired
	private ChainSalesService chainSalesService;
	@Autowired
	private ProductBarcodeService productBarcodeService;
	
	/**
	 * the sales report template
	 */
	private final String templateFileName = "SalesReportTemplate.xls";
	private String excelFileName = "SalesReport.xls";
	private InputStream excelStream;

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	/**
	 * the bean for the sales report
	 */
	private ChainSalesReportActionFormBean formBean = new ChainSalesReportActionFormBean();
	private ChainSalesReportActionUIBean uiBean = new ChainSalesReportActionUIBean();
	public ChainSalesReportActionUIBean getUiBean() {
		return uiBean;
	}

	public void setUiBean(ChainSalesReportActionUIBean uiBean) {
		this.uiBean = uiBean;
	}

	public ChainSalesReportActionFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(ChainSalesReportActionFormBean formBean) {
		this.formBean = formBean;
	}

	/**
	 * before generate the report, need prepare some data
	 * @return
	 */
	public String preSalesReport(){
		loggerLocal.info(this.getClass().getName() + "ChainSalesAction.preSalesReport() ");
		
		uiBean.setBasicData(productBarcodeService.prepareBarcodeSourceData());

		
		return "preSalesReport";
	}
	
	/**
	 * to gerate the chain sales report
	 * @return
	 */
	public String genChainSalesReport(){
		loggerLocal.info(this.getClass().getName() + "ChainSalesAction.genChainSalesReport() : " +formBean.getOrder().getClient_id() + "," + formBean.getOrder().getClient_name() + "," + formBean.getQuarter() +  "," +formBean.getYear() +  "," +formBean.getBrands() +  "," +formBean.getStartDate() +  "," +formBean.getEndDate());
		
		ChainSalesReportBean reportBean = chainSalesService.generateChainSalesReport(formBean);
		
		ActionContext.getContext().getSession().put(Common_util.CHAIN_SALES_REPORT_RESULT, reportBean);
		
		return "chainSalesReport";
	}
	
	/**
	 * to generate the sales report in excel format
	 * @return
	 */
	public String genSalesReportForExcel(){
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/");  ;   

		ChainSalesReportBean reportBean = (ChainSalesReportBean)ActionContext.getContext().getSession().get(Common_util.CHAIN_SALES_REPORT_RESULT);
		
		Map<String,Object> map= chainSalesService.exportSalesReportExcel(reportBean,contextPath + "WEB-INF\\template\\" + templateFileName);   
		excelStream=(InputStream)map.get("stream");   
		return "exportExcel";   
	}
	
	/**
	 * before generate the customer report, it will prepare some data
	 * @return
	 */
	public String preCustomerReport(){
		loggerLocal.info(this.getClass().getName() + "ChainSalesAction.preCustomerReport()");
		
		return SUCCESS;
	}

}
