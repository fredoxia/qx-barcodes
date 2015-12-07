package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.stereotype.Repository;


public class ChainManualCtrlBatchJob implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9127997782539189880L;
	private int id;
	private int rptId;
	private String comment;
	private Date startDate;
	private Date endDate;
	private int yearId;
	private int quarterId;
	private int brandId;
	private java.util.Date createDate;
	private java.util.Date executeDate;
	private String attachmentFileName;
	/**
	 * 0: not executed
	 * 1: executed
	 */
	private int executed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRptId() {
		return rptId;
	}
	public void setRptId(int rptId) {
		this.rptId = rptId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(java.util.Date executeDate) {
		this.executeDate = executeDate;
	}
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public int getExecuted() {
		return executed;
	}
	public void setExecuted(int executed) {
		this.executed = executed;
	}
	
	
}
