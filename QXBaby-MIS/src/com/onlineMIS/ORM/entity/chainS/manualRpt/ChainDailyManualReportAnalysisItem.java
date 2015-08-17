package com.onlineMIS.ORM.entity.chainS.manualRpt;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;

public class ChainDailyManualReportAnalysisItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6702551369448454366L;
	private int rptAnalysisItemId;
	private ChainDailyManualRpt chainDailyManualRpt;
	private ChainUserInfor saler;
	private String salerName;
	private double monthlyAccumulate;
	private int rank;
	private double salesTarget;
	private double salesActual;
	private double salesPercentage;
	private double relatedSalesTarget;
	private double relatedSalesActual;
	private double amountPerSalesTarget;
	private double amountPerSalesActual;
	private double vipIncreaseTarget;
	private double vipIncreaseActual;
	private double vipIncreasePercentage;
	private double vipSalesTarget;
	private double vipSalesActual;
	private double vipSalesPercentage;
	private int rowIndex;

	
	public double getSalesPercentage() {
		return salesPercentage;
	}
	public void setSalesPercentage(double salesPercentage) {
		this.salesPercentage = salesPercentage;
	}
	public double getVipIncreasePercentage() {
		return vipIncreasePercentage;
	}
	public void setVipIncreasePercentage(double vipIncreasePercentage) {
		this.vipIncreasePercentage = vipIncreasePercentage;
	}
	public double getVipSalesPercentage() {
		return vipSalesPercentage;
	}
	public void setVipSalesPercentage(double vipSalesPercentage) {
		this.vipSalesPercentage = vipSalesPercentage;
	}
	public String getSalerName() {
		return salerName;
	}
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getRptAnalysisItemId() {
		return rptAnalysisItemId;
	}
	public void setRptAnalysisItemId(int rptAnalysisItemId) {
		this.rptAnalysisItemId = rptAnalysisItemId;
	}
	public ChainDailyManualRpt getChainDailyManualRpt() {
		return chainDailyManualRpt;
	}
	public void setChainDailyManualRpt(ChainDailyManualRpt chainDailyManualRpt) {
		this.chainDailyManualRpt = chainDailyManualRpt;
	}
	public ChainUserInfor getSaler() {
		return saler;
	}
	public void setSaler(ChainUserInfor saler) {
		this.saler = saler;
	}
	public double getMonthlyAccumulate() {
		return monthlyAccumulate;
	}
	public void setMonthlyAccumulate(double monthlyAccumulate) {
		this.monthlyAccumulate = monthlyAccumulate;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getSalesTarget() {
		return salesTarget;
	}
	public void setSalesTarget(double salesTarget) {
		this.salesTarget = salesTarget;
	}
	public double getSalesActual() {
		return salesActual;
	}
	public void setSalesActual(double salesActual) {
		this.salesActual = salesActual;
	}
	public double getRelatedSalesTarget() {
		return relatedSalesTarget;
	}
	public void setRelatedSalesTarget(double relatedSalesTarget) {
		this.relatedSalesTarget = relatedSalesTarget;
	}
	public double getRelatedSalesActual() {
		return relatedSalesActual;
	}
	public void setRelatedSalesActual(double relatedSalesActual) {
		this.relatedSalesActual = relatedSalesActual;
	}
	public double getAmountPerSalesTarget() {
		return amountPerSalesTarget;
	}
	public void setAmountPerSalesTarget(double amountPerSalesTarget) {
		this.amountPerSalesTarget = amountPerSalesTarget;
	}
	public double getAmountPerSalesActual() {
		return amountPerSalesActual;
	}
	public void setAmountPerSalesActual(double amountPerSalesActual) {
		this.amountPerSalesActual = amountPerSalesActual;
	}
	public double getVipIncreaseTarget() {
		return vipIncreaseTarget;
	}
	public void setVipIncreaseTarget(double vipIncreaseTarget) {
		this.vipIncreaseTarget = vipIncreaseTarget;
	}
	public double getVipIncreaseActual() {
		return vipIncreaseActual;
	}
	public void setVipIncreaseActual(double vipIncreaseActual) {
		this.vipIncreaseActual = vipIncreaseActual;
	}
	public double getVipSalesTarget() {
		return vipSalesTarget;
	}
	public void setVipSalesTarget(double vipSalesTarget) {
		this.vipSalesTarget = vipSalesTarget;
	}
	public double getVipSalesActual() {
		return vipSalesActual;
	}
	public void setVipSalesActual(double vipSalesActual) {
		this.vipSalesActual = vipSalesActual;
	}
	
	
}
