package com.onlineMIS.action.chainS.chainMgmt;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainMgmtService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.action.BaseAction;

public class ChainMgmtAction  extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8185537907790594227L;
	@Autowired
	protected ChainStoreService chainStoreService;
	@Autowired
	protected ChainUserInforService chainUserInforService;
	@Autowired
	protected ChainMgmtService chainMgmtService;
	
	protected ChainMgmtActionFormBean formBean = new ChainMgmtActionFormBean();
	protected ChainMgmtActionUIBean uiBean = new ChainMgmtActionUIBean();
	public ChainMgmtActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainMgmtActionFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainMgmtActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainMgmtActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	
	
}
