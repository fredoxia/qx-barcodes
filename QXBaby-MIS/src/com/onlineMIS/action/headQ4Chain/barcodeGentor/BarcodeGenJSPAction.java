package com.onlineMIS.action.headQ4Chain.barcodeGentor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ4Chain.barcodeGentor.BarcodeGenService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class BarcodeGenJSPAction extends BarcodeGenAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8480664876592918365L;
	private final String templateFileName = "ChainBarcodeTemplate.xls";
	private String excelFileName = "barcodeFiles.xls";
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

	public String getTemplateFileName() {
		return templateFileName;
	}
	@Autowired
	BarcodeGenService barcodeGenService;
	
	@Autowired
	protected ProductBarcodeService productService;
	
	/**
	 * 成功之后登陆main
	 * @return
	 */
	public String goMain(){
		return "main";
	}

	/**
	 * 获取barcode之前，去获取页面需要的东西
	 * @return
	 */
	public String preGenBarcode(){
		barcodeGenService.prepareGenBarcodeUI(formBean, uiBean);
		
		return "barcodeGen";
	}
	
	/**
	 * 准备搜索条码
	 * @return
	 */
	public String preSearchBarcode(){
		barcodeGenService.prepareGenBarcodeUI(formBean, uiBean);
		
		return "barcodeSearch";
	}
	
	public String scanByBrandName(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		String brandName = Common_util.decode(formBean.getProductBarcode().getProduct().getBrand().getBrand_Name());
		
		Response response = barcodeGenService.searchBrands(brandName, userInfor.getMyChainStore());
		if (response.getReturnCode() != Response.SUCCESS)
			addActionError(response.getMessage());
		else 
			uiBean.setBrands((List<Brand>)response.getReturnValue());
		
		return "BrandList";
	}
	
	public String searchColor(){
		loggerLocal.info("ProductJSPAction - scanColor");
		
		String colorNames = Common_util.decode(formBean.getColorNames());
		
		List<Color> colors = productService.searchColors(colorNames);
		
		uiBean.setColors(colors);
		
		return "ColorList";	
	}
	
	/**
	 * search the product information for update
	 * @return
	 */
	public String searchForUpdate(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		barcodeGenService.prepareGenBarcodeUI(formBean, uiBean);

		Response response = barcodeGenService.getProductsByBarcode(formBean.getProductBarcode().getBarcode(), userInfor.getMyChainStore());
		if (response.isSuccess()){
			ProductBarcode product = (ProductBarcode)response.getReturnValue();
			uiBean.setProduct(product);
			formBean.setProductBarcode(product);
		} else {
			addActionMessage(response.getMessage());
		}
		
		return "updateProduct";
	}
	
	public String exportBarcode(){
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Map<String,Object> map = barcodeGenService.generateBarcodeInExcel(formBean.getSelectedBarcodes(),contextPath + "WEB-INF\\template\\" + templateFileName);   
		excelStream=(InputStream)map.get("stream");  
		
		return "report"; 
	}
	
	public String updateProduct(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = barcodeGenService.updateChainProduct(formBean.getProductBarcode(),userInfor.getMyChainStore());
		if (response.isSuccess())
		    return "updateProductSuccess";
		else {
		   addActionError(response.getMessage());
		   return searchForUpdate();
		}
	}
	
	public String deleteBarcode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = barcodeGenService.deleteChainBarcode(formBean.getProductBarcode(),userInfor.getMyChainStore());
		if (response.isSuccess())
		    return "updateProductSuccess";
		else {
		   addActionError(response.getMessage());
		   return searchForUpdate();
		}
	}
	
	public String continueCreateBarcode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		Response response = barcodeGenService.continueCreateBarcode(formBean.getProductBarcode(),userInfor.getMyChainStore());
		if (response.isSuccess()){
			barcodeGenService.prepareGenBarcodeUI(formBean, uiBean);
			Map dataMap = (Map)response.getReturnValue();
			Product product = (Product)dataMap.get("product");
			formBean.getProductBarcode().setProduct(product);
			String colors = (String)dataMap.get("color");
			uiBean.setColorNames(colors);
		    return "barcodeGen";
		} else {
		   addActionError(response.getMessage());
		   return ERROR;
		}
	}
	
	/**
	 * 点击 条形码基础资料之后
	 */
	public String preMaintainBasic(){
		return "preMaintain";
	}
	
	/**
	 * 选了basic data的下拉菜单之后
	 * @return
	 */
	public String changeBasicData(){
		String basicData = formBean.getBasicData();
		
		//Map saleReport = new HashMap();
//		barcodeGenService.prepareListUI(basicData,this.getPage(), this.getRows(), this.getSort(), this.getOrder(),saleReport);
		
		if (basicData.equalsIgnoreCase("brand")){
			return "listBrand";	
		}
		
		return ERROR;
	}
	
	public String preAddBasicData(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		String basicData = formBean.getBasicData();
		

		Response response = barcodeGenService.getBasicData(basicData, formBean.getBasicDataId(), userInfor.getMyChainStore());
		
		if (basicData.equalsIgnoreCase("brand") && response.isSuccess()){
			Object object = response.getReturnValue();
			
			if (object != null)
				formBean.setBrand((Brand)object);
			return "updateBrand";	
		}
		return ERROR;
	}
}
