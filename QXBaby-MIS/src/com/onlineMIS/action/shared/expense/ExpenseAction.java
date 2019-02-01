package com.onlineMIS.action.shared.expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.DAO.shared.expense.ExpenseService;
import com.onlineMIS.action.BaseAction;


@Controller
public class ExpenseAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6147106436046927963L;
	protected ExpenseActionFormBean formBean = new ExpenseActionFormBean();
	protected ExpenseActionUIBean uiBean = new ExpenseActionUIBean();
	
	@Autowired
	protected ExpenseService expenseService;
	
	public ExpenseActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ExpenseActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	public ExpenseActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ExpenseActionFormBean formBean) {
		this.formBean = formBean;
	}



}
