package com.onlineMIS.action.chainS.charts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainSalesChartFormBean {
	    private String months = "";
		private Date startDate;
		private Date endDate;
		private ChainStore chainStore;
		private int brandId;
		private int reportYear;

		
		public String getMonths() {
			return months;
		}
		public void setMonths(String months) {
			this.months = months;
		}
		public int getReportYear() {
			return reportYear;
		}
		public void setReportYear(int reportYear) {
			this.reportYear = reportYear;
		}

		public int getBrandId() {
			return brandId;
		}
		public void setBrandId(int brandId) {
			this.brandId = brandId;
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
		public ChainStore getChainStore() {
			return chainStore;
		}
		public void setChainStore(ChainStore chainStore) {
			this.chainStore = chainStore;
		}
		@Override
		public String toString() {
			return "ChainSalesChartFormBean [startDate=" + startDate
					+ ", endDate=" + endDate + ", chainStore=" + chainStore
					+ ", brandId=" + brandId + "]";
		}

		
		
}
