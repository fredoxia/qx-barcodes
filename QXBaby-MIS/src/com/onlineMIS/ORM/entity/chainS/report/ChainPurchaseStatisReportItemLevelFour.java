package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainPurchaseStatisReportItemLevelFour extends ChainPurchaseStatisReportItemLevelThree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected ProductBarcode productBarcode = new ProductBarcode();
	
	public ChainPurchaseStatisReportItemLevelFour(){
		
	}
	
	public ChainPurchaseStatisReportItemLevelFour(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter,Brand brand, ProductBarcode productBarcode){
		super(chainStore,rptStartDate, rptEndDate, year, quarter, brand);
		this.productBarcode = productBarcode;
	}
	
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}

	public void putValue(ChainStore chainStore, Year year, Quarter quarter, Brand brand, ProductBarcode pb, int orderType,  int quantity, double purchaseAmt){
		putValue(chainStore, year, quarter, brand, orderType, quantity, purchaseAmt);
		this.productBarcode = pb;
	}
	
}
