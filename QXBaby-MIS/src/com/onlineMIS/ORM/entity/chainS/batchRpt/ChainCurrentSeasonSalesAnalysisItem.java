package com.onlineMIS.ORM.entity.chainS.batchRpt;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;


public class ChainCurrentSeasonSalesAnalysisItem {
	private int rank;
	private ChainStore chainStore;
	private double lastYearPurchase;
	private double purchaseAmt;
	private double returnAmt;
	private double netPurchaseAmt;
	private double inDeliveryAmt;
	private double returnRatio;
	private double inventoryAmt;
	private double inventoryRatio;
	private double salesAmt;
	private double salesRatio;
	
	public ChainCurrentSeasonSalesAnalysisItem(){
		
	}
	
	public double getNetPurchaseAmt() {
		return netPurchaseAmt;
	}

	public void setNetPurchaseAmt(double netPurchaseAmt) {
		this.netPurchaseAmt = netPurchaseAmt;
	}

	public double getReturnAmt() {
		return returnAmt;
	}

	public void setReturnAmt(double returnAmt) {
		this.returnAmt = returnAmt;
	}

	public double getInDeliveryAmt() {
		return inDeliveryAmt;
	}

	public void setInDeliveryAmt(double inDeliveryAmt) {
		this.inDeliveryAmt = inDeliveryAmt;
	}

	public ChainCurrentSeasonSalesAnalysisItem(ChainStore store){
		this.chainStore = store;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public double getLastYearPurchase() {
		return lastYearPurchase;
	}
	public void setLastYearPurchase(double lastYearPurchase) {
		this.lastYearPurchase = lastYearPurchase;
	}
	public double getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(double purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	public double getReturnRatio() {
		return returnRatio;
	}
	public void setReturnRatio(double returnRatio) {
		this.returnRatio = returnRatio;
	}
	public double getInventoryAmt() {
		return inventoryAmt;
	}
	public void setInventoryAmt(double inventoryAmt) {
		this.inventoryAmt = inventoryAmt;
	}
	public double getInventoryRatio() {
		return inventoryRatio;
	}
	public void setInventoryRatio(double inventoryRatio) {
		this.inventoryRatio = inventoryRatio;
	}
	public double getSalesAmt() {
		return salesAmt;
	}
	public void setSalesAmt(double salesAmt) {
		this.salesAmt = salesAmt;
	}
	public double getSalesRatio() {
		return salesRatio;
	}
	public void setSalesRatio(double salesRatio) {
		this.salesRatio = salesRatio;
	}

	public void calculateRatio() {
		netPurchaseAmt = purchaseAmt - returnAmt;
		
		if (purchaseAmt == 0) {
			returnRatio = Common_util.ALL_RECORD;
		} else {
			returnRatio = returnAmt / purchaseAmt;
		}
		
		if (netPurchaseAmt == 0) {
			salesRatio = Common_util.ALL_RECORD;
			inventoryRatio = Common_util.ALL_RECORD;
		} else {
			salesRatio = salesAmt/ netPurchaseAmt;
			inventoryRatio = inventoryAmt / netPurchaseAmt;
		}	
	}

	
}
