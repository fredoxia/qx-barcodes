package com.onlineMIS.action.chainS.chainTransfer;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferOrderService;
import com.onlineMIS.action.BaseAction;

/**
 * action of the chain inventory flow 
 * @author fredo
 *
 */
@SuppressWarnings("serial")
public class ChainTransferAction extends BaseAction{
	@Autowired
	protected ChainTransferOrderService chainTransferOrderService;
	
	protected ChainTransferFormBean formBean = new ChainTransferFormBean();
	protected ChainTransferUIBean uiBean = new ChainTransferUIBean();

	public ChainTransferFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainTransferFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainTransferUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainTransferUIBean uiBean) {
		this.uiBean = uiBean;
	}

	
}
