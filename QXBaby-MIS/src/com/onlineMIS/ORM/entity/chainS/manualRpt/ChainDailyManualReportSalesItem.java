package com.onlineMIS.ORM.entity.chainS.manualRpt;

import java.io.Serializable;

public class ChainDailyManualReportSalesItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4078528645872102016L;
	private int rptSalesItemId;
	private ChainDailyManualRpt chainDailyManualRpt;
	private int rowIndex;
	private String brand;
	private String product;
	private double salesQ;
	private double inventoryQ;
	
	public int getRptSalesItemId() {
		return rptSalesItemId;
	}
	public void setRptSalesItemId(int rptSalesItemId) {
		this.rptSalesItemId = rptSalesItemId;
	}
	public ChainDailyManualRpt getChainDailyManualRpt() {
		return chainDailyManualRpt;
	}
	public void setChainDailyManualRpt(ChainDailyManualRpt chainDailyManualRpt) {
		this.chainDailyManualRpt = chainDailyManualRpt;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getSalesQ() {
		return salesQ;
	}
	public void setSalesQ(double salesQ) {
		this.salesQ = salesQ;
	}
	public double getInventoryQ() {
		return inventoryQ;
	}
	public void setInventoryQ(double inventoryQ) {
		this.inventoryQ = inventoryQ;
	}
	
	
}
