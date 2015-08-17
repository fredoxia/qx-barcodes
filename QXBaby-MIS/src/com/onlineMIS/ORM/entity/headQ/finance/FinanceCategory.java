package com.onlineMIS.ORM.entity.headQ.finance;

import java.io.Serializable;


public class FinanceCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852583637495149815L;
	public static final int PREPAY_ACCT_TYPE = 3;
	public static final int INCREASE_DECREASE_ACCT_TYPE = 4;

	private int id;
	private String itemName;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	

}
