package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.Date;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.common.Common_util;

public class ChainPurchaseStatisticReportItemVO  extends ChainReportItemVO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4430014455314999157L;

	protected Date startDate = new Date();
	protected Date endDate = new Date();


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


	public ChainPurchaseStatisticReportItemVO(){
		
	}
	
	public ChainPurchaseStatisticReportItemVO(String name, int parentId, int chainId, int yearId, int quarterId, int brandId, int pbId, boolean seeCost, String state){
		super(name, parentId, chainId, yearId, quarterId, brandId, pbId, state);
		this.setSeeCost(seeCost);
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

	public void add(ChainPurchaseStatisticReportItemVO item){
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
	
	public void putValue(int orderType,  int quantity, double purchaseAmt){
	    switch (orderType) {
		   case InventoryOrder.TYPE_SALES_ORDER_W:
			   this.purchaseQuantity = quantity;
			   this.purchaseTotalAmt += purchaseAmt;
			   break;
		   case InventoryOrder.TYPE_SALES_RETURN_ORDER_W:
			   this.returnQuantity = quantity;
			   this.purchaseTotalAmt -= purchaseAmt;
			   break;
		   default:
			   break;
		}
	}
}
