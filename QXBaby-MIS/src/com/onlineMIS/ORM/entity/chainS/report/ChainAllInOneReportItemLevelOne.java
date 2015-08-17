package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainAllInOneReportItemLevelOne extends
		ChainAllInOneReportItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Year year = new Year();
	
	public ChainAllInOneReportItemLevelOne(){
		
	}
	
	public ChainAllInOneReportItemLevelOne(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year){
		super(chainStore, rptStartDate, rptEndDate);
		this.year = year;
	}
	
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}

}
