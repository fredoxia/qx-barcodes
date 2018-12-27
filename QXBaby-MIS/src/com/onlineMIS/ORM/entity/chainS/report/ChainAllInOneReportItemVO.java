package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItemVO;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;

public class ChainAllInOneReportItemVO extends ChainReportItemVO {

	
	private static final long serialVersionUID = 1L;
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";

	
	protected Date startDate = new Date();
	protected Date endDate = new Date();
	protected int purchaseQ = 0;
	protected int purchaseR = 0;
	protected int salesQ = 0;
	protected int salesR = 0;
	protected int salesF = 0;
	protected int currentInventory = 0;
	
	public ChainAllInOneReportItemVO(){
		
	}	
	
	public ChainAllInOneReportItemVO(String name, int parentId,int chainId, int yearId, int quarterId, int brandId, int pbId, String state,ChainSalesStatisticReportItemVO sales, ChainPurchaseStatisticReportItemVO purchase, ChainInventoryItemVO inventory){
		super(name, parentId, chainId, yearId, quarterId, brandId,pbId, state);

		if (sales != null){
			this.setSalesQ(sales.getSalesQ());
			this.setSalesR(sales.getReturnQ());
			this.setSalesF(sales.getFreeQ());
		}
		
		if (purchase != null){
			this.setPurchaseQ(purchase.getPurchaseQuantity());
			this.setPurchaseR(purchase.getReturnQuantity());
		}
		
		if (inventory != null){
			this.setCurrentInventory(inventory.getInventory());
		}
	}
	
	public int getSalesF() {
		return salesF;
	}

	public void setSalesF(int salesF) {
		this.salesF = salesF;
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


	public int getPurchaseQ() {
		return purchaseQ;
	}

	public void setPurchaseQ(int purchaseQ) {
		this.purchaseQ = purchaseQ;
	}

	public int getPurchaseR() {
		return purchaseR;
	}

	public void setPurchaseR(int purchaseR) {
		this.purchaseR = purchaseR;
	}

	public int getSalesQ() {
		return salesQ;
	}

	public void setSalesQ(int salesQ) {
		this.salesQ = salesQ;
	}

	public int getSalesR() {
		return salesR;
	}

	public void setSalesR(int salesR) {
		this.salesR = salesR;
	}

	public int getCurrentInventory() {
		return currentInventory;
	}

	public void setCurrentInventory(int currentInventory) {
		this.currentInventory = currentInventory;
	}

	public void add(ChainAllInOneReportItemVO item){
		this.purchaseQ += item.getPurchaseQ();
		this.purchaseR += item.getPurchaseR();
		this.salesQ += item.getSalesQ();
		this.salesR += item.getSalesR();
		this.salesF += item.getSalesF();
		this.currentInventory += item.getCurrentInventory();
	}
	
	public void putSales(int salesType, int saleQ){
		if (salesType == ChainStoreSalesOrderProduct.SALES_OUT)
			this.salesQ += saleQ;
		else if (salesType == ChainStoreSalesOrderProduct.RETURN_BACK)
			this.salesR += saleQ;
		else 
			this.salesF += salesF;
	}
	
	public void putPurchase(int purchaseType, int quantity){
		if (purchaseType == InventoryOrder.TYPE_SALES_ORDER_W)
			this.purchaseQ += quantity;
		else 
			this.purchaseR += quantity;
	}
	
	public void putInventory(int quantity){
		this.currentInventory = quantity;
	}
}
