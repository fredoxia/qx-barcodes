package com.onlineMIS.action.chainS.errorProduct;

import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;
import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;


public class ChainErrorProductActionFormBean extends ChainActionFormBaseBean{
	private ChainStore chainStore = new ChainStore();

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	
	
}
