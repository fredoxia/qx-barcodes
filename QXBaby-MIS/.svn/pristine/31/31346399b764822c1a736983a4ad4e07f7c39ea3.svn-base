package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainHotProduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6455802423997699922L;
	protected Date reportDate;
	protected ChainStore chainStore;
	protected int brandId;
	protected ProductBarcode productBarcode;
	protected int rank;
	protected double salesQuantity;
	protected double mySalesQuantity;
	protected java.util.Date createDate;
	
	public ChainHotProduct(){
		
	}

	public ChainHotProduct(Date reportDate, ChainStore chainStore,
			int brandId, ProductBarcode productBarcode, int rank,
			double salesQuantity) {
		super();
		this.reportDate = reportDate;
		this.chainStore = chainStore;
		this.brandId = brandId;
		this.productBarcode = productBarcode;
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
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
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
