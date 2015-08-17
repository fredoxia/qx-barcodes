package com.onlineMIS.ORM.entity.headQ.finance;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainAcctFlowReportItem {
	public static final String ITEM_TYPE_FINANCE = "F";
	public static final String ITEM_TYPE_PURCHASE = "P";
	
	public static final String ACCT_FLOW_TYPE_INCREASE = "I";
	public static final String ACCT_FLOW_TYPE_DECREASE = "D";
	
	private ChainStore chainStore = new ChainStore();
	private Date date = new Date();
	private String itemTypeName = "";
	private String itemType = "";
	private String acctFlowType = "";
	private int quantity;
	private double amount;
	private int id;
	private String comment;
	private double acctIncrease ;
	private double acctDecrease;
	private double postAcct;
	
	public ChainAcctFlowReportItem(){
		
	}
	
	public ChainAcctFlowReportItem(ChainStore chainStore, Date date,String itemTypeName, String itemType, String acctFlowType, int quantity, double amount, int id, String comment, double preAcct, double postAcct){
		this.chainStore = chainStore;
		this.date = date;
		this.itemTypeName = itemTypeName;
		this.itemType = itemType;
		this.quantity = quantity;
		this.amount = amount;
		this.id = id;
		this.comment = comment;
		this.acctFlowType = acctFlowType;
	}
	


	public String getAcctFlowType() {
		return acctFlowType;
	}

	public void setAcctFlowType(String acctFlowType) {
		this.acctFlowType = acctFlowType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getAcctIncrease() {
		return acctIncrease;
	}

	public void setAcctIncrease(double acctIncrease) {
		this.acctIncrease = acctIncrease;
	}

	public double getAcctDecrease() {
		return acctDecrease;
	}

	public void setAcctDecrease(double acctDecrease) {
		this.acctDecrease = acctDecrease;
	}

	public double getPostAcct() {
		return postAcct;
	}
	public void setPostAcct(double postAcct) {
		this.postAcct = postAcct;
	}
	
	
}
