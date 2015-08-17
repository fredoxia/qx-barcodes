package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainLevelTwoInventoryItem extends ChainInventoryItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9014918636350546095L;
	private Year year;
	private Quarter quarter;

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
	@Override
	public String toString() {
		String quarterS = "";
		if (quarter != null)
			quarterS = quarter.getQuarter_Name();
		
		return "ChainLevelOneInventoryItem [year=" + year.getYear() + ", quarter="
				+ quarterS + ", TotalCostAmt()=" + getTotalCostAmt()
				+ ", TotalQuantity()=" + getTotalQuantity() + "]";
	}
	
	public int getKey(){
		return year.getYear_ID() * 19 + quarter.getQuarter_ID() * 199;
	}
	
	public ChainLevelTwoInventoryItem clone(){
		ChainLevelTwoInventoryItem levelOneInventoryItem = new ChainLevelTwoInventoryItem();
		levelOneInventoryItem.setChainStore(this.getChainStore());
		levelOneInventoryItem.setQuarter(this.getQuarter());
		levelOneInventoryItem.setYear(this.getYear());
		levelOneInventoryItem.setTotalCostAmt(this.getTotalCostAmt());
		levelOneInventoryItem.setTotalQuantity(this.getTotalQuantity());
		
		return levelOneInventoryItem;
	}
	
//	public ChainLevelTwoInventoryItem combine(ChainLevelTwoInventoryItem item2){
//		if (item2 != null){
//			this.setTotalQuantity(this.getTotalQuantity() + item2.getTotalQuantity());
//			this.setTotalCostAmt(this.getTotalCostAmt() + item2.getTotalCostAmt());
//		}
//	
//		return this;
//	}

}
