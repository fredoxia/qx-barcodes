package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainPriceIncrement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6609215693878322729L;
	public static final int TYPE_MULTIPLE = 1;
	public static final int TYPE_ADD = 2;
	
	private int id;
	private int incrementType;
	private double increment;
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIncrementType() {
		return incrementType;
	}
	public void setIncrementType(int incrementType) {
		this.incrementType = incrementType;
	}
	public double getIncrement() {
		return increment;
	}
	public void setIncrement(double increment) {
		this.increment = increment;
	}
	
	public void formatDescription(){
		String prefix = "";
		String follow = "";
		if (incrementType == TYPE_MULTIPLE){
			prefix = "价格按百分比涨幅";
			follow = "%";
		} else {
			prefix = "价格固定涨幅";
			follow = "元";		
		}
		
		this.description = prefix + increment + follow;
	}
	
}
