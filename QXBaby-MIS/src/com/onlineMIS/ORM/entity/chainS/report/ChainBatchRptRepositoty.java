package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;

public class ChainBatchRptRepositoty implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8894145370950092740L;
	
	public static final Integer[] RPT_TYPES = {1,2};
	/**
	 * 当季货品的每周滞销货，热销货报表
	 */
	public static final int TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT = 1;
	/**
	 * 当季货品的销售报表
	 */
	public static final int TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT = 2;
	
	public static final String REPORITORY_ROOT = "D:\\QXBaby-MIS\\BatchReport";
	
	/**
	 * rpt type id
	 */
	private int rptId;
	private Date rptDate;
	private String rptName = "";
	/**
	 * rpt的描述比如 
	 * 2015-1-2 至 2015-2-1
	 */
	private String rptDes = "";
	private String rptPath = "";
	
	public String getRptDes() {
		return rptDes;
	}
	public void setRptDes(String rptDes) {
		this.rptDes = rptDes;
	}
	public void setRptDes(java.sql.Date startDate, java.sql.Date endDate, Quarter quarter){
		this.setRptDes(startDate + " 至  " + endDate + " " + quarter.getQuarter_Name());
	}
	
	public int getRptId() {
		return rptId;
	}
	public void setRptId(int id) {
		this.rptId = id;
	}
	public Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}
	public String getRptName() {
		return rptName;
	}
	public void setRptName(String rptName) {
		this.rptName = rptName;
	}
	
	public String getRptPath() {
		return rptPath;
	}
	public void setRptPath(String rptPath) {
		this.rptPath = rptPath;
	}
	public String getRptPathByType(){
		switch (rptId) {
		case TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT: 
			return REPORITORY_ROOT + "\\WeeklySalesAnalysis\\";
		case TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT:
			return REPORITORY_ROOT + "\\AccumulatedSalesAnalysis\\";
		default:
			return "";
		}
	}
	public String getDownloadRptName(){
		switch (this.rptId) {
		case TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT:
			return "当季货品销售报表" + rptDate + ".zip";
		case TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT:
			return "当季销售分析报表" + rptDate + ".zip";
		default:
			return "download.zip";
		}
	}
}
