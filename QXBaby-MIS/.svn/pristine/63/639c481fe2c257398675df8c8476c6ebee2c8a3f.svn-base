package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;

public class ChainHotBrand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5381805583237464139L;
	protected Date reportDate;
	protected ChainStore chainStore;
	protected Brand brand;
	protected int rank;
	protected double salesQuantity;
	protected double mySalesQuantity;
	protected java.util.Date createDate;
	
	public ChainHotBrand(){
		
	}

	public ChainHotBrand(Date reportDate, ChainStore chainStore,
			Brand brand, int rank, double salesQuantity) {
		super();
		this.reportDate = reportDate;
		this.chainStore = chainStore;
		this.brand = brand;
		this.rank = rank;
		this.salesQuantity = salesQuantity;
		this.createDate = new java.util.Date();
	}
	
	public double getMySalesQuantity() {
		return mySalesQuantity;
	}

	public void setMySalesQuantity(double mySalesQuantity) {
		this.mySalesQuantity = mySalesQuantity;
	}

	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getSalesQuantity() {
		return salesQuantity;
	}
	public void setSalesQuantity(double salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
}
