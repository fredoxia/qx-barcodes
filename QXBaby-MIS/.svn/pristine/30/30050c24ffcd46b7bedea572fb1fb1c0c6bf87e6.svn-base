package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainPurchaseStatisReportItemLevelTwo extends ChainPurchaseStatisReportItemLevelOne {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Quarter quarter = new Quarter();
	
	public ChainPurchaseStatisReportItemLevelTwo(){
		
	}
	
	public ChainPurchaseStatisReportItemLevelTwo(ChainStore chainStore, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter){
		super(chainStore,rptStartDate, rptEndDate, year);
		this.quarter = quarter;
	}
	
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	
	public void putValue(ChainStore chainStore, Year year, Quarter quarter, int orderType,  int quantity, double purchaseAmt){
		putValue(chainStore, year, orderType, quantity, purchaseAmt);
		this.quarter = quarter;
	}
	
}
