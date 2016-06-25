package com.onlineMIS.action.headQ.preOrder;

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
public class PreOrderJSPAction extends PreOrderAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1271465764649626831L;

	/**
	 * 进入preorder查找界面
	 * @return
	 */
	public String preOrderSearch(){
		
		preOrderHQService.prepareSearchUI(formBean, uiBean);
		
		return "preOrderSearch";
	}
	
	public String getOrderById(){
		Response response = preOrderHQService.getOrderById(formBean.getOrder().getId());
		if (response.isSuccess()){
			CustPreOrder order = (CustPreOrder)response.getReturnValue();
			uiBean.setOrder(order);
		}
		
		return "preOrderDetail";
	}
}
