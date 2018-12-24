package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainInventoryRptItem {
	private ChainStore chainStore;
	private ProductBarcode productBarcode;
	/**
	 * 总数量
	 */
	private int totalQuantity;
	/**
	 * 总成本
	 */
	private double totalRetailSalesAmt;
	/**
	 * 总销售额
	 */
	private double totalWholeSalesAmt;
	
	public ChainInventoryRptItem(ProductBarcode pb, int totalQuantity, double totalWholeSalesAmt, double totalRetailSalesAmt){
		this.setProductBarcode(pb);
		this.setTotalQuantity(totalQuantity);
		this.setTotalWholeSalesAmt(totalWholeSalesAmt);
		this.setTotalRetailSalesAmt(totalRetailSalesAmt);
	}
	public ChainInventoryRptItem(){
		
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
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
	public double getTotalRetailSalesAmt() {
		return totalRetailSalesAmt;
	}
	public void setTotalRetailSalesAmt(double totalRetailSalesAmt) {
		this.totalRetailSalesAmt = totalRetailSalesAmt;
	}
	public double getTotalWholeSalesAmt() {
		return totalWholeSalesAmt;
	}
	public void setTotalWholeSalesAmt(double totalWholeSalesAmt) {
		this.totalWholeSalesAmt = totalWholeSalesAmt;
	}
	
}
