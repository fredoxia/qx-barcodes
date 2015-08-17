package com.onlineMIS.ORM.entity.chainS.sales;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainDailySalesImpact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8024825746726114528L;
	private ChainStore chainStore; 
    private Date reportDate;
    private int orderId;
    private java.util.Date createDate;
    private java.util.Date failDate;
    
    public ChainDailySalesImpact(){
    	
    }
    
    public ChainDailySalesImpact(ChainStore chainStore, Date reportDate, int orderId){
    	this.setChainStore(chainStore);
    	this.setReportDate(reportDate);
    	this.setCreateDate(new java.util.Date());
    	this.setOrderId(orderId);
    }
    
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getFailDate() {
		return failDate;
	}
	public void setFailDate(java.util.Date failDate) {
		this.failDate = failDate;
	}
    
    
}
