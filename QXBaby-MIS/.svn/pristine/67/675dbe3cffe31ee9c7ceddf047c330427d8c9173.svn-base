package com.onlineMIS.ORM.entity.headQ.finance;

import java.io.Serializable;
import java.util.Date;

public class ChainAcctFlow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3186805427385812468L;
	private int id;
	private int chainId;
	private double acctAmt;
	private Date date;
	private String comment;
	
	/**
	 * 
	 */
	
	public ChainAcctFlow(){
		
	}
	
	public ChainAcctFlow(int chainId, double acctAmt, String comment){
		this.chainId = chainId;
		this.acctAmt = acctAmt;
		this.comment = comment;
		this.date = new Date();
	}
	
	public ChainAcctFlow(int chainId, double acctAmt, String comment, Date changeDate){
		this.chainId = chainId;
		this.acctAmt = acctAmt;
		this.comment = comment;
		
		if (changeDate == null)
		    this.date = new Date();
		else 
			this.date = changeDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public double getAcctAmt() {
		return acctAmt;
	}
	public void setAcctAmt(double acctAmt) {
		this.acctAmt = acctAmt;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
