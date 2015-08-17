package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainSalesStatisReportItemLevelThree extends
		ChainSalesStatisReportItemLevelTwo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	
	protected Brand brand = new Brand();
	
	public ChainSalesStatisReportItemLevelThree(){
		
	}
	
	public ChainSalesStatisReportItemLevelThree(ChainStore chainStore, ChainUserInfor saler, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter,Brand brand){
		super(chainStore, saler,rptStartDate, rptEndDate, year, quarter);
		this.brand = brand;
	}
	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public void putValue(int quantity, int type, double sales, double cost, double salesDiscount, Year year, Quarter quarter, Brand brand, ChainStore chainStore, ChainUserInfor saler){
		putValue(quantity, type, sales, cost, salesDiscount, year, quarter, chainStore, saler);
		this.brand = brand;
	}
	
}
