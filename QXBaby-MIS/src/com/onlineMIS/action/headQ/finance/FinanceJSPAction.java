package com.onlineMIS.action.headQ.finance;

import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.ChainUtility;
import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBillItem;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class FinanceJSPAction extends FinanceAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1120066931973798941L;
	

	
	public String preCreateFHQ(){
		
		financeService.prepareCreateBill(formBean, uiBean);

		System.out.println("---" + formBean.getFinanceBill().getFinanceBillItemList().size());
		
		ChainUtility.calculateParam(formBean, new FinanceBill());
		
		return "EditFinanceBill";
	}
	

	

	
	/**
	 * 删除单据
	 * @return
	 */
	public String deleteFHQBill(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		FinanceBill financeBill = formBean.getFinanceBill();
		
		if (financeBill.getId() != 0){
		    formBean.getFinanceBill().setCreatorHq(loginUserInfor);
		
		    financeService.deleteFHQBill(formBean.getFinanceBill());
		}
		
		formBean.initialize();
		
		addActionMessage("成功删除单据");
		return preCreateFHQ();	
	}
	
	/**
	 * 红冲单据
	 * @return
	 */
	public String cancelFHQBill(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		FinanceBill financeBill = formBean.getFinanceBill();
		
		if (financeBill.getId() != 0){
		    formBean.getFinanceBill().setCreatorHq(loginUserInfor);
		
		    Response response = financeService.cancelFHQBill(formBean.getFinanceBill());
		    if (response.getReturnCode() != Response.SUCCESS){
		    	addActionError(response.getMessage());
				
				formBean.initialize();
				return preCreateFHQ();	
		    }
		}

		formBean.initialize();
		addActionMessage("成功红冲单据");
		return preCreateFHQ();	
	}
	
	/**
	 * to redirect to the head q search page
	 * @return
	 */
	public String preSearchFHQ(){
		financeService.prepareCreateBill(formBean, uiBean);

		return "preSearchFinanceBill";
	}

	
	/**
	 * to get the finance bill from the head quarter part
	 * @return
	 */
	public String getFHQ(){
		String destination = "";
		
		FinanceBill financeBill = financeService.getFinanceBillById(formBean.getFinanceBill().getId());
		
		int status = financeBill.getStatus();
		
		switch (status) {
		    case FinanceBill.STATUS_DRAFT: 
		    	formBean.setFinanceBill(financeBill); 
		    	financeService.prepareEditBill(formBean, financeBill.getChainStore());
		    	destination = "EditFinanceBill"; break;
		    case FinanceBill.STATUS_COMPLETE: 
		    case FinanceBill.STATUS_CANCEL:
		    	uiBean.setFinanceBill(financeBill); 
		    	destination = "ViewFinanceBill"; break;
		    default: destination = "error"; break;
		}
		
		ChainUtility.calculateParam(formBean, financeBill);
		
		return destination;
	}
	
	/**
	 * the user click the Edit bill button to try to edit the bill
	 * @return
	 */
	public String editFHQBill(){
		FinanceBill financeBill = financeService.getFinanceBillById(formBean.getFinanceBill().getId());
		
		formBean.setFinanceBill(financeBill); 
		financeService.prepareEditBill(formBean, financeBill.getChainStore());
		
		ChainUtility.calculateParam(formBean, financeBill);
		
		return "EditFinanceBill";
	}
	
	/**
	 * 搜索帐户流
	 * @return
	 */
	public String preSearchAcctFlow(){
    	UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	loggerLocal.info(userInfor.getUser_name() + "," + this.getClass().getName() + "." + "preSearchAcctFlow");
    	
		//financeService.prepareSearchAcctFlowUI(uiBean,formBean, userInfor);

		return "preSearchAcctFlow";
	}
	
	/**
	 * 搜索帐户流
	 * @return
	 */
	public String preFinanceRpt(){
    	UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	loggerLocal.info(userInfor.getUser_name() + "," + this.getClass().getName() + "." + "preFinanceRpt");
    	
		//financeService.prepareSearchAcctFlowUI(uiBean,formBean, userInfor);

		return "preFinanceRpt";
	}	

}
