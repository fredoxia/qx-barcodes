package com.onlineMIS.ORM.entity.chainS.report;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

/**
 * 
 * @author Administrator
 *
 */
public class ChainWMRank {
	private boolean isWeekly = false;
	private Date startDate;
	private Date endDate;
	private ChainStore chainStore;
	private int rank;
	private String saleQ;
	private String saleAmt;
	private int totalRank;

	
	public boolean isWeekly() {
		return isWeekly;
	}
	public void setWeekly(boolean isWeekly) {
		this.isWeekly = isWeekly;
	}
	public int getTotalRank() {
		return totalRank;
	}
	public void setTotalRank(int totalRank) {
		this.totalRank = totalRank;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getSaleQ() {
		return saleQ;
	}
	public void setSaleQ(String saleQ) {
		this.saleQ = saleQ;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
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
}
