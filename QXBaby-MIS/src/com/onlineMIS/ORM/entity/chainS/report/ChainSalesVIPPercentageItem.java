package com.onlineMIS.ORM.entity.chainS.report;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainSalesVIPPercentageItem {
	private ChainStore chainStore;
	private int netSales;
	private int vip1NetSales;
	private double vip1NetSalesPercentage;
	private int vip2NetSales;
	private double vip2NetSalesPercentage;
	private int vip3NetSales;
	private double vip3NetSalesPercentage;
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public int getNetSales() {
		return netSales;
	}
	public void setNetSales(int netSales) {
		this.netSales = netSales;
	}
	public int getVip1NetSales() {
		return vip1NetSales;
	}
	public void setVip1NetSales(int vip1NetSales) {
		this.vip1NetSales = vip1NetSales;
	}
	public double getVip1NetSalesPercentage() {
		return vip1NetSalesPercentage;
	}
	public void setVip1NetSalesPercentage(double vip1NetSalesPercentage) {
		this.vip1NetSalesPercentage = vip1NetSalesPercentage;
	}
	public int getVip2NetSales() {
		return vip2NetSales;
	}
	public void setVip2NetSales(int vip2NetSales) {
		this.vip2NetSales = vip2NetSales;
	}
	public double getVip2NetSalesPercentage() {
		return vip2NetSalesPercentage;
	}
	public void setVip2NetSalesPercentage(double vip2NetSalesPercentages) {
		this.vip2NetSalesPercentage = vip2NetSalesPercentages;
	}
	public int getVip3NetSales() {
		return vip3NetSales;
	}
	public void setVip3NetSales(int vip3NetSales) {
		this.vip3NetSales = vip3NetSales;
	}
	public double getVip3NetSalesPercentage() {
		return vip3NetSalesPercentage;
	}
	public void setVip3NetSalesPercentage(double vip3NetSalesPercentages) {
		this.vip3NetSalesPercentage = vip3NetSalesPercentages;
	}
	
	
}