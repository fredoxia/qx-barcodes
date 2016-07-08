package com.onlineMIS.action.chainS.preOrder;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.ChainUtility;
import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBillItem;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class PreOrderChainJSPAction extends PreOrderAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1271465764649626831L;

	/**
	 * 进入preorder查找界面 (Chain)
	 * @return
	 */
	public String preOrderSearch(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		preOrderChainService.prepareSearchUI(formBean, uiBean, userInfor);
		
		return "preOrderSearch";
	}

	public String getOrderById(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = preOrderChainService.getOrderById(formBean.getOrder().getId(), userInfor);
		if (response.isSuccess()){
			CustPreOrder order = (CustPreOrder)response.getReturnValue();
			uiBean.setOrder(order);
		}
		
		return "preOrderDetail";
	}
	
	/**
	 * 总部下载单据
	 * @return
	 */
	public String downloadOrderById(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = new Response();
		try {
		    response = preOrderChainService.downloadPreOrder(formBean.getOrder().getId(), userInfor);
		} catch (Exception e){
			e.printStackTrace();
			addActionError(e.getMessage());
			return "error";
		}

		List<Object> values = (List<Object>)response.getReturnValue();
		
		formBean.setFileStream((InputStream)values.get(0)); 
		formBean.setFileName(values.get(1).toString().trim() + ".xls");
		
		return "download"; 
	}

}
