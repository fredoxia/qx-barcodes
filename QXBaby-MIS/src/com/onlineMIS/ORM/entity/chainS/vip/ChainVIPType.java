package com.onlineMIS.ORM.entity.chainS.vip;

import java.io.Serializable;

public class ChainVIPType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2147273576778022351L;
	
	public static final int VIP1_ID = 1;
	public static final int VIP2_ID = 2;
	public static final int VIP3_ID = 3;
	
	
    private int id;
    private String vipTypeName;
    private double discountRate;
    private double couponRatio;
    private String comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVipTypeName() {
		return vipTypeName;
	}
	public void setVipTypeName(String vipTypeName) {
		this.vipTypeName = vipTypeName;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public double getCouponRatio() {
		return couponRatio;
	}
	public void setCouponRatio(double couponRatio) {
		this.couponRatio = couponRatio;
	}
    
    
}
