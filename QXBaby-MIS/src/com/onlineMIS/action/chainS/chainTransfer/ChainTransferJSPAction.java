package com.onlineMIS.action.chainS.chainTransfer;

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
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferOrderService;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferLog;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;


/**
 * action to 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainTransferJSPAction extends ChainTransferAction{

	@Autowired
	private ChainTransferOrderService chainTransferOrderService;
	
	/**
	 * 进入 创建调货单页面
	 * @return
	 */
	public String createTransferOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"createTransferOrder");
    	
    	chainTransferOrderService.prepareCreateTransferOrderPage(userInfor, formBean, uiBean);

		return "createTransferOrder";
	}
	
	/**
	 * 点击了选择 fromChainStore
	 * @return
	 */
	public String listFromChainStore(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"listFromChainStore");
    	
		Response response = new Response();
		
		try {

		     response = chainTransferOrderService.getFromChainStoreList(userInfor, formBean.getPager());
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"listChainStore");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setFromChainStores(chainStores);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listFromChainStores";
	}
	
	/**
	 * 点击了选择 toChainStore
	 * @return
	 */
	public String listToChainStore(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"listToChainStore");
    	
		Response response = new Response();
		
		try {

		     response = chainTransferOrderService.getToChainStoreList(userInfor, formBean.getTransferOrder().getFromChainStore().getChain_id(), formBean.getPager());
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"listToChainStore");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setToChainStores(chainStores);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listToChainStores";
	}
	
	/**
	 * 进入order edit 界面
	 * @return
	 */
	public String editOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"editOrder");
		
		Response response = new Response();
		
		try {
		   response = chainTransferOrderService.editTransferOrder(formBean, userInfor);
		} catch (Exception e ){
			response.setFail(e.getMessage());
		}
		
		if (!response.isSuccess()){
			addActionError("准备修改调货单失败 : " + response.getMessage());
			return ERROR;
		} else 
			return "createTransferOrder";
	}
	
	/**
	 * 点击了Order超链接，加载Order出来
	 * @return
	 */
	public String loadTransferOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"loadTransferOrder");
		
		Response response = new Response();
		
		try {
		     response = chainTransferOrderService.loadTransferOrder(userInfor, formBean.getTransferOrder().getId(), formBean);
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"listToChainStore");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.isSuccess()){
			Map<String, Object> resultMap = (Map)response.getReturnValue();
			uiBean.setOrder((ChainTransferOrder)resultMap.get("order"));
			uiBean.setOrderLogs((List<ChainTransferLog>)resultMap.get("logs"));
		} else 
			addActionError(response.getMessage());
		
		return "displayTransferOrder";
	}

	/**
	 * 准备查询界面，查找transfer order
	 * @return
	 */
	public String preSearchTransferOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchTransferOrder");
		
		chainTransferOrderService.preSearchTransferOrder(userInfor, formBean, uiBean);

		return "searchTransferOrder";
	}
	
	
	/**
	 * 获取transfer acct flow的查询页面
	 * @return
	 */
	public String preSearchTransferAcctFlow(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchTransferAcctFlow");
		
		chainTransferOrderService.preSearchTransferAcctFlow(userInfor, formBean, uiBean);

		return "searchTransferAcctFlow";
	}
}
