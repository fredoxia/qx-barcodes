package com.onlineMIS.action.shared.expense;


import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;


@Controller
public class ExpenseJSPAction extends ExpenseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4430740873103412286L;

	/**
	 * 连锁店创建expense 
	 * @return
	 */
	public String preCreateExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preCreateExpenseChain");
    	
    	expenseService.prepareCreateExpenseChainUI(userInfor, formBean, uiBean);
		
    	return "createExpenseChain";
	}
	
	/**
	 * 查询连锁店的消费记录
	 * @return
	 */
	public String preSearchExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchExpenseChain");
    	
    	expenseService.prepareSearchExpenseChainUI(userInfor, formBean, uiBean);
		
    	return "searchExpenseChain";
	}

	/**
	 * 查询连锁店的消费汇总报表
	 * @return
	 */
	public String preExpenseReportChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preExpenseReportChain");
    	
    	expenseService.prepareSearchExpenseChainUI(userInfor, formBean, uiBean);
		
    	return "expenseRptChain";
	}
}
