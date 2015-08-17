package com.onlineMIS.action.chainS.inventoryFlow;

import org.springframework.beans.factory.annotation.Autowired;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.action.BaseAction;

/**
 * action of the chain inventory flow 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainInventoryFlowAction extends BaseAction{
	protected ChainInventoryFlowFormBean formBean = new ChainInventoryFlowFormBean();
	protected ChainInventoryFlowUIBean uiBean = new ChainInventoryFlowUIBean();

	public ChainInventoryFlowFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainInventoryFlowFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainInventoryFlowUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainInventoryFlowUIBean uiBean) {
		this.uiBean = uiBean;
	}
	
	@Autowired
	protected ChainInventoryFlowOrderService flowOrderService;
	
}
