package com.onlineMIS.action.chainS.manualRpt;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.manualRpt.ChainDailyManualRptService;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainDailyManualRptJSPAction extends ChainDailyManualRptAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3914524847499813266L;
	
	@Autowired
	private ChainDailyManualRptService chainDailyManualRptService;
	

	
	/**
	 * 准备 创建manual report的UI
	 * @return
	 */
	public String preCreateManualRpt(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		chainDailyManualRptService.preparePreCreateManualRptUI(formBean, uiBean, userInfor);
		
		return "preCreateManualRpt";
	}
	
	/**
	 * 开始创建manual report
	 * @return
	 */
	public String createNewManualRpt(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = chainDailyManualRptService.validateCreateManualRpt(formBean.getChainStore(), formBean.getRptDate(), userInfor);
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			return preCreateManualRpt();
		} else {
			//1. 通过validation之后，准备UI数据
			
			chainDailyManualRptService.prepareCreateRptFormBean(formBean);
	
			return "createManualRpt";
		}
	}
	

}
