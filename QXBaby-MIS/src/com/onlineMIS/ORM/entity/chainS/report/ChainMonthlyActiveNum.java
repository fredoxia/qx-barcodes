package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

public class ChainMonthlyActiveNum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6058440524681807217L;
	private Date reportDate;
	private int numOfActiveChain;
	
	public ChainMonthlyActiveNum(){
		
	}
	
	public ChainMonthlyActiveNum(Date reportDate, int num){
		this.reportDate = reportDate;
		this.numOfActiveChain = num;
	}
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public int getNumOfActiveChain() {
		return numOfActiveChain;
	}
	public void setNumOfActiveChain(int numOfActiveChain) {
		this.numOfActiveChain = numOfActiveChain;
	}

}
