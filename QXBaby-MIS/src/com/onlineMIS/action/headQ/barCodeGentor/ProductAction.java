package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandPriceIncreaseService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.action.BaseAction;


@Controller
public class ProductAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1056564516599223621L;
	protected ProductActionUIBean uiBean = new ProductActionUIBean();
	protected ProductActionFormBean formBean = new ProductActionFormBean();
    
	@Autowired
	protected ProductBarcodeService productService;
	@Autowired
	protected BrandPriceIncreaseService brandPriceIncreaseService;

	protected List<String> selectedBarcodes = new ArrayList<String>();


	public ProductActionUIBean getUiBean() {
		return uiBean;
	}

	public void setUiBean(ProductActionUIBean uiBean) {
		this.uiBean = uiBean;
	}

	public ProductActionFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(ProductActionFormBean formBean) {
		this.formBean = formBean;
	}

	public List<String> getSelectedBarcodes() {
		return selectedBarcodes;
	}

	public void setSelectedBarcodes(List<String> selectedBarcodes) {
		this.selectedBarcodes = selectedBarcodes;
	}
	
}
