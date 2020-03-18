package com.onlineMIS.ORM.DAO.chainS.sales;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.action.chainS.errorProduct.ChainErrorProductActionFormBean;
import com.onlineMIS.action.chainS.errorProduct.ChainErrorProductActionUIBean;
import com.onlineMIS.action.chainS.report.ChainReportActionFormBean;
import com.onlineMIS.action.chainS.report.ChainReportActionUIBean;

@Service
public class ChainErrorProductService {
	@Autowired
	private ChainUserInforDaoImpl chainUserInforDaoImpl;

	@Autowired
	private ChainStoreService chainStoreService;

	@Autowired
	private ChainErrorProductDaoImpl chainErrorProductDaoImpl;

	public void prepareListErrorProductUI(ChainErrorProductActionFormBean formBean,
			ChainErrorProductActionUIBean uiBean, ChainUserInfor userInfor) {
		//1. 检查chain store
		checkChainStoreUsers(formBean, uiBean, userInfor);
		
	}
	
	private void checkChainStoreUsers(ChainErrorProductActionFormBean formBean, ChainErrorProductActionUIBean uiBean,
			ChainUserInfor userInfor){
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
	}



}
