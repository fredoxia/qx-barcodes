package com.onlineMIS.ORM.entity.chainS.batchRpt;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainCurrentSeasonSalesAnalysisRpt {
	private ChainStore chainStore;
	private Year year;
	private Quarter quarter;
	private java.sql.Date rptDate;
	private List<ChainCurrentSeasonSalesAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonSalesAnalysisItem>();
	
	public ChainCurrentSeasonSalesAnalysisRpt(){
		
	}
	
	public ChainCurrentSeasonSalesAnalysisRpt(ChainStore chainStore, Year year, Quarter quarter, java.sql.Date rptDate){
		this.setChainStore(chainStore);
		this.setYear(year);
		this.setQuarter(quarter);
		this.setRptDate(rptDate);
	}
	
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
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
