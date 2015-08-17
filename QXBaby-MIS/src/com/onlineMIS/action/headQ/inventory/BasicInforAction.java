package com.onlineMIS.action.headQ.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.headQ.inventory.BasicInforService;
import com.onlineMIS.action.BaseAction;

@Controller
public class BasicInforAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4476059961918056442L;
	@Autowired
	protected BasicInforService basicInforService;
	
	public void setBasicInforService(BasicInforService basicInforService) {
		this.basicInforService = basicInforService;
	}



}
