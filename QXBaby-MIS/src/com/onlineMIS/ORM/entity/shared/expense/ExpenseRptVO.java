package com.onlineMIS.ORM.entity.shared.expense;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ExpenseRptVO implements Serializable{
	private static final long serialVersionUID = 3632171643209176686L;
	
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";
	private String id;
	private String name;
	private double amount;
	private int expenseRptLevel = 0;
	private String state;
	
	public ExpenseRptVO(){
		
	}

	public ExpenseRptVO(String expenseEleName, double amount, int expenseRptLevel, String state){
		this.setId(Common_util.getUUID());
		this.setName(expenseEleName);
		this.setAmount(amount);
		this.setExpenseRptLevel(expenseRptLevel);
		this.setState(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String expenseEleName) {
		this.name = expenseEleName;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public int getExpenseRptLevel() {
		return expenseRptLevel;
	}


	public void setExpenseRptLevel(int expenseRptLevel) {
		this.expenseRptLevel = expenseRptLevel;
	}
	
	

	
}
