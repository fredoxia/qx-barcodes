package com.onlineMIS.action.headQ4Chain.barcodeGentor;

import com.onlineMIS.action.BaseAction;

public class BarcodeGenAction extends BaseAction {
	protected BarcodeGenActionFormBean formBean = new BarcodeGenActionFormBean();
	protected BarcodeGenActionUIBean uiBean = new BarcodeGenActionUIBean();
	
	public BarcodeGenActionFormBean getFormBean() {
		return formBean;
	}
	public void setFormBean(BarcodeGenActionFormBean formBean) {
		this.formBean = formBean;
	}
	public BarcodeGenActionUIBean getUiBean() {
		return uiBean;
	}
	public void setUiBean(BarcodeGenActionUIBean uiBean) {
		this.uiBean = uiBean;
	}
	
}
