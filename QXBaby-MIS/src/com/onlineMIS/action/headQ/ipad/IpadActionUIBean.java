package com.onlineMIS.action.headQ.ipad;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;

public class IpadActionUIBean {
    protected List<InventoryOrderProductVO> orderProducts = new ArrayList<InventoryOrderProductVO>();
    protected List<InventoryOrderVO> orders = new ArrayList<InventoryOrderVO>();
    protected int totalQ = 0;
    protected int totalW = 0;		

	public List<InventoryOrderVO> getOrders() {
		return orders;
	}

	public void setOrders(List<InventoryOrderVO> orders) {
		this.orders = orders;
	}

	public List<InventoryOrderProductVO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<InventoryOrderProductVO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public int getTotalQ() {
		return totalQ;
	}

	public void setTotalQ(int totalQ) {
		this.totalQ = totalQ;
	}

	public int getTotalW() {
		return totalW;
	}

	public void setTotalW(int totalW) {
		this.totalW = totalW;
	}

}
