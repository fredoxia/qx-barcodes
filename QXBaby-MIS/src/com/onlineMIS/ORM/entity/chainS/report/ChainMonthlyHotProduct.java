package com.onlineMIS.ORM.entity.chainS.report;

import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainMonthlyHotProduct extends ChainHotProduct{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6455802423997699922L;
	public ChainMonthlyHotProduct(){
		super();
	}
	
	public ChainMonthlyHotProduct(Date reportDate, ChainStore chainStore,
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
}
