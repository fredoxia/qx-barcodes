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

import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItem;
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
	/**
	 * purchase statistic report
	 */
	private ChainPurchaseStatisReportItem purchaseTotalItem = new ChainPurchaseStatisReportItem();

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

	public ChainSalesStatisReportItem getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(ChainSalesStatisReportItem totalItem) {
		this.totalItem = totalItem;
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
