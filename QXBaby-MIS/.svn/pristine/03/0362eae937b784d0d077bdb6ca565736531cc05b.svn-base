package com.onlineMIS.action.chainS.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

public class ChainSalesJSONAction extends ChainSalesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 775133978858252166L;

	private JSONObject jsonObject;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * when user scan the product by barcode, this will be triggered
	 * @return
	 */
	public String scanByBarcode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"scanByBarcode : " + formBean.getBarcode());
    	
		String barcode = formBean.getBarcode();
		int index = formBean.getIndex();
    	
		ChainStoreSalesOrderProduct chainProduct = chainStoreSalesService.scanProductsByBarcode(barcode, formBean.getChainId());
		
		double discount = 1;
		double discountFM = formBean.getDiscount();
		if (discountFM > 0)
			discount = discountFM;
		
		if (chainProduct == null)
			jsonMap.put("error", true);
		else{
			jsonMap.put("error", false);
	        jsonMap.put("productBarcode", chainProduct.getProductBarcode());	
	        jsonMap.put("inventory", chainProduct.getInventoryLevel());
		}

		jsonMap.put("discount", discount);
		jsonMap.put("index", index);
		jsonMap.put("suffix", formBean.getSuffix());
		
		try{
		   jsonObject = JSONObject.fromObject(jsonMap);
//		   System.out.println(jsonObject.toString());
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"scanByBarcode");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * triggered by user searching the sales orders
	 * @return
	 */
	public String searchSalesOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchSalesOrder : " + formBean);
    	
    	Response response = new Response();
    	
    	try {
		     response = chainStoreSalesService.searchSalesOrders(formBean);
    	} catch (Exception e) {
			 response.setQuickValue(Response.FAIL, e.getMessage());
			 loggerLocal.error(e);
		}
		
		JsonConfig jsonConfig = new JsonConfig();
    	jsonMap.put("response", response);
		jsonMap.put("pager", formBean.getPager());
		
		//to excludes the set and list inforamtion
		jsonConfig.setExcludes( new String[]{"productSet","productListR","productList","myChainStore","issueChainStore","roleType","chainUserFunctions"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  

		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			  // System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchSalesOrder");
				loggerLocal.error(e);
			}
			
		return SUCCESS;		
	}
	
	/**
	 * trigger by using changing the chain store
	 * 1. change the user
	 * 2. change the chainStore conf
	 * @return
	 */
	public String changeChainStore(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"changeChainStore");
    	
		int chainStoreId = formBean.getChainSalesOrder().getChainStore().getChain_id();
		List<Object> uiList = chainStoreSalesService.changeChainStore(chainStoreId);
		
		//get the user list
		List<ChainUserInfor> users = (List<ChainUserInfor>)uiList.get(0);
		ChainStoreConf chainStoreConf = (ChainStoreConf)uiList.get(1);

	    jsonMap.put("chainUsers", users);
	    jsonMap.put("chainStoreConf", chainStoreConf);
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"myChainStore","roleType", "chainUserFunctions"} );
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"changeChainStore");
				loggerLocal.error(e);
			}
			
		return SUCCESS;	
	}
	
	/**
	 * triggered by click the save the sales exchange order to draft button
	 * sales order
	 * @return
	 */
	public String saveSalesToDraft(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveSalesToDraft : " + formBean.getChainSalesOrder().getId());
    	
		//set the data
		ChainStoreSalesOrder salesOrder = formBean.getChainSalesOrder();
		Response response = new Response();
		
		try {
		    response = chainStoreSalesService.saveSaleOrders(salesOrder,userInfor,ChainStoreSalesOrder.SALES, ChainStoreSalesOrder.STATUS_DRAFT);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, e.getMessage());
		}
		
		int salerId = salesOrder.getSaler().getUser_id();
	    jsonMap.put("response", response);
	    jsonMap.put("salerId", salerId);
	    
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			   //System.out.println(jsonObject.toString());
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveSalesToDraft");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * triggered by click the post the sales exchange order "单据过账"
	 * sales order
	 * @return
	 */
	public String postSalesOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"postSalesOrder : " + formBean.getChainSalesOrder().getChainStore().getChain_id() + "," + formBean.getChainSalesOrder().getSaler().getUser_name());
    	
		//set the data
		ChainStoreSalesOrder salesOrder = formBean.getChainSalesOrder();
		
		Response response = new Response();
		
		try {		
		     response = chainStoreSalesService.saveSaleOrders(salesOrder, userInfor, ChainStoreSalesOrder.SALES, ChainStoreSalesOrder.STATUS_COMPLETE);
		} catch (Exception e) {
			e.printStackTrace();
			response.setQuickValue(Response.FAIL, e.getMessage());
		}
		
		int salerId = salesOrder.getSaler().getUser_id();
	    jsonMap.put("response", response);
	    jsonMap.put("salerId", salerId);
	    
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"postSalesOrder");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 搜索product inforamtion需要验证是否具有
	 * response: 1 : 没有权限
	 *        2  ：  
	 * @return
	 */
	public String checkProductInfor(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		Object isBoss = ActionContext.getContext().getSession().get(Common_util.IS_CHAIN_BOSS);
    	
		Response response = new Response();
		
		ProductBarcode productInfor =  chainStoreSalesService.getProductInfo(formBean.getBarcode(), formBean.getChainId());
		if (productInfor == null){
			response.setQuickValue(Response.ERROR, "无法找到对应的产品信息");
		} else {
			if (chainUserInforService.isMgmtFromHQ(userInfor) || userInfor.getRoleType().isOwner() || isBoss != null){
				response.setReturnCode(Response.SUCCESS);
			} else {
				response.setQuickValue(Response.NO_AUTHORITY,"用户没有权限查看产品详细信息");
			}
			response.setReturnValue(productInfor);
		}
		
	    jsonMap.put("response", response);
	    
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"checkProductInfor");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 获取sales order
	 * @return
	 */
	public String getSalesOrderById(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		int orderId = formBean.getChainSalesOrder().getId();
		
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getSalesOrder : " + orderId);
	    
    	Response response = new Response();
    	
		
		ChainStoreSalesOrder salesOrder = chainStoreSalesService.getSalesOrderById(orderId, userInfor);

		//1. check the order type, it is sales-out, exchange or return order
		if (salesOrder != null && salesOrder.getId() != 0 ){
            response.setReturnValue(salesOrder);
		    response.setReturnCode(Response.SUCCESS);
		} else {
			response.setQuickValue(Response.FAIL, "无法获取相对应的订单");
		}
		
	    jsonMap.put("response", response);
	    
		try{
			   JsonConfig jsonConfig = new JsonConfig();
			   jsonConfig.setExcludes( new String[]{"myChainStore","roleType", "chainUserFunctions","productSet","chainSalesOrder"} );
			   jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
			   jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
				
			   jsonObject = JSONObject.fromObject(jsonMap, jsonConfig);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"checkProductInfor");
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}


}
