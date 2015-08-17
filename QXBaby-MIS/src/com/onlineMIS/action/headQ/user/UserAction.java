package com.onlineMIS.action.headQ.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.action.BaseAction;


@Controller
public class UserAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6147106436046927963L;
	protected UserActionFormBean formBean = new UserActionFormBean();
	protected UserActionUIBean uiBean = new UserActionUIBean();
	
	@Autowired
	protected UserInforService userInforService;
	
	public UserActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(UserActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	public UserActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(UserActionFormBean formBean) {
		this.formBean = formBean;
	}



}
