package com.onlineMIS.action.chainS.errorProduct;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainErrorProductService;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.action.BaseAction;

public class ChainErrorProductAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916301254174606389L;
	protected ChainErrorProductActionFormBean formBean = new ChainErrorProductActionFormBean();
	protected ChainErrorProductActionUIBean uiBean = new ChainErrorProductActionUIBean();
	public ChainErrorProductActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainErrorProductActionFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainErrorProductActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainErrorProductActionUIBean uiBean) {
		this.uiBean = uiBean;
	}

	@Autowired
	protected ChainErrorProductService chainErrorProductService;
	
	@Autowired
	protected ChainUserInforService chainUserInforService;

	@Autowired
	protected ChainStoreService chainStoreService;

}
