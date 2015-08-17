package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainLevelThreeInventoryItem extends ChainInventoryItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 168484220091527625L;
	private Year year;
	private Quarter quarter;
	private Brand brand;
	
	public ChainLevelThreeInventoryItem(){
		
	}
	
	public ChainLevelThreeInventoryItem(ChainInventoryItem superItem){
		this.setChainStore(superItem.getChainStore());
		this.setTotalQuantity(superItem.getTotalQuantity());
		this.setTotalCostAmt(superItem.getTotalCostAmt());
		this.setTotalSalesAmt(superItem.getTotalSalesAmt());
	}

	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public int getKey(){
		return brand.getBrand_ID();
	}
}
