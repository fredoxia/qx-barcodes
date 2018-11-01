package com.onlineMIS.action.chainS.inventoryFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainMgmtService;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

/**
 * action to 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainInventoryFlowJSONAction extends ChainInventoryFlowAction{
	@Autowired
	protected ChainMgmtService chainMgmtService;
	
	private JSONObject jsonObject;
	private String message;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * to search the inventory orders
	 * @return
	 */
	public String searchInvenOrder(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchInvenOrder :" + formBean);
    	
		List<ChainInventoryFlowOrder> orders = flowOrderService.searchInvenFlowOrders(formBean, userInfor);
		
		jsonMap.put("orders", orders);
		jsonMap.put("pager", formBean.getPager());
		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"productSet","productList","myChainStore","roleType","chainUserFunctions","toChainStore"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
  
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchInvenOrder");
				loggerLocal.error(e);
			}
			
		return "successful";		
	}

	
	/**
	 * 在调货单中，改变chainstore
	 * @return
	 */
	public String changeFromChainStoreTransfer(){
	   	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"changeFromChainStoreTransfer");
    	
		Response response = new Response();
		try{
		    response = flowOrderService.getToChainStoresByFrom(formBean.getFlowOrder().getFromChainStore().getChain_id(), userInfor);
		} catch (Exception e) {
			loggerLocal.error("Error In changeFromChainStoreTransfer : " + formBean.getFlowOrder().getFromChainStore().getChain_id());
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap.put("response", response);
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter()); 
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"changeFromChainStoreTransfer");
				loggerLocal.error(e);
			}
			
		return "successful";			
	}
	
	/**
	 * 回调flow order里面的year 和 quarter
	 * @return
	 */
	public String getYearQuarterInFlowOrder(){
	   	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getYearQuarterInFlowOrder");
    	
		Response response = new Response();
		try{
		    response = flowOrderService.getYearQuarterInOrder(formBean.getFlowOrder(), null);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap.put("response", response);

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getYearQuarterInFlowOrder");
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 获取货品的库存跟踪信息
	 * @return
	 */
	public String getInventoryTraceInfor(){
	   	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getInventoryTraceInfor " + formBean.getBarcode());
    	
		Response response = new Response();
		try{
		    response = flowOrderService.getInventoryTraceInfor(formBean.getChainId(), formBean.getBarcode());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap = (Map)response.getReturnValue();

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getInventoryTraceInfor");
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
    /**
     * 查询当前连锁店某个货品的当前库存
     * @return
     */
	public String checkChainInventory(){
	   	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"checkChainInventory " + formBean.getBarcode());
    	
		Response response = new Response();
		try{
		    response = flowOrderService.getChainInventory(formBean.getBarcode());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		jsonMap = (Map)response.getReturnValue();

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getInventoryTraceInfor");
				loggerLocal.error(e);
			}
		
		return "successful";
	}
	
	/**
	 * 清空连锁店的库存
	 * @return
	 */
	public String deleteInventory(){
	   	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteInventory ");
    	
		Response response = new Response();
		try{
		    response = flowOrderService.deleteInventory(userInfor,formBean.getChainId());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, e.getMessage());
		}

		try{
			   jsonObject = JSONObject.fromObject(response);
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getInventoryTraceInfor");
				loggerLocal.error(e);
			}
		
		return "successful";		
	}
}
