package com.onlineMIS.sorter;

import java.util.Comparator;

import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisticReportItemVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainReportItemVO;

public class ChainStatisticReportItemVOSorter implements
		Comparator<ChainReportItemVO> {

	@Override
	public int compare(ChainReportItemVO o1,
			ChainReportItemVO o2) {
		boolean isChainO1 = o1.getIsChain();
		boolean isChainO2 = o2.getIsChain();
		
		int yearIdO1 = o1.getYearId();
		int yearIdO2 = o2.getYearId();
		
		int quarterIdO1 = o1.getQuarterId();
		int quarterIdO2 = o2.getQuarterId();
		
		String nameO1 = o1.getName();
		String nameO2 = o2.getName();
		
		if (isChainO1 != isChainO2){
			if (isChainO1)
				return 1;
			else 
				return -1;
		} else if (yearIdO1 != yearIdO2){
			return yearIdO1 - yearIdO2;
	    } else if (quarterIdO1 != quarterIdO2){
			return quarterIdO1 - quarterIdO2;
	    } else {
			return nameO1.compareTo(nameO2);
		}
	}

}
