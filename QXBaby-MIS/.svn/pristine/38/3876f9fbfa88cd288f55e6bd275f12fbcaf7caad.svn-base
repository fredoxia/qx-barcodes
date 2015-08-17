package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainSalesStatisReportItemLevelFour extends
		ChainSalesStatisReportItemLevelThree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected ProductBarcode productBarcode = new ProductBarcode();
	
	public ChainSalesStatisReportItemLevelFour(){
		
	}
	
	public ChainSalesStatisReportItemLevelFour(ChainStore chainStore, ChainUserInfor saler, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter,Brand brand, ProductBarcode productBarcode){
		super(chainStore, saler,rptStartDate, rptEndDate, year, quarter, brand);
		this.productBarcode = productBarcode;
	}
	
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}

	public void putValue(int quantity, int type, double sales, double cost, double salesDiscount, Year year, Quarter quarter, Brand brand, ProductBarcode productBarcode, ChainStore chainStore, ChainUserInfor saler){
		putValue(quantity, type, sales, cost, salesDiscount, year, quarter, brand, chainStore, saler);
		this.productBarcode = productBarcode;
	}
	
}
