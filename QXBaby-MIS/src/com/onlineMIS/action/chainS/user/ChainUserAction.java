package com.onlineMIS.action.chainS.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.action.BaseAction;

@Service
public class ChainUserAction  extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3976875384058664108L;

	@Autowired
	protected ChainUserInforService chainUserInforService;
	
	@Autowired
	protected ChainStoreService chainStoreService;
	
	/**
	 * the UI Bean for the chain user
	 */
	protected ChainUserUIBean uiBean = new ChainUserUIBean();

    public ChainUserUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainUserUIBean uiBean) {
		this.uiBean = uiBean;
	}


	/**
	 * the form bean for the chain user action
	 */
	protected ChainUserFormBean formBean = new ChainUserFormBean();
	public ChainUserFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainUserFormBean formBean) {
		this.formBean = formBean;
	}
}
