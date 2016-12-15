package com.onlineMIS.ORM.entity.chainS.report;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;

public class ChainSalesReport extends ChainReport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * sales report element
	 */
    private int saleQuantitySum = 0;
    private int returnQuantitySum = 0;
    private int freeQantitySum = 0;
    private int netQuantitySum = 0;
    private double salesAmtSum = 0;
    private double salesDiscountAmtSum = 0;
    private double returnAmtSum = 0;
    private double netAmtSum = 0;
    private double netSaleCostSum = 0;
    private double freeCostSum = 0;
    private double discountSum = 0;
    private double netProfit = 0;
    private double couponSum = 0;
    private double receiveAmtSum = 0;
    private double cardAmtSum = 0;
    private double cashNetSum = 0;
    private double wechatAmtSum = 0;
    private double alipaySum = 0;    
    private double vipScoreSum = 0;
    /**
     * 消费的预存款
     */
    private double vipPrepaidAmt = 0;
    /**
     * 预存款，现金+刷卡
     */
    private double vipPrepaidDepositCash = 0;
    private double vipPrepaidDepositCard = 0;
    /**
     * 累计预存款总数
     */
    private double vipPrepaidAccumulate = 0;
    
    
    private ChainUserInfor user = null;
	/**
	 * 千禧货品数量
	 */
	private int qxQuantity;
	
	/**
	 * 千禧货品净销售
	 */
	private double qxAmount;
	
	/**
	 * 千禧货品成本
	 */
	private double qxCost;
	
	/**
	 * 自己货品的数量
	 */
	private int myQuantity;
	
	/**
	 * 自己货品净销售
	 */
	private double myAmount;
	
	/**
	 * 自己货品成本
	 */
	private double myCost;
	
	private double vipSaleAmt;
	
	private int vipSaleQ;

	private double vipPercentage;
	
    public double getVipPrepaidAccumulate() {
		return vipPrepaidAccumulate;
	}

	public void setVipPrepaidAccumulate(double vipPrepaidAccumulate) {
		this.vipPrepaidAccumulate = vipPrepaidAccumulate;
	}

	public ChainSalesReport(){
    	
    }
    
    public ChainSalesReport(int type, int saleQuantitySum, int returnQuantitySum,
			int freeQantitySum, double salesAmtSum, double salesDiscountAmtSum, double returnAmtSum,
			double netSaleCostSum, double freeCostSum, double discountSum,
			double couponSum, double cardAmtSum, double cashNetSum, double vipScoreSum, int qxQ, double qxAmount, double qxCost, int myQ, double myAmount, double myCost,int vipSaleQ,double vipSaleAmt, double vipPrepaidAmt, double vipPrepaidDepositCash, double vipPrepaidDepositCard, double vipPrepaidAccumulate, ChainStore chainStore, ChainUserInfor user, double wechatAmt, double alipayAmt) {
    	this(type, saleQuantitySum, returnQuantitySum, freeQantitySum, salesAmtSum, salesDiscountAmtSum, returnAmtSum, netSaleCostSum, freeCostSum, discountSum, couponSum, cardAmtSum, cashNetSum, vipScoreSum, qxQ, qxAmount, qxCost,  myQ,  myAmount, myCost, vipSaleQ, vipSaleAmt,vipPrepaidAmt, vipPrepaidDepositCash, vipPrepaidDepositCard,vipPrepaidAccumulate, chainStore, wechatAmt, alipayAmt);
    	this.user = user;
    }
    
    
    public ChainSalesReport(int type, int saleQuantitySum, int returnQuantitySum,
			int freeQantitySum, double salesAmtSum, double salesDiscountAmtSum, double returnAmtSum,
			double netSaleCostSum, double freeCostSum, double discountSum,
			double couponSum, double cardAmtSum, double cashNetSum, double vipScoreSum, int qxQ, double qxAmount, double qxCost, int myQ, double myAmount, double myCost,int vipSaleQ,double vipSaleAmt, double vipPrepaidAmt, double vipPrepaidDepositCash, double vipPrepaidDepositCard, double vipPrepaidAccumulate, ChainStore chainStore, double wechatAmt, double alipayAmt) {
    	this(type, saleQuantitySum, returnQuantitySum, freeQantitySum, salesAmtSum,salesDiscountAmtSum, returnAmtSum, netSaleCostSum, freeCostSum, discountSum, couponSum, cardAmtSum, cashNetSum, vipScoreSum, qxQ, qxAmount, qxCost,  myQ, myAmount, myCost, vipSaleQ, vipSaleAmt,vipPrepaidAmt,vipPrepaidDepositCash, vipPrepaidDepositCard,vipPrepaidAccumulate,wechatAmt,alipayAmt);
    	this.chainStore = chainStore;
    }
    
	public ChainSalesReport(int type, int saleQuantitySum, int returnQuantitySum,
			int freeQantitySum, double salesAmtSum, double salesDiscountAmtSum, double returnAmtSum,
			double netSaleCostSum, double freeCostSum, double discountSum,
			double couponSum, double cardAmtSum, double cashNetSum, double vipScoreSum, int qxQ, double qxAmount, double qxCost, int myQ, double myAmount, double myCost,int vipSaleQ,double vipSaleAmt, double vipPrepaidAmt, double vipPrepaidDepositCash, double vipPrepaidDepositCard, double vipPrepaidAccumulate, double wechatAmt, double alipayAmt) {
		super();
		this.type = type;
		this.saleQuantitySum = saleQuantitySum;
		this.returnQuantitySum = returnQuantitySum;
		this.freeQantitySum = freeQantitySum;
		this.salesAmtSum = salesAmtSum;
		this.salesDiscountAmtSum = salesDiscountAmtSum;
		this.returnAmtSum = returnAmtSum;
		this.netSaleCostSum = netSaleCostSum;
		this.freeCostSum = freeCostSum;
		this.discountSum = discountSum;
		this.couponSum = couponSum;
		this.cardAmtSum = cardAmtSum;
		this.cashNetSum = cashNetSum;
		this.vipScoreSum = vipScoreSum;
		this.netQuantitySum = saleQuantitySum - returnQuantitySum;
		this.netAmtSum = salesAmtSum - returnAmtSum;
		this.netProfit = salesAmtSum - returnAmtSum - netSaleCostSum - freeCostSum - discountSum;
		this.receiveAmtSum = cardAmtSum + cashNetSum + wechatAmt +alipayAmt ;
		this.qxQuantity = qxQ;
		this.qxAmount = qxAmount;
		this.qxCost = qxCost;
		this.myAmount = myAmount;
		this.myCost = myCost;
		this.myQuantity = myQ;
		this.vipSaleAmt = vipSaleAmt;
		this.vipSaleQ = vipSaleQ;
		if (netAmtSum > 0)
			this.vipPercentage = vipSaleAmt / netAmtSum * 100;
		this.vipPrepaidAmt = vipPrepaidAmt;
		this.vipPrepaidDepositCash = vipPrepaidDepositCash;
		this.vipPrepaidDepositCard = vipPrepaidDepositCard;
		this.vipPrepaidAccumulate = vipPrepaidAccumulate;
		this.wechatAmtSum = wechatAmt;
		this.alipaySum = alipayAmt;
	}

	
	
	public double getVipSaleAmt() {
		return vipSaleAmt;
	}

	public void setVipSaleAmt(double vipSaleAmt) {
		this.vipSaleAmt = vipSaleAmt;
	}

	public int getVipSaleQ() {
		return vipSaleQ;
	}

	public void setVipSaleQ(int vipSaleQ) {
		this.vipSaleQ = vipSaleQ;
	}

	public double getVipPercentage() {
		return vipPercentage;
	}

	public void setVipPercentage(double vipPercentage) {
		this.vipPercentage = vipPercentage;
	}

	public ChainUserInfor getUser() {
		return user;
	}

	public void setUser(ChainUserInfor user) {
		this.user = user;
	}

	public int getNetQuantitySum() {
		return netQuantitySum;
	}

	public void setNetQuantitySum(int netQuantitySum) {
		this.netQuantitySum = netQuantitySum;
	}

	public double getNetAmtSum() {
		return netAmtSum;
	}

	public void setNetAmtSum(double netAmtSum) {
		this.netAmtSum = netAmtSum;
	}

	public double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}

	public double getVipScoreSum() {
		return vipScoreSum;
	}

	public void setVipScoreSum(double vipScoreSum) {
		this.vipScoreSum = vipScoreSum;
	}

	public int getSaleQuantitySum() {
		return saleQuantitySum;
	}

	public void setSaleQuantitySum(int saleQuantitySum) {
		this.saleQuantitySum = saleQuantitySum;
	}

	public int getReturnQuantitySum() {
		return returnQuantitySum;
	}

	public void setReturnQuantitySum(int returnQuantitySum) {
		this.returnQuantitySum = returnQuantitySum;
	}

	public int getFreeQantitySum() {
		return freeQantitySum;
	}

	public void setFreeQantitySum(int freeQantitySum) {
		this.freeQantitySum = freeQantitySum;
	}

	public double getSalesAmtSum() {
		return salesAmtSum;
	}

	public void setSalesAmtSum(double salesAmtSum) {
		this.salesAmtSum = salesAmtSum;
	}

	public double getReturnAmtSum() {
		return returnAmtSum;
	}

	public void setReturnAmtSum(double returnAmtSum) {
		this.returnAmtSum = returnAmtSum;
	}

	public double getNetSaleCostSum() {
		return netSaleCostSum;
	}

	public void setNetSaleCostSum(double netSaleCostSum) {
		this.netSaleCostSum = netSaleCostSum;
	}

	public double getFreeCostSum() {
		return freeCostSum;
	}

	public void setFreeCostSum(double freeCostSum) {
		this.freeCostSum = freeCostSum;
	}

	public double getDiscountSum() {
		return discountSum;
	}

	public void setDiscountSum(double discountSum) {
		this.discountSum = discountSum;
	}

	public double getCouponSum() {
		return couponSum;
	}

	public void setCouponSum(double couponSum) {
		this.couponSum = couponSum;
	}

	public double getCardAmtSum() {
		return cardAmtSum;
	}

	public void setCardAmtSum(double cardAmtSum) {
		this.cardAmtSum = cardAmtSum;
	}

	public double getCashNetSum() {
		return cashNetSum;
	}

	public void setCashNetSum(double cashNetSum) {
		this.cashNetSum = cashNetSum;
	}

	public double getSalesDiscountAmtSum() {
		return salesDiscountAmtSum;
	}

	public void setSalesDiscountAmtSum(double salesDiscountAmtSum) {
		this.salesDiscountAmtSum = salesDiscountAmtSum;
	}

	public double getReceiveAmtSum() {
		return receiveAmtSum;
	}

	public void setReceiveAmtSum(double receiveAmtSum) {
		this.receiveAmtSum = receiveAmtSum;
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

	public double getVipPrepaidAmt() {
		return vipPrepaidAmt;
	}

	public void setVipPrepaidAmt(double vipPrepaidAmt) {
		this.vipPrepaidAmt = vipPrepaidAmt;
	}

	public double getVipPrepaidDepositCash() {
		return vipPrepaidDepositCash;
	}

	public void setVipPrepaidDepositCash(double vipPrepaidDepositCash) {
		this.vipPrepaidDepositCash = vipPrepaidDepositCash;
	}

	public double getVipPrepaidDepositCard() {
		return vipPrepaidDepositCard;
	}

	public void setVipPrepaidDepositCard(double vipPrepaidDepositCard) {
		this.vipPrepaidDepositCard = vipPrepaidDepositCard;
	}

	public double getWechatAmtSum() {
		return wechatAmtSum;
	}

	public void setWechatAmtSum(double wechatAmtSum) {
		this.wechatAmtSum = wechatAmtSum;
	}

	public double getAlipaySum() {
		return alipaySum;
	}

	public void setAlipaySum(double alipaySum) {
		this.alipaySum = alipaySum;
	}

}