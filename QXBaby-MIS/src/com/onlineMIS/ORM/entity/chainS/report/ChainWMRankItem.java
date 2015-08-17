package com.onlineMIS.ORM.entity.chainS.report;

import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainWMRankItem {
	private ChainStore chainStore;
	private int rank;
	private String saleQ;
	private String saleAmt;
	private int totalRank;
	
	

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
	
}
