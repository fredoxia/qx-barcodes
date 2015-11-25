package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;

public class ChainLoginStatisticInforVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5786757897880665268L;
	private String desc;
	private int quantity;
	
	public ChainLoginStatisticInforVO(){
		
	}
	
	public ChainLoginStatisticInforVO(String desc, int quantity){
		this.setDesc(desc);
		this.setQuantity(quantity);
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
