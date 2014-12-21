package com.onlineMIS.ORM.entity.chainS.batchRpt;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;

public class ChainCurrentSeasonProductAnalysisItem {
	private int rank;
	private ProductBarcode pb;
	private java.sql.Date marketDate;
	private int purchaseWeekly;
	private int purchaseAccumulated;
	private int salesWeekly;
	private int salesAccumulated;
	/**
	 * 格式是45%
	 */
	private String digestRatioWeekly;
	private String digestRatioAccumulated;
	private int currentInentory;
	private int quantityInDelivery;
	
	public ChainCurrentSeasonProductAnalysisItem(){
		
	}
	
	public ChainCurrentSeasonProductAnalysisItem(ProductBarcode pb){
		this.setPb(pb);
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public ProductBarcode getPb() {
		return pb;
	}
	public void setPb(ProductBarcode pb) {
		this.pb = pb;
	}
	public java.sql.Date getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(java.sql.Date marketDate) {
		this.marketDate = marketDate;
	}
	public int getPurchaseWeekly() {
		return purchaseWeekly;
	}
	public void setPurchaseWeekly(int purchaseWeekly) {
		this.purchaseWeekly = purchaseWeekly;
	}
	public int getPurchaseAccumulated() {
		return purchaseAccumulated;
	}
	public void setPurchaseAccumulated(int purchaseAccumulated) {
		this.purchaseAccumulated = purchaseAccumulated;
	}
	public int getSalesWeekly() {
		return salesWeekly;
	}
	public void setSalesWeekly(int salesWeekly) {
		this.salesWeekly = salesWeekly;
	}
	public int getSalesAccumulated() {
		return salesAccumulated;
	}
	public void setSalesAccumulated(int salesAccumulated) {
		this.salesAccumulated = salesAccumulated;
	}
	public String getDigestRatioWeekly() {
		return digestRatioWeekly;
	}
	public void setDigestRatioWeekly(String digestRatioWeekly) {
		this.digestRatioWeekly = digestRatioWeekly;
	}
	public String getDigestRatioAccumulated() {
		return digestRatioAccumulated;
	}
	public void setDigestRatioAccumulated(String digestRatioAccumulated) {
		this.digestRatioAccumulated = digestRatioAccumulated;
	}
	public int getCurrentInentory() {
		return currentInentory;
	}
	public void setCurrentInentory(int currentInentory) {
		this.currentInentory = currentInentory;
	}
	public int getQuantityInDelivery() {
		return quantityInDelivery;
	}
	public void setQuantityInDelivery(int quantityInDelivery) {
		this.quantityInDelivery = quantityInDelivery;
	}

	public void calculateRatio() {
		double weeklyDigestRatio = 0;
		double accumulatedDigestRatio = 0;
		
		if (purchaseAccumulated != 0){
			weeklyDigestRatio = new BigDecimal(salesWeekly).divide(new BigDecimal(purchaseAccumulated),4,BigDecimal.ROUND_HALF_DOWN).doubleValue();
			accumulatedDigestRatio = new BigDecimal(salesAccumulated).divide(new BigDecimal(purchaseAccumulated),4,BigDecimal.ROUND_HALF_DOWN).doubleValue();
		}
		
		digestRatioAccumulated = Common_util.pf.format(accumulatedDigestRatio);
		digestRatioWeekly = Common_util.pf.format(weeklyDigestRatio);
		
	}
	
	
	
}
