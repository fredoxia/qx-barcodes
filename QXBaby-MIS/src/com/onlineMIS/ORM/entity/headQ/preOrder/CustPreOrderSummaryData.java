package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.util.List;

public class CustPreOrderSummaryData {
	private String custName ;
	private List<Integer> q;
	private int sum ;
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public List<Integer> getQ() {
		return q;
	}
	public void setQ(List<Integer> q) {
		this.q = q;
	}
	
	
}
