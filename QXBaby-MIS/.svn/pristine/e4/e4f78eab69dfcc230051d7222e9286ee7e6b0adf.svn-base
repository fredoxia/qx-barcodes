package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.Date;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainPurchaseStatisReportItem implements Serializable {
	/**
	 * 
	 */
	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;
	public static final int LEVEL_FOUR = 4;
	
	private static final long serialVersionUID = 1L;
	protected Date startDate = new Date();
	protected Date endDate = new Date();
	protected ChainStore chainStore = new ChainStore();

	/**
	 * 总发货数量
	 */
	protected int purchaseQuantity = 0;
	/**
	 * 采购退货数量
	 */
	protected int returnQuantity = 0;
	/**
	 * 净采购数量
	 */
	protected int netQuantity = 0;
	protected double avgPrice = 0;
	/**
	 * 净采购总额
	 */
	protected double purchaseTotalAmt = 0;


	public ChainPurchaseStatisReportItem(){
		
	}
	
	public ChainPurchaseStatisReportItem(ChainStore chainStore, Date rptStartDate, Date rptEndDate){
		this.chainStore = chainStore;
		this.startDate = rptStartDate;
		this.endDate = rptEndDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public double getPurchaseTotalAmt() {
		return purchaseTotalAmt;
	}

	public void setPurchaseTotalAmt(double purchaseTotalAmt) {
		this.purchaseTotalAmt = purchaseTotalAmt;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}

	public int getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public int getNetQuantity() {
		return netQuantity;
	}

	public void setNetQuantity(int netQuantity) {
		this.netQuantity = netQuantity;
	}

	public void add(ChainPurchaseStatisReportItem item){
		this.purchaseQuantity += item.getPurchaseQuantity();
		this.purchaseTotalAmt += item.getPurchaseTotalAmt();
		this.returnQuantity += item.getReturnQuantity();
		this.netQuantity += item.getNetQuantity();
	}
	
	public void reCalculate(){
		this.netQuantity = purchaseQuantity - returnQuantity;
		if (netQuantity != 0)
			this.avgPrice = this.purchaseTotalAmt / netQuantity;
	}
}
