package com.onlineMIS.ORM.DAO.chainS;

import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;

/**
 * this class is to do the common server function
 * @author fredo
 *
 */
public class ChainUtility {
	/**
	 * to calculate the parameters
	 * @param formBean
	 * @param order
	 */
	public static void calculateParam(ChainActionFormBaseBean formBean,
			BaseOrder order) {
		int status = order.getStatus();
		//1. canSaveDraft;
		if (status == 0 || status == ChainStoreSalesOrder.STATUS_DRAFT)
			formBean.setCanSaveDraft(true);
		
		//2. canPost
		if (status == 0 || status == ChainStoreSalesOrder.STATUS_DRAFT)
			formBean.setCanPost(true);
		
		//3. canDelete
		if (status == ChainStoreSalesOrder.STATUS_DRAFT)
			formBean.setCanDelete(true);
		
		//4. can cancel
		if (status == ChainStoreSalesOrder.STATUS_COMPLETE)
			formBean.setCanCancel(true);

        //5. can edit
		if (status == ChainStoreSalesOrder.STATUS_DRAFT)
	        formBean.setCanEdit(true);
		
		//6. can copy
		if (status == ChainStoreSalesOrder.STATUS_CANCEL)
	        formBean.setCanCopy(true);
	}
}
