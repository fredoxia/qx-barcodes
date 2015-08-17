package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainLevelOneInventoryItem extends ChainInventoryItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9014918636350546095L;
	private Year year;


	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}

	@Override
	public String toString() {
		
		return "ChainLevelOneInventoryItem [year=" + year.getYear()  + ", TotalCostAmt()=" + getTotalCostAmt()
				+ ", TotalQuantity()=" + getTotalQuantity() + "]";
	}
	
	public int getKey(){
		return year.getYear_ID();
	}


}
