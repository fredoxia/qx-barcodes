package com.onlineMIS.ORM.entity.chainS.batchRpt;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainCurrentSeasonSalesAnalysisRpt {
	private Year year;
	private Quarter quarter;
	private java.sql.Date rptDate;
	private List<ChainCurrentSeasonSalesAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonSalesAnalysisItem>();
	
	/**
	 * 报表展示数据
	 */
	private java.sql.Date endDate;
	
	public ChainCurrentSeasonSalesAnalysisRpt(){
		
	}
	
	public ChainCurrentSeasonSalesAnalysisRpt(Year year, Quarter quarter, java.sql.Date rptDate, java.sql.Date endDate){
		this.setYear(year);
		this.setQuarter(quarter);
		this.setRptDate(rptDate);
		this.endDate = endDate;
	}
	
	
	public java.sql.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}

	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public java.sql.Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(java.sql.Date rptDate) {
		this.rptDate = rptDate;
	}
	public List<ChainCurrentSeasonSalesAnalysisItem> getRptItems() {
		return rptItems;
	}
	public void setRptItems(List<ChainCurrentSeasonSalesAnalysisItem> rptItems) {
		this.rptItems = rptItems;
	}
	
	
}
