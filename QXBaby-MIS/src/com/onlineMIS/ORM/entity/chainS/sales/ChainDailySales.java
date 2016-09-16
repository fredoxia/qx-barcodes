package com.onlineMIS.ORM.entity.chainS.sales;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainDailySales  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145222745550253258L;
	private ChainStore chainStore; 
    private Date reportDate;
    private int salesQuantity;
    private int returnQuantity;
    private int netSalesQuantity;
    private int freeQuantity;
    private double salesAmount;
    private double returnAmount;
    private double netSalesAmount;
    private double netCost;
    private double netProfit;
    private double freeCost;
    private double discountAmount;
    private double coupon;
    private double vipScoreCash;
    private double card;
    private double cash;
    private java.util.Date createDate;
    private int rank;
	private int qxQuantity;
	private double qxAmount;
	private double qxCost;
	private int myQuantity;
	private double myAmount;
	private double myCost; 
	private double wechatAmt;
	private double alipayAmt;
	
    public ChainDailySales(){
    	
    }
    
    public ChainDailySales(ChainStore chainStore, Date reportDate){
		super();
		this.chainStore = chainStore;
		this.reportDate = reportDate;
		this.createDate = Common_util.getToday();
    }
    
    public ChainDailySales(ChainStore chainStore, Date reportDate,
			int salesQuantity, int returnQuantity, int netSalesQuantity,
			int freeQuantity, double salesAmount, double returnAmount,
			double netSalesAmount, double netCost, double netProfit,
			double freeCost, double discountAmount, double coupon,
			double vipScoreCash, double card, double cash, int rank, int qxQ, double qxAmount, double qxCost, int myQ, double myAmount, double myCost, double wechatAmt, double alipayAmt) {
        this(chainStore, reportDate, salesQuantity, returnQuantity, netSalesQuantity, freeQuantity, salesAmount, returnAmount, netSalesAmount, netCost, netProfit, freeCost, discountAmount, coupon, vipScoreCash, card, cash, qxQ, qxAmount, qxCost, myQ, myAmount, myCost, wechatAmt, alipayAmt);
        this.rank = rank;
    }

	public ChainDailySales(ChainStore chainStore, Date reportDate,
			int salesQuantity, int returnQuantity, int netSalesQuantity,
			int freeQuantity, double salesAmount, double returnAmount,
			double netSalesAmount, double netCost, double netProfit,
			double freeCost, double discountAmount, double coupon,
			double vipScoreCash, double card, double cash, int qxQ, double qxAmount, double qxCost, int myQ, double myAmount, double myCost, double wechatAmt, double alipayAmt) {
		super();
		this.chainStore = chainStore;
		this.reportDate = reportDate;
		this.salesQuantity = salesQuantity;
		this.returnQuantity = returnQuantity;
		this.netSalesQuantity = netSalesQuantity;
		this.freeQuantity = freeQuantity;
		this.salesAmount = salesAmount;
		this.returnAmount = returnAmount;
		this.netSalesAmount = netSalesAmount;
		this.netCost = netCost;
		this.netProfit = netProfit;
		this.freeCost = freeCost;
		this.discountAmount = discountAmount;
		this.coupon = coupon;
		this.vipScoreCash = vipScoreCash;
		this.card = card;
		this.cash = cash;
		this.createDate = Common_util.getToday();
		this.qxQuantity = qxQ;
		this.qxAmount = qxAmount;
		this.qxCost = qxCost;
		this.myQuantity = myQ;
		this.myAmount = myAmount;
		this.myCost = myCost;
		this.wechatAmt = wechatAmt;
		this.alipayAmt = alipayAmt;
	}



	public double getWechatAmt() {
		return wechatAmt;
	}

	public void setWechatAmt(double wechatAmt) {
		this.wechatAmt = wechatAmt;
	}

	public double getAlipayAmt() {
		return alipayAmt;
	}

	public void setAlipayAmt(double alipayAmt) {
		this.alipayAmt = alipayAmt;
	}

	public int getQxQuantity() {
		return qxQuantity;
	}

	public void setQxQuantity(int qxQuantity) {
		this.qxQuantity = qxQuantity;
	}

	public double getQxAmount() {
		return qxAmount;
	}

	public void setQxAmount(double qxAmount) {
		this.qxAmount = qxAmount;
	}

	public double getQxCost() {
		return qxCost;
	}

	public void setQxCost(double qxCost) {
		this.qxCost = qxCost;
	}

	public int getMyQuantity() {
		return myQuantity;
	}

	public void setMyQuantity(int myQuantity) {
		this.myQuantity = myQuantity;
	}

	public double getMyAmount() {
		return myAmount;
	}

	public void setMyAmount(double myAmount) {
		this.myAmount = myAmount;
	}

	public double getMyCost() {
		return myCost;
	}

	public void setMyCost(double myCost) {
		this.myCost = myCost;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public int getSalesQuantity() {
		return salesQuantity;
	}
	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	public int getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public int getNetSalesQuantity() {
		return netSalesQuantity;
	}
	public void setNetSalesQuantity(int netSalesQuantity) {
		this.netSalesQuantity = netSalesQuantity;
	}
	public int getFreeQuantity() {
		return freeQuantity;
	}
	public void setFreeQuantity(int freeQuantity) {
		this.freeQuantity = freeQuantity;
	}
	public double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}
	public double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public double getNetSalesAmount() {
		return netSalesAmount;
	}
	public void setNetSalesAmount(double netSalesAmount) {
		this.netSalesAmount = netSalesAmount;
	}
	public double getNetCost() {
		return netCost;
	}
	public void setNetCost(double netCost) {
		this.netCost = netCost;
	}
	public double getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}
	public double getFreeCost() {
		return freeCost;
	}
	public void setFreeCost(double freeCost) {
		this.freeCost = freeCost;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public double getVipScoreCash() {
		return vipScoreCash;
	}
	public void setVipScoreCash(double vipScoreCash) {
		this.vipScoreCash = vipScoreCash;
	}
	public double getCard() {
		return card;
	}
	public void setCard(double card) {
		this.card = card;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
    
    
}
