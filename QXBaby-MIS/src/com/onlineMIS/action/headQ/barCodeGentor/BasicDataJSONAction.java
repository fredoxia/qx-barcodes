package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.common.loggerLocal;

public class BasicDataJSONAction extends BasicDataAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5466084514657131241L;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * 翻页时候获取basic data
	 * @return
	 */
	public String getBasicData(){
		String basicData = formBean.getBasicData();

		Response response = new Response();
		try {
		    response = basicDataService.prepareListUI(basicData,this.getPage(), this.getRows(), this.getSort(), this.getOrder());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
		    jsonObject = JSONObject.fromObject(jsonMap);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新update year
	 * @return
	 */
	public String updateYear(){
		Response response = basicDataService.updateYear(formBean.getYear());
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 更新update quarter
	 * @return
	 */
	public String updateQuarter(){
		Response response = basicDataService.updateQuarter(formBean.getQuarter());
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 更新update quarter
	 * @return
	 */
	public String updateBrand(){
		Response response = new Response();
		
		try {
			response = basicDataService.updateBrand(formBean.getBrand());
		} catch (Exception e) {
			response.setFail(e.getMessage());
		}
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 更新update quarter
	 * @return
	 */
	public String updateCategory(){
		Response response = new Response(Response.SUCCESS);
		try {
		     response = basicDataService.updateCategory(formBean.getCategory());
		} catch (Exception e) {
			 response.setQuickValue(Response.FAIL, e.getMessage());
		}
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 更新update quarter
	 * @return
	 */
	public String updateProductUnit(){
		Response response = basicDataService.updateProductUnit(formBean.getProductUnit());
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * 更新update quarter
	 * @return
	 */
	public String updateNumPerHand(){
		Response response = basicDataService.updateNumPerHand(formBean.getNumPerHand());
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return SUCCESS;
	}
	
	/**
	 * to synchize color information from jinsuan
	 * @return
	 */
	public String synchnizeColor(){
		loggerLocal.info("BasicDataAction - saveUpdateColor");
		
		Response response = new Response(); 
		try {
		   response = basicDataService.synchnizeColor();
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, "同步颜色失败 : " + e.getMessage());
		}
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return  SUCCESS;		
	}
	
	/**
	 * save the size information
	 * @return
	 */
	public String synchnizeSize(){
		loggerLocal.info("BasicDataAction - saveUpdateSize");
		
		Response response = new Response(); 
		try {
			response = basicDataService.synchnizeSize();
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, "同步尺码失败 : " + e.getMessage());
		}
		
		jsonMap.put("response", response);
	    jsonObject = JSONObject.fromObject(jsonMap);
		
		return  SUCCESS;	
	}	
}
