package com.onlineMIS.ORM.entity.shared.expense;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ExpenseVO implements Serializable{
	private static final long serialVersionUID = 3632171643209176686L;

	private int id;
	private String expenseType;
	private double amount;
	private String comment;
	private String userName;
	private String status;
	private int statusCode;
	private String expenseDate;
	private String lastUpdateTime;
	private String entity;
	
	public ExpenseVO(){
		
	}
	
	public ExpenseVO(Expense e){
		this.setId(e.getId());
		this.setExpenseType(e.getExpenseType().getName());
		this.setAmount(e.getAmount());
		this.setComment(e.getComment());
		this.setUserName(e.getUserName());
		this.setStatus(Expense.statusE.getStatusS(e.getStatus()));
		ChainStore store = e.getEntity();
		if (store == null)
			this.setEntity("总部");
		else 
			this.setEntity(store.getChain_name());
		
		this.setExpenseDate(Common_util.dateFormat.format(e.getExpenseDate()));
		this.setLastUpdateTime(Common_util.dateFormat_f.format(e.getLastUpdateTime()));
		this.setStatusCode(e.getStatus());
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String belong) {
		this.entity = belong;
	}
	
	
}
