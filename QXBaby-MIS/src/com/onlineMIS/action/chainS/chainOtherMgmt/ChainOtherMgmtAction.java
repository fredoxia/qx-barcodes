package com.onlineMIS.action.chainS.chainOtherMgmt;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainMgmtService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.user.NewsService;
import com.onlineMIS.action.BaseAction;

public class ChainOtherMgmtAction  extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8185537907790594227L;
	@Autowired
	protected ChainStoreService chainStoreService;
	@Autowired
	protected ChainUserInforService chainUserInforService;
	@Autowired
	protected NewsService newsService;
	
	protected ChainOtherMgmtActionFormBean formBean = new ChainOtherMgmtActionFormBean();
	protected ChainOtherMgmtActionUIBean uiBean = new ChainOtherMgmtActionUIBean();
	public ChainOtherMgmtActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainOtherMgmtActionFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainOtherMgmtActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainOtherMgmtActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	
	
}
