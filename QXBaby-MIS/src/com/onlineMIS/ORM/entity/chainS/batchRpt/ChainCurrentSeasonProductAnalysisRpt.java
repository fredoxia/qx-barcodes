package com.onlineMIS.ORM.entity.chainS.batchRpt;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainCurrentSeasonProductAnalysisRpt {
	private ChainStore chainStore;
	private Year year;
	private Quarter quarter;
	private java.sql.Date rptDate;
	private List<ChainCurrentSeasonProductAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonProductAnalysisItem>();
	
	/**
	 * 报表展示数据
	 */
	private java.sql.Date endDate;
	
	public ChainCurrentSeasonProductAnalysisRpt(){
		
	}
	
	public ChainCurrentSeasonProductAnalysisRpt(ChainStore chainStore, Year year, Quarter quarter, java.sql.Date rptDate, java.sql.Date endDate){
		this.setChainStore(chainStore);
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
	public List<ChainCurrentSeasonProductAnalysisItem> getRptItems() {
		return rptItems;
	}
	public void setRptItems(List<ChainCurrentSeasonProductAnalysisItem> rptItems) {
		this.rptItems = rptItems;
	}
	
	
}
