package com.onlineMIS.action.headQ.preOrder;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;

public class PreOrderActionFormBean extends ChainActionFormBaseBean{
	private ChainStore chainStore = new ChainStore();
    private Pager pager = new Pager(); 
    private PreOrderUIBean order = new PreOrderUIBean();
	
	public PreOrderUIBean getOrder() {
		return order;
	}
	public void setOrder(PreOrderUIBean order) {
		this.order = order;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
}
