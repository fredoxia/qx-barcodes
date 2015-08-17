package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BasicDataService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.loggerLocal;

@Controller
public class BasicDataAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	protected BasicDataService basicDataService;
	@Autowired
	protected ProductBarcodeService productBarcodeService;

	protected BasicDataActionFormBean formBean = new BasicDataActionFormBean();
	protected BasicDataActionUIBean uiBean = new BasicDataActionUIBean();

	public BasicDataActionFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(BasicDataActionFormBean formBean) {
		this.formBean = formBean;
	}

	public BasicDataActionUIBean getUiBean() {
		return uiBean;
	}

	public void setUiBean(BasicDataActionUIBean uiBean) {
		this.uiBean = uiBean;
	}

}
