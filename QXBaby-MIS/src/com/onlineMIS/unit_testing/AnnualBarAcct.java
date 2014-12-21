package com.onlineMIS.unit_testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.DAO.chainS.batchRpt.ChainBatchRptService;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStockArchive;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStockCopy;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;

public class AnnualBarAcct {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		List<java.sql.Date> dates = Common_util.getLastWeekDays();
		System.out.println("last week : " + dates.get(0) + dates.get(6));
		//ChainBatchRptService.calculateAccumulatedDates(dates.get(0), dates.get(6));

	}

}

