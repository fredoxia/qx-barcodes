package com.onlineMIS.action.headQ4Chain.barcodeGentor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ4Chain.barcodeGentor.BarcodeGenService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

public class BarcodeGenJSONAction extends BarcodeGenAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8480664876592918365L;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	@Autowired
	BarcodeGenService barcodeGenService;
	
	/**
	 * 检查创建product code的时候，是否已经有重复的
	 * @return
	 */
	public String checkProductCodeSerial(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = barcodeGenService.checkProductCodeSerial(formBean.getProductBarcode().getProduct(), userInfor.getMyChainStore());

		jsonMap.put("response", response);

		try {
            jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e) {
			loggerLocal.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 生成条码
	 * @return
	 */
	public String generateProductBarcodeForChain(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		/**
		 * 1.0 to save the product information
		 */
		Response response = new Response();
		try {
			barcodeGenService.saveProduct(formBean.getProductBarcode().getProduct(), formBean.getColorIds(), formBean.getSizeIds(),userInfor.getMyChainStore());
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap.put("response", response);
		
		if (response.getReturnCode() == Response.SUCCESS){

			Response response2 = barcodeGenService.getSameGroupProductBarcodes(formBean.getProductBarcode().getProduct(), userInfor.getMyChainStore());

		    if (response2.getReturnCode() == Response.SUCCESS)
			     jsonMap.put("barcodes", response2.getReturnValue());
		}

	
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		try {
	        jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
		} catch (Exception e) {
			loggerLocal.error(e);
		}
	
		return SUCCESS;
	}
	
	
	public String searchBarcode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = barcodeGenService.searchBarcodeForChain(formBean.getProductBarcode(), formBean.getStartDate(), formBean.getEndDate(), formBean.getNeedCreateDate(), userInfor.getMyChainStore());
		jsonMap.put("response", response);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());
		
		jsonObject = ProductBarcodeService.transferProductBarcode(jsonMap, jsonConfig);
		
		return SUCCESS;
		
	}
	
	public String getBasicData(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		String basicData = formBean.getBasicData();

		Response response = new Response();
		try {
		    response = barcodeGenService.getBasicDataList(basicData,this.getPage(), this.getRows(), this.getSort(), this.getOrder(), userInfor.getMyChainStore());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig(); 
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter()); 
			try {
				jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		}
		
		return SUCCESS;
	}
	
	public String updateBrand(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = new Response();
		
		try {
			response = barcodeGenService.updateBrand(formBean.getBrand(),userInfor.getMyChainStore());
		} catch (Exception e) {
			response.setFail(e.getMessage());
		}
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
}
