package com.onlineMIS.action.headQ.barCodeGentor;


import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ProductJSPAction extends ProductAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -161455229996547363L;
	/**
	 * the four parameters are for the export files
	 */
	private final String templateFileName = "BarcodeTemplate.xls";
	private InputStream excelStream;
	private String excelFileName = "barcodeFiles.xls";
	private final String JinsuanFileName = "JinSuanOrderTemplate.xls";
	
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

    /**
     * export the barcodes to the file which could be used to crate the barcode
     */
	public String execute() throws Exception {   
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Map<String,Object> map= productService.generateExcelReport(selectedBarcodes,contextPath + "WEB-INF\\template\\" + templateFileName);   
		excelStream=(InputStream)map.get("stream");   
		return "report";   
	}  
	
	/**
     * export the barcodes to the file which could be used to print the barcode
     */
	public String exportToPrintBarcode() throws Exception {   
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Map<String,Object> map= productService.generateJinsuanExcelReport(new HashSet<String>(selectedBarcodes),contextPath + "WEB-INF\\template\\" + JinsuanFileName);   
		excelStream=(InputStream)map.get("stream");   
		return "report";   
	} 
	
	/**
	 * when user scan the product by product code
	 * @return
	 */
	public String scanByProductCode(){
		String productCode = Common_util.decode(formBean.getProductBarcode().getProduct().getProductCode());
		List<ProductBarcode> products = productService.getProductsForSimiliarProductCode(productCode, 0, formBean.getPager());
		uiBean.setProducts(products);
		uiBean.setIndex(formBean.getIndexPage());
		
		return "ProductCodeSearchProducts";
	}
	
	/**
	 * pre-create barcode product
	 */
	public String preCreateProduct(){
		loggerLocal.info("ProductJSPAction - preCreateProduct");
		
		uiBean.setBasicData(productService.prepareBarcodeSourceDataForHead());
		
		productService.prepareCreateProductUIForm(formBean);
		
		return "create";
	}

	
	/**
	 * view the product infromation
	 * @return
	 */
	public String viewProduct(){
		loggerLocal.info("ProductJSPAction - searchForUpdate");
		
		productService.prepareBarcodeSourceData();
		
		ProductBarcode product = productService.getProductsByBarcode(formBean.getProductBarcode().getBarcode());
		if (product == null){
			System.out.print("无法找到相应信息");
		}

		uiBean.setProduct(product);
		
		uiBean.setCanViewRecCost(true);
		
		return "ViewProduct";		
	}

	/**
	 * search the product information for update
	 * @return
	 */
	public String searchForUpdate(){
		loggerLocal.info("ProductJSPAction - searchForUpdate");
		
		uiBean.setBasicData(productService.prepareBarcodeSourceDataForHead());

		ProductBarcode product = productService.getProductsByBarcode(formBean.getProductBarcode().getBarcode());
		if (product == null){
			addActionMessage("不能找到对应产品");
		} else {
			uiBean.setProduct(product);
			formBean.setProductBarcode(product);
		}
		
		return SUCCESS;
	}
	
	/**
	 * when user press the search hyperlink
	 * @return
	 */
	public String preSearch(){
		loggerLocal.info("ProductJSPAction - preSearch");
		
		uiBean.setBasicData(productService.prepareBarcodeSourceData());
		
		return "search";
	}
	
	/**
	 * update the product information
	 * @return
	 */
	public String update(){
		loggerLocal.info("ProductJSPAction - update");
		
		Response response = productService.updateProduct(formBean.getProductBarcode());
		if (response.isSuccess())
		    return "update";
		else {
			addActionError(response.getMessage());
			return searchForUpdate();
		}
	}
	
	/**
	 * delete the product information
	 * @return
	 */
	public String delete(){
		loggerLocal.info("ProductJSPAction - delete");
		
		productService.deleteProductBarcode(formBean.getProductBarcode().getId());
		return "delete";
	}
	
	/**
	 * scan the brands by brand name
	 * @return
	 */
	public String scanBrand(){
		loggerLocal.info("ProductJSPAction - scanBrand");
		
		String brandName = Common_util.decode(formBean.getProductBarcode().getProduct().getBrand().getBrand_Name());
		
		List<Brand> brands = productService.searchBrands(brandName);
		
		uiBean.setBrands(brands);
		
		return "BrandList";
	}
	
	public String searchColor(){
		loggerLocal.info("ProductJSPAction - scanColor");
		
		String colorNames = Common_util.decode(formBean.getColorNames());
		
		List<Color> colors = productService.searchColors(colorNames);
		
		uiBean.setColors(colors);
		
		return "ColorList";	
	}

}
