package com.onlineMIS.ORM.entity.chainS.report;

import java.util.ArrayList;
import java.util.List;

public class ChainFinanceReport  extends ChainReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7800828589678758259L;
    private List<ChainFinanceReportItem> reportItems = new ArrayList<ChainFinanceReportItem>();
	public List<ChainFinanceReportItem> getReportItems() {
		return reportItems;
	}
	public void setReportItems(List<ChainFinanceReportItem> reportItems) {
		this.reportItems = reportItems;
	}
    
}
