package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.common.Common_util;


public class ChainSalesStatisticReportItemVO {
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";
	private int parentId;
	private int chainId;
	private int salerId;
	private int yearId;
	private int quarterId;
	private int brandId;
	
	private boolean seeCost = false;

	private String name;
	private String id;
	
	protected Date startDate = new Date();
	protected Date endDate = new Date();

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
	
	private String state;
	
	
	public ChainSalesStatisticReportItemVO(String name, int parentId, int yearId, int quarterId, int brandId, boolean seeCost, String state){
		this.setId(Common_util.getUUID());
		this.setName(name);
		this.setParentId(parentId);
		this.setChainId(chainId);
		this.setSalerId(salerId);
		this.setYearId(yearId);
		this.setQuarterId(quarterId);
		this.setBrandId(brandId);
		this.setSeeCost(seeCost);
		this.setState(state);
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getSeeCost() {
		return seeCost;
	}
	public void setSeeCost(boolean seeCost) {
		this.seeCost = seeCost;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public int getSalerId() {
		return salerId;
	}
	public void setSalerId(int salerId) {
		this.salerId = salerId;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getNetQ() {
		return netQ;
	}
	public void setNetQ(int netQ) {
		this.netQ = netQ;
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
	public double getReturnPrice() {
		return returnPrice;
	}
	public void setReturnPrice(double returnPrice) {
		this.returnPrice = returnPrice;
	}
	public double getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}
	public double getSalesDiscount() {
		return salesDiscount;
	}
	public void setSalesDiscount(double salesDiscount) {
		this.salesDiscount = salesDiscount;
	}
	public double getSalesCost() {
		return salesCost;
	}
	public void setSalesCost(double salesCost) {
		this.salesCost = salesCost;
	}
	public double getReturnCost() {
		return returnCost;
	}
	public void setReturnCost(double returnCost) {
		this.returnCost = returnCost;
	}
	public double getNetCost() {
		return netCost;
	}
	public void setNetCost(double netCost) {
		this.netCost = netCost;
	}
	public double getFreeCost() {
		return freeCost;
	}
	public void setFreeCost(double freeCost) {
		this.freeCost = freeCost;
	}
	public double getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}
	
	public void putValue(int quantity, int type, double sales, double cost, double salesDiscount){
		switch (type) {
		case ChainStoreSalesOrderProduct.SALES_OUT:
			this.setSalesPrice(sales);
			this.setSalesCost(cost);
			this.setSalesQ(quantity);
			this.setSalesDiscount(salesDiscount + this.getSalesDiscount());
			break;
		case ChainStoreSalesOrderProduct.RETURN_BACK:
			this.setReturnPrice(sales);
			this.setReturnCost(cost);
			this.setReturnQ(quantity);
			break;
		case ChainStoreSalesOrderProduct.FREE:
			this.setFreeCost(cost);
			this.setFreeQ(quantity);
			break;
		default:
			break;
		}
	}

	
	public void reCalculate(){
		netQ = salesQ - returnQ;
		netPrice = salesPrice - returnPrice;
		netCost = salesCost - returnCost;
		netProfit = netPrice - netCost - freeCost;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
