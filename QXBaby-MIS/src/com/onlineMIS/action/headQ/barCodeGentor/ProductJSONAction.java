package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;






import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandPriceIncreaseService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ProductJSONAction extends ProductAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2406606210572028349L;

	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}



	/**
	 * to search the product list of the given criterian
	 */
	public String execute(){
	loggerLocal.info("ProductJSONAction");
		
    	Map<String,Object> jsonMap = new HashMap<String, Object>();

		//--get the original list
		List<ProductBarcode> barcode_org = productService.getBarcodesFromCriteria(formBean.getProductBarcode(), formBean.getBrandIds(), formBean.getStartDate(), formBean.getEndDate(), formBean.getNeedCreateDate());
		jsonMap.put("barcodes", barcode_org);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());
		
		jsonObject = ProductBarcodeService.transferProductBarcode(jsonMap, jsonConfig);
		
		return SUCCESS;
	}

	
	/**
	 * when inventory scan the barcode information for the headq part
	 * 1. if it is sales order, - check its existence in the history table -get the product information directly
	 * 2. if it is sales return order, get the sales history price
	 * @return
	 */
	public String search(){
		String uuid = Common_util.getUUID();
		String log = logHQ("search", formBean.getClient_id(), formBean.getProductBarcode().getBarcode(), uuid);
		loggerLocal.info(log);
		
		//--get the original list
    	List<ProductBarcode> barcodes = new ArrayList<ProductBarcode>();
    	ProductBarcode barcode_org = null;
    	if (formBean.getOrderType() == InventoryOrder.TYPE_SALES_ORDER_W){
    		barcode_org= productService.scanProductsByBarcodeHeadq(formBean.getProductBarcode().getBarcode(), formBean.getClient_id() );
    	} else if (formBean.getOrderType() == InventoryOrder.TYPE_SALES_RETURN_ORDER_W){
    		barcode_org = productService.getProductFromSalesHistory(formBean.getProductBarcode().getBarcode(), formBean.getClient_id());
    	}
    	
    	if (barcode_org != null){
    		barcodes.add(barcode_org);
    	}
    	
		jsonMap.put("barcodes", barcodes);
    	jsonMap.put("orderType", formBean.getOrderType());
	    jsonMap.put("index", formBean.getIndexPage());
	    jsonMap.put("where", formBean.getFromSrc());
	    
	    jsonObject = ProductBarcodeService.transferProductBarcode(jsonMap, null);

		return SUCCESS;
	}
	
	/**
	 * use this function to get product and initialize the whole price
	 * mainly for the PDA
	 * @return
	 */
	public String getProductFromPDA(){
		int clientId =formBean.getClient_id();
		String barcode = formBean.getProductBarcode().getBarcode();
		String uuid = Common_util.getUUID();
		String log = logHQ("getProductFromPDA", clientId, barcode, uuid);
		loggerLocal.info(log);
		
    	int inventory = 0;
    	
		//--get the original list
    	List<ProductBarcode> barcodes = new ArrayList<ProductBarcode>();
    	ProductBarcode barcode_org = productService.getPDAProductsByBarcodeCalWholePrice(barcode, clientId);
    	if (barcode_org != null){
    		barcodes.add(barcode_org);
    		inventory = productService.getProductInven(barcode_org.getBarcode(),null);
    	}
    	
		jsonMap.put("barcodes", barcodes);
		jsonMap.put("inventory", inventory);
		
		jsonObject = ProductBarcodeService.transferProductBarcode(jsonMap, null);
		
		return SUCCESS;
	}
	
	
	/**
	 * when user want to delete the product information
	 * @return
	 */
	public String checkBarcode(){
		loggerLocal.info("ProductJSONAction - checkBarcode");

		//--get the original list
		boolean used = productService.checkBarcodeInOrder(formBean.getProductBarcode().getId());
		jsonMap.put("result", used);
		
		try{
		   jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * to synchronize the sales price from jin suan and also check the product code with jinsuan
	 * @return
	 */
	public String synchronizePrice(){
		loggerLocal.info("ProductJSONAction - synchronizePrice");

		Map<String, String> msg = productService.synchronizePrice(new HashSet<String>(selectedBarcodes));

		String ERROR_NOT_FOUND = msg.get(Common_util.ERROR_NOT_FOUND);
		String ERROR_PRICE_INCONSISTANT = msg.get(Common_util.ERROR_PRICE_INCONSISTANT);
		String ERROR_PRODUCT_CODE_INCONSISTANT = msg.get(Common_util.ERROR_PRODUCT_CODE_INCONSISTANT);
		
		if (ERROR_NOT_FOUND.length()>0)
		    jsonMap.put("ERROR_NOT_FOUND", ERROR_NOT_FOUND);
		else 
			jsonMap.put("ERROR_NOT_FOUND", "");
		
		if (ERROR_PRICE_INCONSISTANT.length()>0)
		    jsonMap.put("ERROR_PRICE_INCONSISTANT", ERROR_PRICE_INCONSISTANT);
		else 
			jsonMap.put("ERROR_PRICE_INCONSISTANT", "");
		
		if (ERROR_PRODUCT_CODE_INCONSISTANT.length()>0)
		    jsonMap.put("ERROR_PRODUCT_CODE_INCONSISTANT", ERROR_PRODUCT_CODE_INCONSISTANT);
		else 
			jsonMap.put("ERROR_PRODUCT_CODE_INCONSISTANT", "");
		
		try{
		   jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.error(e);
		}	
		return SUCCESS;
	}
	
	/**
	 * click the 生成货品和货品条码
	 */
	public String generateProductBarcode(){
		loggerLocal.info("productJSONAction - generateProductBarcode");
		if (!isError()){
			/**
			 * 1.0 to save the product information
			 */
			Response response = new Response();
			try {
				productService.saveProduct(formBean.getProductBarcode().getProduct(), formBean.getColorIds(), formBean.getSizeIds());
			
			} catch (Exception e) {
				e.printStackTrace();
				response.setQuickValue(Response.FAIL, e.getMessage());
			}
			
			jsonMap.put("returnCode", response.getReturnCode());
			
			if (response.getReturnCode() != Response.SUCCESS){
				
			    jsonMap.put("msg", response.getMessage());
			} else {
				
				List<ProductBarcode> barcodes = productService.getSameGroupProductBarcodes(formBean.getProductBarcode().getProduct());
	
			    jsonMap.put("msg", response.getMessage());
				jsonMap.put("barcodes", barcodes);
			}
		} else 
			jsonMap.put("returnCode", Response.ERROR);
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try {
            jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
		} catch (Exception e) {
			loggerLocal.error(e);
		}

		return SUCCESS;
	}
	
	/**
	 * to get the product information by the serial number
	 * 比如年，季度，货号，价格这些
	 * @return
	 */
	public String getProductInforBySerialNum(){
		Response response = new Response();
		
		try {
		    response = productService.getProductInforBySerialNum(formBean.getProductBarcode().getProduct().getSerialNum());
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setReturnCode(Response.FAIL);
		}
		
		jsonMap.put("response", response);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try {
            jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
		} catch (Exception e) {
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	public String checkProductCodeSerialNum(){

		Response response = productService.checkErrorBeforeGenerateBarcode(formBean.getProductBarcode().getProduct());
		
		String tip ="";
		int returnCode = response.getReturnCode();
		jsonMap.put("returnCode", returnCode );
		
		if (returnCode == Response.WARNING){
			List<Product> products = (List<Product>)response.getReturnValue();
			tip = response.getMessage() + "\n";
			
			for (int i =0; i < products.size() ; i++)
			       tip += "          " + products.get(i).toString() + "\n";
		} else if (returnCode == Response.FAIL){
			tip = response.getMessage();
		}
		
		jsonMap.put("tip", tip);
		jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 获取所有品牌价格变动的记录
	 * @return
	 */
	public String getAllBrandPriceIncrease(){
		Response response = new Response();
		try {
		    response = brandPriceIncreaseService.getAllBrandPriceIncrease();
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"chainStore"} );
			
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
	 * 保存 brand price increase
	 * @return
	 */
	public String saveBrandPriceIncrease(){
		Response response = new Response();
		try {
			
		    response = brandPriceIncreaseService.saveBrandPriceIncrease(formBean.getBpi(), formBean.getProductBarcode().getProduct().getBrand());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}

		
		return SUCCESS;
	}
	
	/**
	 * 删除 brand price increase
	 * @return
	 */
	public String deleteBrandPriceIncrease(){
		Response response = new Response();
		try {
			
		    response = brandPriceIncreaseService.deleteBrandPriceIncrease(formBean.getBpi());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){

			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * to validate the input data
	 */
	private boolean isError(){
		boolean error = false;
		Product product = formBean.getProductBarcode().getProduct();

		if (product.getYear().getYear_ID()==0 || product.getBrand().getBrand_ID()==0|| product.getCategory().getCategory_ID()==0 || product.getCategory().getCategory_ID()==0){
			jsonMap.put("error", "错误：条形码基础资料不完整，请先完善基础资料" );
			error = true;
		}else if (product.getProductCode().equals("")){
			jsonMap.put("error", "错误：货号是空，请填写货号" );
			error = true;
		}
		
		return error;
	}
	
	private String logHQ(String action, Object clientId, Object orderId, String uuid){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		String log = this.getClass().getSimpleName() + "." + action +","  + clientId + "," + orderId  + "," + loginUserInfor.getUser_name()+ "," + uuid;
		return log;		
	}

}
