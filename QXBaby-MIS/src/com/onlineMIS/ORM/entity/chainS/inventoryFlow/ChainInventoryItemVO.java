package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainInventoryItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5222664508701251567L;
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";
	
	private String id;
	private String name;

	private int inventory;
	private double wholeSales;
	private double retailSales;
	private String state;
	
	private int parentId = 0;
	private int chainId = 0;
	private int yearId = 0;
	private int quarterId = 0;
	private int brandId = 0;
	
	public ChainInventoryItemVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ChainInventoryItemVO(String name, int inventory, double wholeSales,double retailSales, String state,int parentId,int chainId,  int yearId, int quarterId, int brandId){
		this.setId(Common_util.getUUID());
		this.setChainId(chainId);
		this.setName(name);
		this.setInventory(inventory);
		this.setWholeSales(wholeSales);
		this.setState(state);
		this.setYearId(yearId);
		this.setQuarterId(quarterId);
		this.setBrandId(brandId);
		this.setRetailSales(retailSales);
		this.setParentId(parentId);
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getQuarterId() {
		return quarterId;
	}

	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	
}
