package com.onlineMIS.action.shared.expense;


import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.shared.expense.Expense;

public class ExpenseActionFormBean {
	private ChainStore chainStore = new ChainStore();
	private Expense expense = new Expense();
	private java.sql.Date startDate;
	private java.sql.Date endDate;
	private int expenseRptLevel;
	
	
	public int getExpenseRptLevel() {
		return expenseRptLevel;
	}

	public void setExpenseRptLevel(int expenseRptLevel) {
		this.expenseRptLevel = expenseRptLevel;
	}

	public java.sql.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	public java.sql.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	
	
}
