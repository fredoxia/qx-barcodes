package com.onlineMIS.action.chainS.sales;

import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;

public class PurchaseActionFormBean extends ChainActionFormBaseBean{
	/**
	 * the two variables for the search start time and end time
	 */
	private Date search_Start_Time = new Date(new java.util.Date().getTime());
    private Date search_End_Time = new Date(new java.util.Date().getTime());
    private int productId;
    private ChainStore chainStore;
    private InventoryOrder order = new InventoryOrder();
    private Pager pager = new Pager();
    
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public InventoryOrder getOrder() {
		return order;
	}
	public void setOrder(InventoryOrder order) {
		this.order = order;
	}
	public Date getSearch_Start_Time() {
		return search_Start_Time;
	}
	public void setSearch_Start_Time(Date search_Start_Time) {
		this.search_Start_Time = search_Start_Time;
	}
	public Date getSearch_End_Time() {
		return search_End_Time;
	}
	public void setSearch_End_Time(Date search_End_Time) {
		this.search_End_Time = search_End_Time;
	}
	@Override
	public String toString() {
		return "PurchaseActionFormBean [search_Start_Time=" + search_Start_Time
				+ ", search_End_Time=" + search_End_Time + ", pager=" + pager
				+ "]";
	}
	
	
}
