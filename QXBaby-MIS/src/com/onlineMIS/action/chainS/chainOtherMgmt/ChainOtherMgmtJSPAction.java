package com.onlineMIS.action.chainS.chainOtherMgmt;

import java.util.List;

import net.sf.json.JSONObject;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;


public class ChainOtherMgmtJSPAction extends ChainOtherMgmtAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1796697393416073505L;

	/**
	 * go to the 
	 * @return
	 */
	public String preEditNews(){
		
		return "newsEditPage";
	}
}
