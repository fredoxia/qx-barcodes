package com.onlineMIS.ORM.entity.chainS.sales;

import java.util.Date;

public class ChainCouponConsumeHis {
	public static int COUPON_USED = 1;
	public static int COUPON_UNUSED = 0;
	
	
	private int couponNum;
	private Date date;
	
	/**
	 * 0: unused
	 * 1: used
	 */
	private int used = 1;
	
	public ChainCouponConsumeHis(){
		
	}
	
	public ChainCouponConsumeHis(int couponNum, int used){
		this.couponNum = couponNum;
		this.used = used;
		this.date = new Date();
	}
	
	
	
	public int getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	
	
}
