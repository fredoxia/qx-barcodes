package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainAllInOneReportItemLevelFour extends
		ChainAllInOneReportItemLevelThree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected ProductBarcode pb = new ProductBarcode();
	
	public ChainAllInOneReportItemLevelFour(){
		
	}
	
	public ChainAllInOneReportItemLevelFour(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter, Brand brand, ProductBarcode pb){
		super(chainStore, rptStartDate, rptEndDate, year, quarter, brand);
		this.pb = pb;
	}

	public ProductBarcode getPb() {
		return pb;
	}

	public void setPb(ProductBarcode pb) {
		this.pb = pb;
	}


}
