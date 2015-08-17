package com.onlineMIS.ORM.entity.chainS.manualRpt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;

public class ChainDailyManualRpt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5939538003439088827L;
	
	private int id;
	private ChainUserInfor creator;
	private ChainStore chainStore;
	private Date rptDate;
	private String weekday;
	private String weather;
	private double temperature;
	private int shouldAttend;
	private int actualAttend;
	private double monthlyTarget;
	private double accumulateMonthlyTarget;
	private double monthlyAchievePercentage;
	private double monthlyDiff;
	private double dailyTarget;
	private double actualDailyTarget;
	private double dailyAchievePercentage;
	private double dailyDiff;	
	private double sameDayLastWeekSales;
	private int rankSameDayLastWeek;
	private String yestordayGoodPoint = "";
	private String yestordayWeakPoint = "";
	private String yestordayEmergency = "";	
	private String todayTarget = "";
	private String mainProductPromotion = "";
	private String operationTarget = "";
	
    private List<ChainDailyManualReportAnalysisItem> analysisItems = new ArrayList<ChainDailyManualReportAnalysisItem>();
    private List<ChainDailyManualReportSalesItem> salesItems = new ArrayList<ChainDailyManualReportSalesItem>();
    private Set<ChainDailyManualReportAnalysisItem> analysisSet = new HashSet<ChainDailyManualReportAnalysisItem>();
    private Set<ChainDailyManualReportSalesItem> salesSet = new HashSet<ChainDailyManualReportSalesItem>();
    
	
	public Set<ChainDailyManualReportSalesItem> getSalesSet() {
		return salesSet;
	}
	public void setSalesSet(Set<ChainDailyManualReportSalesItem> salesSet) {
		this.salesSet = salesSet;
	}
	public Set<ChainDailyManualReportAnalysisItem> getAnalysisSet() {
		return analysisSet;
	}
	public void setAnalysisSet(Set<ChainDailyManualReportAnalysisItem> analysisSet) {
		this.analysisSet = analysisSet;
	}
	public double getMonthlyAchievePercentage() {
		return monthlyAchievePercentage;
	}
	public void setMonthlyAchievePercentage(double monthlyAchievePercentage) {
		this.monthlyAchievePercentage = monthlyAchievePercentage;
	}
	public double getMonthlyDiff() {
		return monthlyDiff;
	}
	public void setMonthlyDiff(double monthlyDiff) {
		this.monthlyDiff = monthlyDiff;
	}
	public double getDailyAchievePercentage() {
		return dailyAchievePercentage;
	}
	public void setDailyAchievePercentage(double dailyAchievePercentage) {
		this.dailyAchievePercentage = dailyAchievePercentage;
	}
	public double getDailyDiff() {
		return dailyDiff;
	}
	public void setDailyDiff(double dailyDiff) {
		this.dailyDiff = dailyDiff;
	}
	public int getRankSameDayLastWeek() {
		return rankSameDayLastWeek;
	}
	public void setRankSameDayLastWeek(int rankSameDayLastWeek) {
		this.rankSameDayLastWeek = rankSameDayLastWeek;
	}
	public double getSameDayLastWeekSales() {
		return sameDayLastWeekSales;
	}
	public void setSameDayLastWeekSales(double sameDayLastWeekSales) {
		this.sameDayLastWeekSales = sameDayLastWeekSales;
	}
	public List<ChainDailyManualReportAnalysisItem> getAnalysisItems() {
		return analysisItems;
	}
	public void setAnalysisItems(
			List<ChainDailyManualReportAnalysisItem> analysisItems) {
		this.analysisItems = analysisItems;
	}
	public List<ChainDailyManualReportSalesItem> getSalesItems() {
		return salesItems;
	}
	public void setSalesItems(List<ChainDailyManualReportSalesItem> salesItems) {
		this.salesItems = salesItems;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChainUserInfor getCreator() {
		return creator;
	}
	public void setCreator(ChainUserInfor creator) {
		this.creator = creator;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public int getShouldAttend() {
		return shouldAttend;
	}
	public void setShouldAttend(int shouldAttend) {
		this.shouldAttend = shouldAttend;
	}
	public int getActualAttend() {
		return actualAttend;
	}
	public void setActualAttend(int actualAttend) {
		this.actualAttend = actualAttend;
	}
	public double getMonthlyTarget() {
		return monthlyTarget;
	}
	public void setMonthlyTarget(double monthlyTarget) {
		this.monthlyTarget = monthlyTarget;
	}
	public double getAccumulateMonthlyTarget() {
		return accumulateMonthlyTarget;
	}
	public void setAccumulateMonthlyTarget(double accumulateMonthlyTarget) {
		this.accumulateMonthlyTarget = accumulateMonthlyTarget;
	}
	public double getDailyTarget() {
		return dailyTarget;
	}
	public void setDailyTarget(double dailyTarget) {
		this.dailyTarget = dailyTarget;
	}
	public double getActualDailyTarget() {
		return actualDailyTarget;
	}
	public void setActualDailyTarget(double actualDailyTarget) {
		this.actualDailyTarget = actualDailyTarget;
	}
	public String getYestordayGoodPoint() {
		return yestordayGoodPoint;
	}
	public void setYestordayGoodPoint(String yestordayGoodPoint) {
		this.yestordayGoodPoint = yestordayGoodPoint;
	}
	public String getYestordayWeakPoint() {
		return yestordayWeakPoint;
	}
	public void setYestordayWeakPoint(String yestordayWeakPoint) {
		this.yestordayWeakPoint = yestordayWeakPoint;
	}
	public String getYestordayEmergency() {
		return yestordayEmergency;
	}
	public void setYestordayEmergency(String yestordayEmergency) {
		this.yestordayEmergency = yestordayEmergency;
	}
	public String getTodayTarget() {
		return todayTarget;
	}
	public void setTodayTarget(String todayTarget) {
		this.todayTarget = todayTarget;
	}
	public String getMainProductPromotion() {
		return mainProductPromotion;
	}
	public void setMainProductPromotion(String mainProductPromotion) {
		this.mainProductPromotion = mainProductPromotion;
	}
	public String getOperationTarget() {
		return operationTarget;
	}
	public void setOperationTarget(String operationTarget) {
		this.operationTarget = operationTarget;
	}
	
	
}
