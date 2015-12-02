package com.onlineMIS.action.chainS.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.chainS.finance.FinanceChainService;
import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.headQ.finance.FinanceJSPAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class FinanceChainJSPAction extends FinanceJSPAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1120066931973798941L;
	
	@Autowired
	FinanceChainService financeChainService;

	
	/**
	 * to redirect to the head q search page
	 * @return
	 */
	public String preSearchFHQ(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchFHQ");
    	
		financeChainService.prepareSearchFinanceBill(formBean, userInfor);

		return "preSearchHQFinanceBill";
	}

	
	/**
	 * to get the finance bill from the head quarter part
	 * @return
	 */
	public String getFHQ(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getFHQ : " + formBean.getFinanceBill().getId());
    	
		String destination = "";
		
		FinanceBill financeBill = financeChainService.getFinanceHQBillById(formBean.getFinanceBill().getId());
		
		int status = financeBill.getStatus();
		
		switch (status) {
		    case FinanceBill.STATUS_COMPLETE: 
		    	uiBean.setFinanceBill(financeBill); 
		    	destination = "ViewHQFinanceBill"; break;
		    default: destination = "ERROR"; break;
		}
		
		return destination;
	}
	
	/**
	 * 搜索帐户流
	 * @return
	 */
	public String preSearchAcctFlow(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchAcctFlow");
    	
		financeChainService.prepareSearchAcctFlowUI(uiBean,formBean, userInfor);

		return "preSearchAcctFlow";
	}

}
