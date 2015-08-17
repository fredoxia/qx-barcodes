package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainDailySalesAnalysis  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145222745550253258L;
	private ChainStore chainStore; 
    private Date reportDate;
    /**
     * 销售量最大单（饰品，内裤除开）
     */
    private int largestSalesQ;
    /**
     * 连带率 = 总销售量/总的销售小票单数
     */
    private double lianDaiRatio;
    /**
     * 可耻单占比 = 一件销售量单数/总的销售小票单数
     */
    private double keChiOrderRatio;
    /**
     * 客单价 = 总销售额/总销售数量
     */
    private double keDanAvg;
    private java.util.Date createDate;
    private int rank;
    
    public ChainDailySalesAnalysis(){
    	
    }
    
    public ChainDailySalesAnalysis(ChainStore chainStore, Date reportDate){
		super();
		this.chainStore = chainStore;
		this.reportDate = reportDate;
		this.createDate = Common_util.getToday();
    }
    
    public ChainDailySalesAnalysis(ChainStore chainStore, Date reportDate,
			int largestSalesQ, double lianDaiRatio, double keChiOrderRatio,
			double keDanAvg,  int rank) {
    	this.largestSalesQ = largestSalesQ;
    	this.lianDaiRatio = lianDaiRatio;
    	this.keChiOrderRatio = keChiOrderRatio;
    	this.keDanAvg = keDanAvg;
        this.rank = rank;
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

	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public int getLargestSalesQ() {
		return largestSalesQ;
	}

	public void setLargestSalesQ(int largestSalesQ) {
		this.largestSalesQ = largestSalesQ;
	}

	public double getLianDaiRatio() {
		return lianDaiRatio;
	}

	public void setLianDaiRatio(double lianDaiRatio) {
		this.lianDaiRatio = lianDaiRatio;
	}

	public double getKeChiOrderRatio() {
		return keChiOrderRatio;
	}

	public void setKeChiOrderRatio(double keChiOrderRatio) {
		this.keChiOrderRatio = keChiOrderRatio;
	}

	public double getKeDanAvg() {
		return keDanAvg;
	}

	public void setKeDanAvg(double keDanAvg) {
		this.keDanAvg = keDanAvg;
	}
    
    
}
