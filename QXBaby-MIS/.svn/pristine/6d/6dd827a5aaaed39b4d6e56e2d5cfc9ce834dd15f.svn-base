package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainSalesStatisReportItemLevelTwo extends
		ChainSalesStatisReportItemLevelOne {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Quarter quarter = new Quarter();
	
	public ChainSalesStatisReportItemLevelTwo(){
		
	}
	
	public ChainSalesStatisReportItemLevelTwo(ChainStore chainStore, ChainUserInfor saler, Date rptStartDate, Date rptEndDate, Year year, Quarter quarter){
		super(chainStore, saler,rptStartDate, rptEndDate, year);
		this.quarter = quarter;
	}
	
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	
	public void putValue(int quantity, int type, double sales, double cost, double salesDiscount, Year year, Quarter quarter, ChainStore chainStore, ChainUserInfor saler){
		putValue(quantity, type, sales, cost, salesDiscount, year, chainStore, saler);
		this.quarter = quarter;
	}
	
}
