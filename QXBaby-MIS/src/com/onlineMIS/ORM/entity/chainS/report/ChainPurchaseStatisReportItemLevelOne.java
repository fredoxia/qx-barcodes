package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;

public class ChainPurchaseStatisReportItemLevelOne extends
		ChainPurchaseStatisReportItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Year year = new Year();
	
	public ChainPurchaseStatisReportItemLevelOne(){
		
	}
	
	public ChainPurchaseStatisReportItemLevelOne(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year){
		super(chainStore, rptStartDate, rptEndDate);
		this.year = year;
	}
	
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	
	public void putValue(ChainStore chainStore, Year year, int orderType,  int quantity, double purchaseAmt){
	    this.chainStore = chainStore;
	    this.year = year;
	    
        putValue(orderType, quantity, purchaseAmt);
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
