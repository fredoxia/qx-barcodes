package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;

public class VIPReportVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4601272270101158280L;
	private ChainVIPCard vip = null;
    private int saleQ = 0;
    private int returnQ = 0;
    private int freeQ = 0;
    private int netQ = 0;
    private double salesAmt = 0;
    private double returnAmt= 0;
    private double netAmt = 0;
    private double couponSum = 0;
    private double receiveAmt = 0;
    private double discountAmt = 0;

	public VIPReportVO(int saleQ, int returnQ, int freeQ, double salesAmt, double returnAmt, double couponSum, double receiveAmt, double discountAmt, ChainVIPCard vip){
		this.saleQ = saleQ;
		this.returnQ = returnQ;
		this.freeQ = freeQ;
		this.salesAmt = salesAmt;
		this.returnAmt = returnAmt;
		this.couponSum = couponSum;
		this.receiveAmt = receiveAmt;
		this.discountAmt = discountAmt;
		this.netQ = saleQ - returnQ;
		this.netAmt = salesAmt - returnAmt;
		this.vip = vip;
	}
    
    
	public int getSaleQ() {
		return saleQ;
	}
	public void setSaleQ(int saleQ) {
		this.saleQ = saleQ;
	}
	public int getReturnQ() {
		return returnQ;
	}
	public void setReturnQ(int returnQ) {
		this.returnQ = returnQ;
	}
	public int getFreeQ() {
		return freeQ;
	}
	public void setFreeQ(int freeQ) {
		this.freeQ = freeQ;
	}
	public int getNetQ() {
		return netQ;
	}
	public void setNetQ(int netQ) {
		this.netQ = netQ;
	}
	public double getReturnAmt() {
		return returnAmt;
	}
	public void setReturnAmt(double returnAmt) {
		this.returnAmt = returnAmt;
	}
	public double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	public double getCouponSum() {
		return couponSum;
	}
	public void setCouponSum(double couponSum) {
		this.couponSum = couponSum;
	}
	public double getReceiveAmt() {
		return receiveAmt;
	}
	public void setReceiveAmt(double receiveAmtSum) {
		this.receiveAmt = receiveAmtSum;
	}
	public double getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(double discountAmt) {
		this.discountAmt = discountAmt;
	}
	public ChainVIPCard getVip() {
		return vip;
	}
	public void setVip(ChainVIPCard vip) {
		this.vip = vip;
	}
	public double getSalesAmt() {
		return salesAmt;
	}
	public void setSalesAmt(double salesAmt) {
		this.salesAmt = salesAmt;
	}

}
