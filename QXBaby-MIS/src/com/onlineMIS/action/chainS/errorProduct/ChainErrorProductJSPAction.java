package com.onlineMIS.action.chainS.errorProduct;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainErrorProductJSPAction extends ChainErrorProductAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5320369291528971495L;

	/**
	 * 
	 * @return
	 */
	public String preErrorProductList(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preErrorProductList");
    	
    	chainErrorProductService.prepareListErrorProductUI(formBean, uiBean, userInfor);
		
		return "listErrorProduct";
	}
	
}
