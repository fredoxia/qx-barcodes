package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainInventoryItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8915188716068194983L;
	/**
	 * 所属连锁店
	 */
	private ChainStore chainStore;
	/**
	 * 总数量
	 */
	private int totalQuantity;
	/**
	 * 总成本
	 */
	private double totalCostAmt;
	/**
	 * 总销售额
	 */
	private double totalSalesAmt;
	
	public double getTotalCostAmt() {
		return totalCostAmt;
	}
	public void setTotalCostAmt(double totalCostAmt) {
		this.totalCostAmt = totalCostAmt;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	
	public double getTotalSalesAmt() {
		return totalSalesAmt;
	}
	public void setTotalSalesAmt(double totalSalesAmt) {
		this.totalSalesAmt = totalSalesAmt;
	}
	
	public ChainInventoryItem combine(ChainInventoryItem item2){
		if (item2 != null){
			this.setTotalQuantity(this.getTotalQuantity() + item2.getTotalQuantity());
			this.setTotalCostAmt(this.getTotalCostAmt() + item2.getTotalCostAmt());
			this.setTotalSalesAmt(this.getTotalSalesAmt() + item2.getTotalSalesAmt());
		}
		return this;
	}
	
	@Override
	public String toString() {
		return "ChainInventoryItem [chainStore=" + chainStore
				+ ", totalQuantity=" + totalQuantity + ", totalCostAmt="
				+ totalCostAmt + ", totalSalesAmt =" + totalSalesAmt +"]";
	}
	
	
}
