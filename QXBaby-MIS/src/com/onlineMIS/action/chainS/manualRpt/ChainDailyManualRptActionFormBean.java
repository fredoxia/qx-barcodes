package com.onlineMIS.action.chainS.manualRpt;

import java.sql.Date;

import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRpt;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainDailyManualRptActionFormBean {
	private ChainStore chainStore = new ChainStore();
	private Date rptDate = Common_util.getToday();
	private ChainDailyManualRpt rpt = new ChainDailyManualRpt();
	private Integer[] salerSize = {0,1,2,3};
	private Integer[] brandSize = {0,1,2,3,4};

	public Integer[] getBrandSize() {
		return brandSize;
	}
	public void setBrandSize(Integer[] brandSize) {
		this.brandSize = brandSize;
	}
	public Integer[] getSalerSize() {
		return salerSize;
	}
	public void setSalerSize(Integer[] salerSize) {
		this.salerSize = salerSize;
	}
	public ChainDailyManualRpt getRpt() {
		return rpt;
	}
	public void setRpt(ChainDailyManualRpt rpt) {
		this.rpt = rpt;
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
	
	
}
