package com.onlineMIS.action.chainS.report;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItemLevelFour;
import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItemLevelOne;
import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItemLevelThree;
import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItemLevelTwo;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItemLevelFour;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItemLevelOne;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItemLevelThree;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItemLevelTwo;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItemLevelFour;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItemLevelOne;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItemLevelThree;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItemLevelTwo;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainReportActionUIBean {
	private String reportTypeS;
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	private List<Year> years = new ArrayList<Year>();
	private List<Quarter> quarters = new ArrayList<Quarter>();
	private List<ChainUserInfor> chainSalers = new ArrayList<ChainUserInfor>();
	/**
	 * sales statis report
	 */
	private ChainSalesStatisReportItem totalItem = new ChainSalesStatisReportItem();
	private List<ChainSalesStatisReportItemLevelOne> saleStatisLevelOne = new ArrayList<ChainSalesStatisReportItemLevelOne>();
	private List<ChainSalesStatisReportItemLevelTwo> saleStatisLevelTwo = new ArrayList<ChainSalesStatisReportItemLevelTwo>();
	private List<ChainSalesStatisReportItemLevelThree> saleStatisLevelThree = new ArrayList<ChainSalesStatisReportItemLevelThree>();
	private List<ChainSalesStatisReportItemLevelFour> saleStatisLevelFour = new ArrayList<ChainSalesStatisReportItemLevelFour>();
	/**
	 * purchase statistic report
	 */
	private ChainPurchaseStatisReportItem purchaseTotalItem = new ChainPurchaseStatisReportItem();
	private List<ChainPurchaseStatisReportItemLevelOne> purchaseStatisLevelOne = new ArrayList<ChainPurchaseStatisReportItemLevelOne>();
	private List<ChainPurchaseStatisReportItemLevelTwo> purchaseStatisLevelTwo = new ArrayList<ChainPurchaseStatisReportItemLevelTwo>();
	private List<ChainPurchaseStatisReportItemLevelThree> purchaseStatisLevelThree = new ArrayList<ChainPurchaseStatisReportItemLevelThree>();
	private List<ChainPurchaseStatisReportItemLevelFour> purchaseStatisLevelFour = new ArrayList<ChainPurchaseStatisReportItemLevelFour>();

	
	/**
	 * AllInOne report
	 *
	 */
	private ChainAllInOneReportItem allInOneTotal = new ChainAllInOneReportItem();
	private List<ChainAllInOneReportItemLevelOne> allInOneLevelOne = new ArrayList<ChainAllInOneReportItemLevelOne>();
	private List<ChainAllInOneReportItemLevelTwo> allInOneLevelTwo = new ArrayList<ChainAllInOneReportItemLevelTwo>();
	private List<ChainAllInOneReportItemLevelThree> allInOneLevelThree = new ArrayList<ChainAllInOneReportItemLevelThree>();
	private List<ChainAllInOneReportItemLevelFour> allInOneLevelFour = new ArrayList<ChainAllInOneReportItemLevelFour>();

	/**
	 * report repository的参数
	 */
	//产品分析报表日期
	private List<ChainBatchRptRepositoty> currentSalesDates = new ArrayList<ChainBatchRptRepositoty>();
	//销售分析报表日期
	private List<ChainBatchRptRepositoty> accumulatedSalesDates = new ArrayList<ChainBatchRptRepositoty>();
	//调货账目流水报表日期
	private List<ChainBatchRptRepositoty> chainTransferAcctFlowDates = new ArrayList<ChainBatchRptRepositoty>();
	
	
	
	public List<ChainBatchRptRepositoty> getChainTransferAcctFlowDates() {
		return chainTransferAcctFlowDates;
	}

	public void setChainTransferAcctFlowDates(
			List<ChainBatchRptRepositoty> chainTransferAcctFlowDates) {
		this.chainTransferAcctFlowDates = chainTransferAcctFlowDates;
	}

	public List<ChainBatchRptRepositoty> getAccumulatedSalesDates() {
		return accumulatedSalesDates;
	}

	public void setAccumulatedSalesDates(List<ChainBatchRptRepositoty> accumulatedSalesDates) {
		this.accumulatedSalesDates = accumulatedSalesDates;
	}

	public List<ChainBatchRptRepositoty> getCurrentSalesDates() {
		return currentSalesDates;
	}

	public void setCurrentSalesDates(List<ChainBatchRptRepositoty> currentSalesDates) {
		this.currentSalesDates = currentSalesDates;
	}

	public List<ChainAllInOneReportItemLevelThree> getAllInOneLevelThree() {
		return allInOneLevelThree;
	}

	public void setAllInOneLevelThree(
			List<ChainAllInOneReportItemLevelThree> allInOneLevelThree) {
		this.allInOneLevelThree = allInOneLevelThree;
	}

	public List<ChainAllInOneReportItemLevelFour> getAllInOneLevelFour() {
		return allInOneLevelFour;
	}

	public void setAllInOneLevelFour(
			List<ChainAllInOneReportItemLevelFour> allInOneLevelFour) {
		this.allInOneLevelFour = allInOneLevelFour;
	}

	public List<ChainAllInOneReportItemLevelTwo> getAllInOneLevelTwo() {
		return allInOneLevelTwo;
	}

	public void setAllInOneLevelTwo(
			List<ChainAllInOneReportItemLevelTwo> allInOneLevelTwo) {
		this.allInOneLevelTwo = allInOneLevelTwo;
	}

	public ChainAllInOneReportItem getAllInOneTotal() {
		return allInOneTotal;
	}

	public void setAllInOneTotal(ChainAllInOneReportItem allInOneTotal) {
		this.allInOneTotal = allInOneTotal;
	}

	public List<ChainAllInOneReportItemLevelOne> getAllInOneLevelOne() {
		return allInOneLevelOne;
	}

	public void setAllInOneLevelOne(
			List<ChainAllInOneReportItemLevelOne> allInOneLevelOne) {
		this.allInOneLevelOne = allInOneLevelOne;
	}

	public ChainPurchaseStatisReportItem getPurchaseTotalItem() {
		return purchaseTotalItem;
	}

	public void setPurchaseTotalItem(ChainPurchaseStatisReportItem purchaseTotalItem) {
		this.purchaseTotalItem = purchaseTotalItem;
	}

	public List<ChainPurchaseStatisReportItemLevelOne> getPurchaseStatisLevelOne() {
		return purchaseStatisLevelOne;
	}

	public void setPurchaseStatisLevelOne(
			List<ChainPurchaseStatisReportItemLevelOne> purchaseStatisLevelOne) {
		this.purchaseStatisLevelOne = purchaseStatisLevelOne;
	}

	public List<ChainPurchaseStatisReportItemLevelTwo> getPurchaseStatisLevelTwo() {
		return purchaseStatisLevelTwo;
	}

	public void setPurchaseStatisLevelTwo(
			List<ChainPurchaseStatisReportItemLevelTwo> purchaseStatisLevelTwo) {
		this.purchaseStatisLevelTwo = purchaseStatisLevelTwo;
	}

	public List<ChainPurchaseStatisReportItemLevelThree> getPurchaseStatisLevelThree() {
		return purchaseStatisLevelThree;
	}

	public void setPurchaseStatisLevelThree(
			List<ChainPurchaseStatisReportItemLevelThree> purchaseStatisLevelThree) {
		this.purchaseStatisLevelThree = purchaseStatisLevelThree;
	}

	public List<ChainPurchaseStatisReportItemLevelFour> getPurchaseStatisLevelFour() {
		return purchaseStatisLevelFour;
	}

	public void setPurchaseStatisLevelFour(
			List<ChainPurchaseStatisReportItemLevelFour> purchaseStatisLevelFour) {
		this.purchaseStatisLevelFour = purchaseStatisLevelFour;
	}

	public List<ChainSalesStatisReportItemLevelThree> getSaleStatisLevelThree() {
		return saleStatisLevelThree;
	}

	public void setSaleStatisLevelThree(
			List<ChainSalesStatisReportItemLevelThree> saleStatisLevelThree) {
		this.saleStatisLevelThree = saleStatisLevelThree;
	}

	public List<ChainSalesStatisReportItemLevelFour> getSaleStatisLevelFour() {
		return saleStatisLevelFour;
	}

	public void setSaleStatisLevelFour(
			List<ChainSalesStatisReportItemLevelFour> saleStatisLevelFour) {
		this.saleStatisLevelFour = saleStatisLevelFour;
	}

	public List<ChainSalesStatisReportItemLevelTwo> getSaleStatisLevelTwo() {
		return saleStatisLevelTwo;
	}

	public void setSaleStatisLevelTwo(
			List<ChainSalesStatisReportItemLevelTwo> saleStatisLevelTwo) {
		this.saleStatisLevelTwo = saleStatisLevelTwo;
	}

	public ChainSalesStatisReportItem getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(ChainSalesStatisReportItem totalItem) {
		this.totalItem = totalItem;
	}

	public List<ChainSalesStatisReportItemLevelOne> getSaleStatisLevelOne() {
		return saleStatisLevelOne;
	}

	public void setSaleStatisLevelOne(
			List<ChainSalesStatisReportItemLevelOne> saleStatisLevelOne) {
		this.saleStatisLevelOne = saleStatisLevelOne;
	}

	public List<ChainUserInfor> getChainSalers() {
		return chainSalers;
	}

	public void setChainSalers(List<ChainUserInfor> chainSalers) {
		this.chainSalers = chainSalers;
	}

	public List<Year> getYears() {
		return years;
	}

	public void setYears(List<Year> years) {
		this.years = years;
	}

	public List<Quarter> getQuarters() {
		return quarters;
	}

	public void setQuarters(List<Quarter> quarters) {
		this.quarters = quarters;
	}

	public List<ChainStore> getChainStores() {
		return chainStores;
	}

	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}

	public String getReportTypeS() {
		return reportTypeS;
	}

	public void setReportTypeS(String reportTypeS) {
		this.reportTypeS = reportTypeS;
	}
	
}
