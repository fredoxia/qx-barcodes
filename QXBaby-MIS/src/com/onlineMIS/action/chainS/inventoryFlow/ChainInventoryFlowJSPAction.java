package com.onlineMIS.action.chainS.inventoryFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.ChainUtility;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInvenTraceInfoVO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelFourInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelOneInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelThreeInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.onlineMIS.filter.SystemParm;
import com.onlineMIS.sorter.ChainInveProductSort;
import com.opensymphony.xwork2.ActionContext;

/**
 * action to 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainInventoryFlowJSPAction extends ChainInventoryFlowAction{
	private final String CHAIN_INVENTORY_REPORT_TEMPLATENAME = "ChainInventoryReportTemplate.xls";
	private final String CHAIN_INVENTORY_REPORT_NAME = "KuCunBiao.xls";

	@Autowired
	private ChainStoreService chainStoreService;


	/**
	 * go to 报溢单 (overflow order)
	 */
	public String preCreateOverflowOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preCreateOverflowOrder");
    	
		ChainInventoryFlowOrder order = new ChainInventoryFlowOrder();
		
		flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
		
		//prepare the 
		ChainUtility.calculateParam(formBean, order);
		
		//set the order type
		formBean.getFlowOrder().setType(ChainInventoryFlowOrder.OVER_FLOW_ORDER);
		
		return "editFlowOrder";
	}
	
	/**
	 * go to 报损单 (flow loss order)
	 */
	public String preCreateflowLossOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preCreateflowLossOrder");
    	
		ChainInventoryFlowOrder order = formBean.getFlowOrder();
    	
		flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
		
		//prepare the 
		ChainUtility.calculateParam(formBean, order);
		
		//set the order type
		formBean.getFlowOrder().setType(ChainInventoryFlowOrder.FLOW_LOSS_ORDER);
		
		return "editFlowOrder";
	}
	
	/**
	 * go to create 库存单 (inventory order)
	 * @return
	 */
	public String preCreateInventoryOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preCreateInventoryOrder");
    	
		ChainInventoryFlowOrder order = formBean.getFlowOrder();
		
		flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
		
		//set the order type
		formBean.getFlowOrder().setType(ChainInventoryFlowOrder.INVENTORY_ORDER);
		
		return "editInventoryOrder";		
	}
	
	/**
	 * go to create 调货单 (inventory transfer order)
	 * @return
	 */
	public String preCreateInventoryTransferOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preCreateInventoryTransferOrder");
    	
		ChainInventoryFlowOrder order = formBean.getFlowOrder();
		
		flowOrderService.prepareCreateInvenTransferOrderFormUIBean(loginUser, uiBean, formBean, order);
		
		//prepare the 
		ChainUtility.calculateParam(formBean, order);
		
		//set the order type
		formBean.getFlowOrder().setType(ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER);
		
		return "editInventoryTransferOrder";		
	}
	
	public String preSearchInvenFlowOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preSearchInvenFlowOrder");
    	
		flowOrderService.prepareSearchFlowOrderFormUIBean(loginUser, uiBean, formBean);
		
		return "searchOrder";
	}
	
	/**
	 * to save the inventory order to draft 库存盘点单
	 * @return
	 */
	public String saveInventoryToDraft(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"saveInventoryToDraft: " + formBean.getFlowOrder().getId());
    	
		Response response = new Response();
		try {
		    response = flowOrderService.saveInventoryToDraft(formBean.getFlowOrder(), formBean.getInventory() ,loginUser);
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			return postSubmitFlowOrder(false);
		}else {
			addActionMessage(response.getMessage());
			int orerId = (Integer)response.getReturnValue();
			formBean.getFlowOrder().setId(orerId);
	        return loadOrder();
		}
	}
	
	/**
	 * action to generate the inventory report
	 * @return
	 */
	public String genInventoryReport(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"genInventoryReport");
    	
		Response response = new Response();
		try {
		    response = flowOrderService.genInventoryReport(formBean.getFlowOrder(), formBean.getInventory() ,loginUser);
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			return postSubmitFlowOrder(false);
		}else {
			addActionMessage(response.getMessage());
			int orerId = (Integer)response.getReturnValue();
			formBean.getFlowOrder().setId(orerId);
	        return loadOrder();
		}
	}

	/**
	 * 一键修复库存，只会核对单据上的物品的库存
	 * @return
	 */
	public String oneKeyAdjustInventory(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"oneKeyAdjustInventory : " + formBean.getFlowOrder().getId());
    	
		Response response = new Response();
		try {
		    response = flowOrderService.adjustInventory(formBean.getFlowOrder(),loginUser);
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, formBean.getFlowOrder());
			return "editInventoryOrder";
		}else {
			String message = response.getMessage();
			response = flowOrderService.calculateInventoryOrder(formBean.getFlowOrder());
			
			if (response.getReturnCode() != Response.SUCCESS){
				addActionError(response.getMessage());
			} else {
				addActionMessage(message);
				ChainInventoryFlowOrder flowOrder = (ChainInventoryFlowOrder) response.getReturnValue();
				flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, flowOrder);
			}

			return "editInventoryOrder";
		}
	}
	
	/**
	 * 此单据应该是连锁店的实际库存,此功能将使用此单据完全取代电脑库存
	 * @return
	 */
	public String oneKeyClearAndAdjustInventory(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"oneKeyClearAndAdjustInventory : " + formBean.getFlowOrder().getId());
    	
		Response response = new Response();
		try {
		    response = flowOrderService.clearAndadjustInventory(formBean.getFlowOrder(),loginUser);
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, formBean.getFlowOrder());
			return "editInventoryOrder";
		}else {
			String message = response.getMessage();
			response = flowOrderService.calculateInventoryOrder(formBean.getFlowOrder());
			
			if (response.getReturnCode() != Response.SUCCESS){
				addActionError(response.getMessage());
			} else {
				addActionMessage(message);
				ChainInventoryFlowOrder flowOrder = (ChainInventoryFlowOrder) response.getReturnValue();
				flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, flowOrder);
			}

			return "editInventoryOrder";
		}	
	}
	
	/**
	 * to save the 报损单，报溢单，调货单 to draft
	 * @return
	 */
	public String saveToDraft(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"saveToDraft : " + formBean.getFlowOrder().getId());
    	
		try {
		    flowOrderService.saveToDraft(formBean.getFlowOrder(),formBean.getInventory(), loginUser);
			
			addActionMessage("已经成功保存单据到草稿");
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}

        return postSubmitFlowOrder(true);
	}

	/**
	 * to save the  报损单，报溢单，调货单 to final order
	 * @return
	 */
	public String saveToFinal(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"saveToFinal");
    	
		try {
			
		    flowOrderService.saveToFinal(formBean.getFlowOrder(), loginUser);
			
			addActionMessage("已经成功保存单据");
		} catch (Exception e) {
			loggerLocal.error("ChainInventoryFlowAction.saveToDraft: " + loginUser.getUser_id());
			loggerLocal.info(formBean.getFlowOrder().toString());
			loggerLocal.error(e);
			addActionError("保存单据发生系统错误，请联系系统管理员");
		}

        return postSubmitFlowOrder(true);
	}
	
	/**
	 * to edit the inventory flow order
	 * @return

	public String editOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"editOrder");
    	
		int orderId =  formBean.getFlowOrder().getId();
		ChainInventoryFlowOrder order = flowOrderService.getOrderById(orderId, loginUser);

 	    flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
 	    
 	    int orderType = order.getType();
		if (orderType == ChainInventoryFlowOrder.INVENTORY_ORDER)
		   return "editInventoryOrder";
		else if (orderType == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER)
		   return "displayInventoryTransferOrder";
	   else  
           return "editFlowOrder";

	}	 */
	
	/**
	 * to load the inventory flow order
	 * @return
	 */
	public String loadOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		int orderId =  formBean.getFlowOrder().getId();
		
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"loadOrder : " + orderId);

		ChainInventoryFlowOrder order = flowOrderService.getOrderById(orderId, loginUser);
		
		ChainUtility.calculateParam(formBean, order);

		int orderStatus = order.getStatus();
		int orderType = order.getType();
        if (orderStatus == ChainInventoryFlowOrder.STATUS_COMPLETE || orderStatus == ChainInventoryFlowOrder.STATUS_CANCEL){
           flowOrderService.prepareDisplayFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
    	   if (orderType == ChainInventoryFlowOrder.INVENTORY_ORDER){
    		   //1. sort the flow order by year, quarter, brand, product code
    		   Collections.sort(order.getProductList(), new ChainInveProductSort());
    		   
    		   return "displayInventoryReport";
           } else if (orderType == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER)
    		   return "displayInventoryTransferOrder";
    	   else 
               return "displayFlowOrder";
        }else if (orderStatus ==  ChainInventoryFlowOrder.STATUS_DRAFT){

    	   if (orderType == ChainInventoryFlowOrder.INVENTORY_ORDER){
        	   flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
        	   
    		   return "editInventoryOrder";
    	   } else if (orderType == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
    		   flowOrderService.prepareCreateInvenTransferOrderFormUIBean(loginUser, uiBean, formBean, order);
    		   return "editInventoryTransferOrder";
    	   } else {
        	   flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
               return "editFlowOrder";
    	   }
        }else {
           addActionError("你没有访问这个单据的权限");
           return ERROR;
        }
        	
	}
	
	/**
	 * delete the inventory flow order
	 * @return
	 */
	public String deleteOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		int orderId =  formBean.getFlowOrder().getId();
		
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"deleteOrder : " + orderId);

		flowOrderService.deleteOrderById(orderId, loginUser);
		addActionMessage("已经成功删除单据");
		
		return preSearchInvenFlowOrder();
	}
	
	/**
	 * to cancel the order 红冲单据
	 * @return
	 */
	public String cancelOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		int orderId =  formBean.getFlowOrder().getId();
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"cancelOrder : " + orderId);


		Response response = flowOrderService.cancelOrder(orderId, loginUser);
		if (response.getReturnCode() == Response.SUCCESS){
		   addActionMessage(response.getMessage());
		   return preSearchInvenFlowOrder();
		} else {
		   addActionError(response.getMessage());
		   return loadOrder();
		}
	}

	/**
	 * function to copy inventory order
	 * @return
	 */
	public String copyOrder(){
		ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		ChainInventoryFlowOrder flowOrder = formBean.getFlowOrder();
		
		loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"copyOrder : " + flowOrder.getId());

		Response response = flowOrderService.copyOrder(flowOrder,loginUser);
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			return "operationError";
		} else {
			ChainInventoryFlowOrder order = (ChainInventoryFlowOrder)response.getReturnValue();
			
            int order_type = order.getType();
	
			//1. prepare the ui bean
			flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, order);
			
			//4. set the parameter
			ChainUtility.calculateParam(formBean, order);
			
			addActionMessage(response.getMessage());
			
			if (order_type == ChainInventoryFlowOrder.FLOW_LOSS_ORDER || order_type == ChainInventoryFlowOrder.OVER_FLOW_ORDER){
		        return "editFlowOrder";
			} else if (order_type == ChainInventoryFlowOrder.INVENTORY_ORDER)
				return "editInventoryOrder";
			else 
				return ERROR;
		}
	}
	
	/**
	 * action to re-calculate (重新计算) the inventory order such as quantity
	 * @return
	 */
	public String calculateInventoryOrder(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"calculateInventoryOrder : " + formBean.getFlowOrder().getId());
    	
		Response response = flowOrderService.calculateInventoryOrder(formBean.getFlowOrder());
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
		} else {
			ChainInventoryFlowOrder flowOrder = (ChainInventoryFlowOrder) response.getReturnValue();
			
			flowOrderService.prepareCreateFlowOrderFormUIBean(loginUser, uiBean, formBean, flowOrder);
		}
		
		return "editInventoryOrder";		
	}
	

	
	/**
	 * function to open the upload file ui
	 * @return
	 */
	public String preUploadFile(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preUploadFile");
    	
		return "preUpload";
	}
	
	/**
	 * action to download the barcodes
	 * @return
	 */
	public String getBarcodeFile(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"getBarcodeFile");
    	InputStream fileStream = null;
		Date date = new Date();
		formBean.setFileName("barcode.txt");
		
		File barcodeFile = new File(SystemParm.getParm("BARCODE_DOWNLOAD_PATH"));
		if (!barcodeFile.exists())
			fileStream = flowOrderService.genBarcodeFile();
		else {
			try {
				fileStream = new FileInputStream(barcodeFile);
			} catch (FileNotFoundException e) {
				loggerLocal.error(e);
				fileStream = flowOrderService.genBarcodeFile();
			}
		}
		
		formBean.setFileStream(fileStream);
		
		return "download";
	}
	
	/**
	 * 获取当前库存前，需要提前选择连锁店
	 * @return
	 */
	public String preGetCurrentInventory(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"preGetCurrentInventory");
    	
		Response response = new Response();
		
		try {
		     response = chainStoreService.getChainStoreList(loginUser, formBean.getPager(), Common_util.CHAIN_ACCESS_LEVEL_2, 1);
		} catch (Exception e) {
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setChainStores(chainStores);
			
			return "preGetCurrentInventory";
		} else if (response.getReturnCode() == response.WARNING){
			ChainStore chainStore = loginUser.getMyChainStore();
			
			formBean.setChainId(chainStore.getChain_id());
			
			return getLevelOneCurrentInventory();
		} else {
			addActionError("发生错误 : " + response.getMessage());
			return "preGetCurrentInventory";
		}

	}
	
	/**
	 * 获取level one的当前库存
	 * @return
	 */
	public String getLevelOneCurrentInventory(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"getLevelOneCurrentInventory : " + formBean);
    	
		int chainId = formBean.getChainId();
		
		Response response = new Response();
		
		try {
 		     response = flowOrderService.getLevelOneCurInventory(chainId);
		} catch (Exception e) {
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			List<Object> respObjects = (List<Object>)response.getReturnValue();
			ChainInventoryItem inventoryItem = (ChainInventoryItem)respObjects.get(0);
			List<ChainLevelOneInventoryItem> levelOneInventoryItem = (List<ChainLevelOneInventoryItem>)respObjects.get(1);
			uiBean.setInventoryItem(inventoryItem);
			uiBean.setLevelOneInventoryItem(levelOneInventoryItem);
		} else
			addActionError(response.getMessage());
		
		return "levelOneCurInventory";
	}

	/**
	 * 获取level two的当前库存
	 * @return
	 */
	public String getLevelTwoCurrentInventory(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"getLevelTwoCurrentInventory : " + formBean);
    	
		int chainId = formBean.getChainId();
		int yearId = formBean.getYearId();
		
		Response response = new Response();
		
		try {
 		     response = flowOrderService.getLevelTwoCurInventory(chainId, yearId);
		} catch (Exception e) {
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			List<Object> respObjects = (List<Object>)response.getReturnValue();
			ChainInventoryItem inventoryItem = (ChainInventoryItem)respObjects.get(0);
			List<ChainLevelTwoInventoryItem> levelTwoInventoryItems = (List<ChainLevelTwoInventoryItem>)respObjects.get(1);
			uiBean.setInventoryItem(inventoryItem);
			uiBean.setLevelTwoInventoryItem(levelTwoInventoryItems);
		} else
			addActionError(response.getMessage());
		
		return "levelTwoCurInventory";
	}
	
	/**
	 * 获取level two的当前库存
	 * @return
	 */
	public String getLevelThreeCurrentInventory(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"getLevelThreeCurrentInventory : " + formBean);
    	
		int chainId = formBean.getChainId();
		int yearId = formBean.getYearId();
		int quarterId = formBean.getQuarterId();
		
		Response response = new Response();
		
		try {
 		     response = flowOrderService.getLevelThreeCurInventory(chainId, yearId, quarterId, formBean.getPager());
		} catch (Exception e) {
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			List<Object> respObjects = (List<Object>)response.getReturnValue();
			ChainInventoryItem inventoryItem = (ChainInventoryItem)respObjects.get(0);
			List<ChainLevelThreeInventoryItem> levelTwoInventoryItems = (List<ChainLevelThreeInventoryItem>)respObjects.get(1);
			uiBean.setInventoryItem(inventoryItem);
			uiBean.setLevelThreeInventoryItem(levelTwoInventoryItems);
		} else
			addActionError(response.getMessage());
		
		return "levelThreeCurInventory";
	}
	
	/**
	 * 获取level four的当前库存
	 * @return
	 */
	public String getLevelFourCurrentInventory(){
    	ChainUserInfor loginUser = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(loginUser,this.getClass().getName()+ "."+"getLevelFourCurrentInventory : " + formBean);
    	
		int chainId = formBean.getChainId();
		int yearId = formBean.getYearId();
		int quarterId = formBean.getQuarterId();
		int brandId = formBean.getBrandId();
		
		Response response = new Response();
		
		try {
 		     response = flowOrderService.getLevelFourCurInventory(chainId, yearId, quarterId, brandId, formBean.getPager());
		} catch (Exception e) {
			loggerLocal.chainActionError(loginUser,this.getClass().getName()+ "."+"");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			List<Object> respObjects = (List<Object>)response.getReturnValue();
			ChainInventoryItem inventoryItem = (ChainInventoryItem)respObjects.get(0);
			List<ChainLevelFourInventoryItem> levelTwoInventoryItems = (List<ChainLevelFourInventoryItem>)respObjects.get(1);
			uiBean.setInventoryItem(inventoryItem);
			uiBean.setLevelFourInventoryItem(levelTwoInventoryItems);
		} else
			addActionError(response.getMessage());
		
		return "levelFourCurInventory";
	}
	
	/**
	 * 获取连锁店库存报表
	 * @return
	 */
	public String generateChainInventoryExcelReport(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"generateChainInventoryExcelReport");

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		Response response = new Response();
		try {
			response = flowOrderService.generateChainInventoryExcelReport(formBean.getReportType(), formBean.getChainId(), formBean.getYearId(), formBean.getQuarterId(), formBean.getBrandId(), loginUserInfor, contextPath + "WEB-INF\\template\\" + CHAIN_INVENTORY_REPORT_TEMPLATENAME);     
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}
 
		if (response.getReturnCode() == Response.SUCCESS){
		    InputStream excelStream= (InputStream)response.getReturnValue();
		    formBean.setFileStream(excelStream);
		    formBean.setFileName(CHAIN_INVENTORY_REPORT_NAME);
		    return "download"; 
		} else 
			return ERROR;
	}
	
	/**
	 * 察看跟踪货品库存
	 * @return
	 */
	public String checkInvenTrace(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"checkInvenTrace");

		flowOrderService.prepareCheckInvenTraceUIBean(loginUserInfor, uiBean, formBean);

		return "checkInventoryTrace";
	}
	
	/**
	 * 查看连搜点某种商品库存
	 * @return
	 */
	public String preCheckChainInven(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		
		formBean.setChainId(loginUserInfor.getMyChainStore().getChain_id());
		
		return "checkChainInven";

	}
	
	/**
	 * 客户通过点击 条码进入一个 message box打开
	 * @return
	 */
	public String openInvenTracePage(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"openInvenTracePage ： " + formBean.getBarcode());

		Response response = flowOrderService.getInventoryTraceInfor(formBean.getChainId(), formBean.getBarcode());
		if (response.getReturnCode() == Response.SUCCESS){
			Map<String, List> data = (Map<String, List>)response.getReturnValue();
			List<ChainInvenTraceInfoVO> traceVOs = data.get("rows");
			List<ChainInvenTraceInfoVO> footers = data.get("footer");
			
			if (footers != null && footers.size() >0)
				uiBean.setTraceFooter(footers.get(0));
			
			uiBean.setTraceItems(traceVOs);
		}
		return "OpenCheckInventoryTracePage";
	}
	
	public String downloadFlowOrder(){
		ChainUserInfor loginUserInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.chainActionInfo(loginUserInfor,this.getClass().getName()+ "."+"openInvenTracePage ： " + formBean.getBarcode());

		Response response = new Response();
		try {
		    response = flowOrderService.downloadFlowOrder(formBean.getFlowOrder().getId(), loginUserInfor);
		} catch (Exception e){
			e.printStackTrace();
			addActionError(e.getMessage());
			return "error";
		}

		List<Object> values = (List<Object>)response.getReturnValue();
		
		formBean.setFileStream((InputStream)values.get(0)); 
		formBean.setFileName(values.get(1).toString().trim() + formBean.getFlowOrder().getId() + ".xls");
		
		return "download"; 
	}

	
	private String postSubmitFlowOrder(boolean toFinalize){
		//clear the data
		int orderType = formBean.getFlowOrder().getType();
		
		if (toFinalize)
		    formBean.finalize();
		
		if (orderType == ChainInventoryFlowOrder.OVER_FLOW_ORDER)
		    return preCreateOverflowOrder();
		else if (orderType == ChainInventoryFlowOrder.FLOW_LOSS_ORDER)
			return preCreateflowLossOrder();
		else if (orderType == ChainInventoryFlowOrder.INVENTORY_ORDER)
			return preCreateInventoryOrder();
		else if (orderType == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER)
			return preCreateInventoryTransferOrder();
		else
			return ERROR;
	}
	
}
