package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

public class ChainAutoRptRepositoty implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8894145370950092740L;
	
	public static final Integer[] RPT_TYPES = {1};
	/**
	 * 当季货品的每周滞销货，热销货报表
	 */
	public static final int TYPE_WEEKLY_SALES_ANALYSIS_RPT = 1;
	
	private int rptId;
	private Date rptDate;
	private String rptName;
	/**
	 * rpt的描述比如 
	 * 2015-1-2 至 2015-2-1
	 */
	private String rptDes;
	
	public String getRptDes() {
		return rptDes;
	}
	public void setRptDes(String rptDes) {
		this.rptDes = rptDes;
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
	
	
}
