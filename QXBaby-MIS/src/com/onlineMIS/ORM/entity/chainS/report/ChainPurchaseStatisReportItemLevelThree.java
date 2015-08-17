package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainPurchaseStatisReportItemLevelThree extends ChainPurchaseStatisReportItemLevelTwo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	
	protected Brand brand = new Brand();
	
	public ChainPurchaseStatisReportItemLevelThree(){
		
	}
	
	public ChainPurchaseStatisReportItemLevelThree(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter,Brand brand){
		super(chainStore, rptStartDate, rptEndDate, year, quarter);
		this.brand = brand;
	}
	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public void putValue(ChainStore chainStore, Year year, Quarter quarter, Brand brand, int orderType,  int quantity, double purchaseAmt){
		putValue(chainStore, year, quarter, orderType, quantity, purchaseAmt);
		this.brand = brand;
	}
	
}
