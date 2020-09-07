package com.onlineMIS.action.chainS.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.base.Pager;

import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainReport;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesReportVIPPercentageTemplate;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainReportJSPAction extends ChainReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2591790047835294824L;
	private final String templateFileName = "ChainSalesReportTemplate.xls";
	private final String CHAIN_SALES_STATISC_REPORT_TEMPLATENAME = "ChainSalesStatisticsReportTemplate.xls";
	private final String CHAIN_SALES_STATISC_DETAIL_REPORT_TEMPLATENAME = "ChainSalesStatisticsDetailReportTemplate.xls";
	private String excelFileName = "XiaoShouBaoBiao.xls";
	private InputStream excelStream;
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getExcelFileName() {
		   return this.excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = Common_util.encodeAttachment(excelFileName);
	}

	/**
	 * the action to prepare generate the sales report
	 * @return
	 */
	public String preSalesReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSalesReport");
    	
		formBean.setReportType(ChainReport.TYPE_SALES_REPORT);
		this.getUiBean().setReportTypeS(ChainReport.getTypeMap().get(ChainReport.TYPE_SALES_REPORT));

		chainReportService.prepareGenSalesReportUI(formBean, uiBean, userInfor);
		
		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			return "PreSalesReportHQ";
		} else 
		   return "PreSalesReport";
	}
	
	/**
	 * 准备sale analysis report的UI
	 * @return
	 */
	public String preSalesAnalysisReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSalesAnalysisReport");

    	chainReportService.prepareSalesAnalysisReportUI(formBean);
    	
		return "PreSalesAnalysisReport";
	}
	
	/**
	 * the action to prepare generate the purchase report
	 * @return
	 */
	public String prePurchaseReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"prePurchaseReport");
    	
		formBean.setReportType(ChainReport.TYPE_PURCHASE_REPORT);
		this.getUiBean().setReportTypeS(ChainReport.getTypeMap().get(ChainReport.TYPE_PURCHASE_REPORT));

		chainReportService.prepareGenReportUI(formBean, uiBean, userInfor);
		
		return "PreReport";
	}
	
	/**
	 * the action to prepare generate the finance report
	 * @return
	 */
	public String preFinanceReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preFinanceReport");
    	
		formBean.setReportType(ChainReport.TYPE_FINANCE_REPORT);
		this.getUiBean().setReportTypeS(ChainReport.getTypeMap().get(ChainReport.TYPE_FINANCE_REPORT));

		chainReportService.prepareGenReportUI(formBean, uiBean, userInfor);
		
		return "PreReport";
	}
	
	/**
	 * 准备销售统计报表ui
	 * @return
	 */
	public String preSalesStatisticReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSalesStatisticReport");
    	
    	chainReportService.prepareSalesStatisticReportUIBean(formBean, uiBean, userInfor);
    	
		return "PreSalesStatisticReport";
	}
	
	/**
	 * 准备采购统计报表ui
	 * @return
	 */
	public String prePurchaseStatisticReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"prePurchaseStatisticReport");
    	
    	chainReportService.preparePurchaseStatisticReportUIBean(formBean, uiBean, userInfor);
    	
		return "PrePurchaseStatisticReport";
	}


	/**
	 * 生成销售统计报表
	 * @return
	 */
	public String generateSalesStatisticReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesStatisticReport : " + formBean);
    	
    	
    	return "salesStatisticReport";
	}
	
	/**
	 * 生成采购统计报表
	 * @return
	 */
	public String generatePurchaseStatisticReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generatePurchaseStatisticReport : " + formBean);
    	
    	return "purchaseStatisticReport";
	}
	/**
	 * 准备综合报表ui
	 * @return
	 */
	public String preAllInOneReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preAllInOneReport");
    	
    	chainReportService.preparePurchaseStatisticReportUIBean(formBean, uiBean, userInfor);
    	
		return "PreAllInOneReport";
	}
	
	
	/**
	 * 生成综合统计报表
	 * @return
	 */
	public String generateAllInOneReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateAllInOneReport : " + formBean);
    	
    	return "allInOneStatisticReport";
	}

	
	/**
	 * the action to generate the 销售报表 report 到 excel
	 * @return
	 */
	public String generateSalesReportToExcel(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateSalesReportToExcel : " + formBean);
    	
    	//1. 获取现在的销售数据
		Response response = new Response();
		try {
		    response = chainReportService.generateSalesReport(formBean, null, null, this.getSort(), this.getOrder());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			//2. 获取去年的销售和千禧，千禧净销售
			Map lastYearData = null;
			Response response2 = chainReportService.generateLastYearSalesReport(formBean);
			if (response2.isSuccess())
				lastYearData = (Map)response2.getReturnValue();
			
			HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
			String contextPath= request.getRealPath("/");
			Map dataMap = (Map)response.getReturnValue();
			
			Map<String,Object> map= chainReportService.generateSalesRptExcel(dataMap, lastYearData, contextPath + "WEB-INF\\template\\" + templateFileName, formBean.getStartDate(), formBean.getEndDate());   
			excelStream=(InputStream)map.get("stream");  
			
			return "report";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 生成按照销售人员排列的销售报表
	 * @return
	 */
	public String preSalesReportBySaler(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSalesReportBySaler");
    	
		formBean.setReportType(ChainReport.TYPE_SALES_REPORT);
		this.getUiBean().setReportTypeS(ChainReport.getTypeMap().get(ChainReport.TYPE_SALES_REPORT));

		chainReportService.prepareGenSalesReportBySalerUI(formBean, uiBean, userInfor);
		
		return "PreSalesReportBySaler";
	}
	
	/**
	 * 生成销售统计报表的excel格式
	 * @return
	 */
	public String generateChainSalesStatisticExcelReport(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"generateChainInventoryExcelReport");

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Response response = new Response();
		try {
			response = chainReportService.generateChainSalesStatisticExcelReport(formBean.getParentId(),formBean.getChainStore().getChain_id(), formBean.getSaler().getUser_id(), formBean.getStartDate(), formBean.getEndDate(), formBean.getYear().getYear_ID(), formBean.getQuarter().getQuarter_ID(), formBean.getBrand().getBrand_ID(), loginUserInfor, contextPath + "WEB-INF\\template\\" + CHAIN_SALES_STATISC_REPORT_TEMPLATENAME);     
		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
 
		if (response.getReturnCode() == Response.SUCCESS){
		    InputStream excelStream= (InputStream)response.getReturnValue();
		    this.setExcelStream(excelStream);
		    this.setExcelFileName("SalesStatisticExcelReport.xls");
		    return "report"; 
		} else 
			return ERROR;		
	}
	
	/**
	 * 生成销售统计报表明晰的excel格式
	 * @return
	 */
	public String generateChainSalesStatisticExcelDetailReport(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"generateChainInventoryExcelDetailReport");

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Response response = new Response();
		try {
			response = chainReportService.generateChainSalesStatisticExcelReportDetail(formBean.getParentId(),formBean.getChainStore().getChain_id(), formBean.getSaler().getUser_id(), formBean.getStartDate(), formBean.getEndDate(), formBean.getYear().getYear_ID(), formBean.getQuarter().getQuarter_ID(), formBean.getBrand().getBrand_ID(), loginUserInfor, contextPath + "WEB-INF\\template\\" + CHAIN_SALES_STATISC_DETAIL_REPORT_TEMPLATENAME);     
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
 
		if (response.getReturnCode() == Response.SUCCESS){
		    InputStream excelStream= (InputStream)response.getReturnValue();
		    this.setExcelStream(excelStream);
		    this.setExcelFileName("SalesStatisticExcelReport.xls");
		    return "report"; 
		} else 
			return ERROR;		
	}
	
	/**
	 * 生成采购统计报表的excel格式
	 * @return
	 */
	public String generateChainPurchaseStatisticExcelReport(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"generateChainPurchaseStatisticExcelReport");

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Response response = new Response();
		try {
			response = chainReportService.generateChainPurchaseStatisticExcelReport(formBean.getParentId(),formBean.getChainStore().getChain_id(), formBean.getStartDate(), formBean.getEndDate(), formBean.getYear().getYear_ID(), formBean.getQuarter().getQuarter_ID(), formBean.getBrand().getBrand_ID(), loginUserInfor, contextPath + "WEB-INF\\template\\");     
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
 
		if (response.getReturnCode() == Response.SUCCESS){
		    InputStream excelStream= (InputStream)response.getReturnValue();
		    this.setExcelStream(excelStream);
		    this.setExcelFileName("PurchaseStatisticExcelReport.xls");
		    return "report"; 
		} else 
			return ERROR;		
	}
	
	
	/**
	 * 准备vip消费报表
	 * @return
	 */
	public String preVIPConsumpReport(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preVIPConsumpReport");
    	
		chainReportService.prepareVIPConsumpReportUI(formBean, uiBean, userInfor);
		
		return "PreVIPConsumpReport";
	}
	
	/**
	 * 准备生成报表库的页面
	 * @return
	 */
	public String preChainAutoRptRepository(){
		chainReportService.prepareChainRptRepositoryUI(formBean, uiBean);
		
		return "rptRepositoryUI";
	}
	
	/**
	 * 生成报表
	 * @return
	 */
	public String generateChainRptRepository(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"generateChainRptRepository");
    	
    	ChainBatchRptRepositoty rptRepository =  formBean.getRptRepository();

		Response response = new Response();
		try {
			response = chainReportService.loadBatchRptRepository(rptRepository, userInfor);     
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
 
		if (response.getReturnCode() == Response.SUCCESS){
			Map<String, Object> result = (Map)response.getReturnValue();
		    InputStream excelStream= (InputStream)result.get("download");
		    this.setExcelStream(excelStream);
		    
		    String fileName = "";
			fileName = (String)result.get("name");           
			this.setExcelFileName(fileName);

			String type = (String)result.get("Type");
			if (type.equals("zip"))
		         return "zipReport"; 
			else 
				return "report";
		} else 
			return ERROR;	
	}
	
	/**
	 * VIP每个类别销售的占比，每天晚上运行处理的报表。通过界面下载
	 * @return
	 */
	public String preVIPSalesAnalysisRpt(){
		formBean.setStartDate(Common_util.getYestorday());
		return "vipSalesAnalysisRpt";
	}
	
	/**
	 * 下载vip excel报表
	 * @return
	 * @throws FileNotFoundException
	 */
	public String getVIPSalesAnalysisRpt() throws FileNotFoundException{
		Date rptDate = formBean.getStartDate();
		
		Response response = chainReportService.getVIPSalesAnalysisRpt(rptDate);
		if (response.isSuccess()){
			File excelFile = (File)response.getReturnValue();
		    InputStream excelStream= new FileInputStream(excelFile);
		    this.setExcelStream(excelStream);
		    this.setExcelFileName(ChainSalesReportVIPPercentageTemplate.getFileName(rptDate));
			return "report";
		} else {
			addActionError("无法找到 " + rptDate + "的报表");
			return "vipSalesAnalysisRpt";
		}
	}
	
	/**
	 * 下载 客户acct flow 成excel report
	 * @return
	 */
	public String downloadVIPConsumpReport(){
		loggerLocal.info(this.getClass().getName()+ ".downloadVIPConsumpReport");
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Response response = new Response();
		try {
		     response = chainReportService.downloadVIPConsumpReport(formBean, contextPath + "WEB-INF\\template\\");
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
		 
		if (response.getReturnCode() == Response.SUCCESS){
		    InputStream excelStream= (InputStream)response.getReturnValue();
		    this.setExcelStream(excelStream);
		    this.setExcelFileName("VIPXiaoFeiBaoBiao.xls");
		    return "report"; 
		} else 
			return ERROR;	
	}

}
