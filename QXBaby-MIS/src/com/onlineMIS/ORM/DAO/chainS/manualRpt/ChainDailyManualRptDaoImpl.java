package com.onlineMIS.ORM.DAO.chainS.manualRpt;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualReportAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualReportSalesItem;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRpt;
import com.onlineMIS.ORM.entity.chainS.report.ChainDailySalesAnalysis;

@Repository
public class ChainDailyManualRptDaoImpl extends BaseDAO<ChainDailyManualRpt> {
	
	public void save(ChainDailyManualRpt rpt, boolean cached){
		if (cached)
			getHibernateTemplate().setCacheQueries(true);
		
		List<ChainDailyManualReportAnalysisItem> analysisList = rpt.getAnalysisItems();
		if (analysisList != null){
			for (ChainDailyManualReportAnalysisItem item : analysisList){
				item.setChainDailyManualRpt(rpt);
			}
		}
		
		List<ChainDailyManualReportSalesItem> salesList = rpt.getSalesItems();
		if (salesList != null){
			for (ChainDailyManualReportSalesItem item : salesList){
				item.setChainDailyManualRpt(rpt);
			}
		}
		
		getHibernateTemplate().save(rpt);
	}
}
