package com.onlineMIS.action.headQ.finance;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.report.ChainReportService;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceService;
import com.onlineMIS.action.BaseAction;

public class FinanceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328444611965367761L;
	@Autowired
	protected FinanceService financeService;
	
	@Autowired
	protected ChainReportService chainReportService;
	
    protected FinanceActionFormBean formBean = new FinanceActionFormBean();
    protected FinanceActionUIBean uiBean = new FinanceActionUIBean();
	public FinanceActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(FinanceActionFormBean formBean) {
		this.formBean = formBean;
	}
	public FinanceActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(FinanceActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
    
}
