package com.onlineMIS.ORM.DAO.chainS.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceBillImpl;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceCategoryImpl;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;

import com.onlineMIS.action.headQ.finance.FinanceActionFormBean;
import com.onlineMIS.action.headQ.finance.FinanceActionUIBean;

@Service
public class FinanceChainService {
	@Autowired
	private FinanceBillImpl financeBillImpl;
	
	@Autowired
	private FinanceCategoryImpl financeCategoryImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	/**
	 * to prepare the bean when create the finance bill
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareSearchFinanceBill(FinanceActionUIBean uiBean, ChainUserInfor user){
		List<ChainStore> chainStores = chainStoreService.getChainStoreList(user);
		uiBean.setChainStores(chainStores);
		
	}

	@Transactional
	public FinanceBill getFinanceHQBillById(int id) {
		FinanceBill financeBill = financeBillImpl.get(id, true);
		
		financeBillImpl.initialize(financeBill);
		
		financeBill.putSetToList();
		
		return financeBill;
	}

	/**
	 * 准备帐户流搜索的界面
	 * @param uiBean
	 * @param userInfor
	 */
	public void prepareSearchAcctFlowUI(FinanceActionUIBean uiBean, FinanceActionFormBean formBean,
			ChainUserInfor userInfor) {
		//1. 准备页面
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} 
		
	}
}
