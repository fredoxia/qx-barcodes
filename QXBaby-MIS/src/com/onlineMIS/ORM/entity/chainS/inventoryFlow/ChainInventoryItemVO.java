package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.report.ChainReportItemVO;
import com.onlineMIS.common.Common_util;

public class ChainInventoryItemVO extends ChainReportItemVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5222664508701251567L;

	private int inventory;
	private double wholeSales;
	private double retailSales;
	
	
	public ChainInventoryItemVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ChainInventoryItemVO(String name, int inventory, double wholeSales,double retailSales, String state,int parentId,int chainId,  int yearId, int quarterId, int brandId, int pbId, boolean seeCost){
		super(name, parentId,chainId, yearId, quarterId, brandId, pbId, state);
		this.setInventory(inventory);
		this.setWholeSales(wholeSales);
		this.setRetailSales(retailSales);
		this.setSeeCost(seeCost);
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public double getWholeSales() {
		return wholeSales;
	}

	public void setWholeSales(double wholeSales) {
		this.wholeSales = wholeSales;
	}

	public double getRetailSales() {
		return retailSales;
	}

	public void setRetailSales(double retailSales) {
		this.retailSales = retailSales;
	}

}
