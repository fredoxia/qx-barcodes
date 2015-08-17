package com.onlineMIS.action.chainS.sales;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.action.BaseAction;

public class ChainSalesAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916301254174606389L;
	protected ChainSalesActionFormBean formBean = new ChainSalesActionFormBean();
	protected ChainSalesActionUIBean uiBean = new ChainSalesActionUIBean();
	public ChainSalesActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(ChainSalesActionFormBean formBean) {
		this.formBean = formBean;
	}
	public ChainSalesActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(ChainSalesActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
//	
//	@Autowired
//	protected ProductBarcodeService productService;
//	
//	protected 
	
	@Autowired
	protected ChainStoreSalesService chainStoreSalesService;
	
	@Autowired
	protected ChainUserInforService chainUserInforService;
	
	@Autowired
	protected ChainInventoryFlowOrderService chainInventoryService;
	@Autowired
	protected ChainStoreService chainStoreService;

}
