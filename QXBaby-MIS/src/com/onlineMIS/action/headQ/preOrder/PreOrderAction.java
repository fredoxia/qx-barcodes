package com.onlineMIS.action.headQ.preOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.onlineMIS.ORM.DAO.headQ.preOrder.PreOrderHQService;
import com.onlineMIS.action.BaseAction;

public class PreOrderAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4442908626911931327L;
	
	@Autowired
	protected PreOrderHQService preOrderHQService;

	protected PreOrderActionFormBean formBean = new PreOrderActionFormBean();
    protected PreOrderActionUIBean uiBean = new PreOrderActionUIBean();
    
	public PreOrderActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(PreOrderActionFormBean formBean) {
		this.formBean = formBean;
	}
	public PreOrderActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(PreOrderActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
    
}
