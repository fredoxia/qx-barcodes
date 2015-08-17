package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainAllInOneReportItemLevelTwo extends
		ChainAllInOneReportItemLevelOne {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Quarter quarter = new Quarter();
	
	public ChainAllInOneReportItemLevelTwo(){
		
	}
	
	public ChainAllInOneReportItemLevelTwo(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter){
		super(chainStore, rptStartDate, rptEndDate, year);
		this.quarter = quarter;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	

}
