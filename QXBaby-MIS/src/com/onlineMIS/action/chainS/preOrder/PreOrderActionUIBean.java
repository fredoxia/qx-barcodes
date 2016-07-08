package com.onlineMIS.action.chainS.preOrder;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreorderIdentity;

public class PreOrderActionUIBean {
	private List<CustPreorderIdentity> identities = new ArrayList<CustPreorderIdentity>();
	private CustPreOrder order = new CustPreOrder();
	
	public List<CustPreorderIdentity> getIdentities() {
		return identities;
	}

	public void setIdentities(List<CustPreorderIdentity> identities) {
		this.identities = identities;
	}

	public CustPreOrder getOrder() {
		return order;
	}

	public void setOrder(CustPreOrder order) {
		this.order = order;
	}

	
	

}
