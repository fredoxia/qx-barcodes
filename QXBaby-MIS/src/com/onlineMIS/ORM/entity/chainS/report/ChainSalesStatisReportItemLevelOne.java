package com.onlineMIS.ORM.entity.chainS.report;

import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainSalesStatisReportItemLevelOne extends
		ChainSalesStatisReportItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852109842683604179L;
	protected Year year = new Year();
	
	public ChainSalesStatisReportItemLevelOne(){
		
	}
	
	public ChainSalesStatisReportItemLevelOne(ChainStore chainStore, ChainUserInfor saler, Date rptStartDate, Date rptEndDate, Year year){
		super(chainStore, saler,rptStartDate, rptEndDate);
		this.year = year;
	}
	
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
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
	
	public void putValue(int quantity, int type, double sales, double cost, double salesDiscount, Year year, ChainStore chainStore, ChainUserInfor saler){
		putValue(quantity, type, sales, cost, salesDiscount);
		setChainStore(chainStore);
		setSaler(saler);
		setYear(year);
	}
	
	public void reCalculate(){
		netQ = salesQ - returnQ;
		netPrice = salesPrice - returnPrice;
		netCost = salesCost - returnCost;
		netProfit = netPrice - netCost - freeCost;
	}
}
