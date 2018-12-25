package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainSalesStatisReportItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Date startDate = new Date();
	protected Date endDate = new Date();
	protected ChainStore chainStore = new ChainStore();
	protected ChainUserInfor saler = new ChainUserInfor();
	private ProductBarcode productBarcode = new ProductBarcode();
	protected int salesQ = 0;
	protected int returnQ = 0;
	protected int netQ = 0;
	protected int freeQ = 0;
	//销售额
	protected double salesPrice = 0;
	//退货额
	protected double returnPrice = 0;
	//净销售额
	protected double netPrice =0;
	//销售折扣
	protected double salesDiscount = 0;
	//销售成本
	protected double salesCost = 0;
	//退货成本
	protected double returnCost = 0;
	//净销售成本
	protected double netCost = 0;
	//赠品成本
	protected double freeCost = 0;
	//净利润
	protected double netProfit = 0;

	public ChainSalesStatisReportItem(){
		
	}
	
	public ChainSalesStatisReportItem(ChainStore chainStore, ChainUserInfor saler, Date rptStartDate, Date rptEndDate, ProductBarcode pb){
		this.chainStore = chainStore;
		this.saler = saler;
		this.startDate = rptStartDate;
		this.endDate = rptEndDate;
		this.setProductBarcode(pb);
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}

	public double getSalesDiscount() {
		return salesDiscount;
	}

	public void setSalesDiscount(double salesDiscount) {
		this.salesDiscount = salesDiscount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNetQ() {
		return netQ;
	}
	public void setNetQ(int netQ) {
		this.netQ = netQ;
	}
	public double getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
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
	public ChainUserInfor getSaler() {
		return saler;
	}
	public void setSaler(ChainUserInfor saler) {
		this.saler = saler;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public int getSalesQ() {
		return salesQ;
	}
	public void setSalesQ(int salesQ) {
		this.salesQ = salesQ;
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
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public double getSalesCost() {
		return salesCost;
	}
	public void setSalesCost(double salesCost) {
		this.salesCost = salesCost;
	}
	public double getReturnPrice() {
		return returnPrice;
	}
	public void setReturnPrice(double returnPrice) {
		this.returnPrice = returnPrice;
	}
	public double getReturnCost() {
		return returnCost;
	}
	public void setReturnCost(double returnCost) {
		this.returnCost = returnCost;
	}
	public double getFreeCost() {
		return freeCost;
	}
	public void setFreeCost(double freeCost) {
		this.freeCost = freeCost;
	}
	
	public void add(ChainSalesStatisReportItem item){
		this.salesQ += item.getSalesQ();
		this.returnQ += item.getReturnQ();
		this.freeQ += item.getFreeQ();
		this.netQ += item.getNetQ();
		this.salesPrice += item.getSalesPrice();
		this.returnPrice += item.getReturnPrice();
		this.netPrice += item.getNetPrice();
		this.salesCost += item.getReturnCost();
		this.returnCost += item.getReturnCost();
		this.netCost += item.getNetCost();
		this.freeCost += item.getFreeCost();
		this.netProfit += item.getNetProfit();
		this.salesDiscount += item.getSalesDiscount();
	}
	

}
