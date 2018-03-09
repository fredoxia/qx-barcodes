package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesVIPPercentageItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainTransferAcctFlowItem;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainTransferAcctFlowTemplate extends ExcelTemplate {
	private final static String templateName = "template\\ChainTransferAcctFlowTemplate.xls";
	//public final static String RPTOUTPUTPATH = "D:\\QXBaby-MIS\\BatchReport\\TransferAcctFlow\\";
	//public final static String RPTOUTPUTNAME = "调货账目报表.xls";
	
	
	private List<ChainTransferAcctFlowItem> items;
	private Date rptDate = null;
	private String fromToDate = "";
	private int DATA_ROW = 3;
	private int CHAR_COL = 0;
	private int CHAIN_COL = 1;
	private int ACCT_COL = 2;

	private int DATE_ROW = 1;

	public ChainTransferAcctFlowTemplate(List<ChainTransferAcctFlowItem> items, String templateWorkbookPath, Date rptDate, String fromToDate) throws IOException{
		super(templateWorkbookPath + "\\" + templateName);
		this.items = items;
		this.rptDate = rptDate;
		this.fromToDate = fromToDate;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//写日期
		Row header1 = sheet.getRow(DATE_ROW);
		header1.createCell(ACCT_COL).setCellValue(fromToDate);

		int i = 0;
		Row dataRow = null;
		for (ChainTransferAcctFlowItem item : items){
			dataRow = sheet.createRow(DATA_ROW + i);
			
			String chainStore = item.getChainStoreName();
			
			dataRow.createCell(CHAIN_COL).setCellValue(chainStore);
			dataRow.createCell(ACCT_COL).setCellValue(item.getTransferAcctFlow());
			
			i++;
		}
		
		return templateWorkbook;
	}
	
//	public static String getFileName(Date rptDate){
//		return Common_util.dateFormat.format(rptDate) + rptRepository;
//	}
//
//	public static String getFilePath(Date rptDate) {
//		return ChainTransferAcctFlowTemplate.RPTOUTPUTPATH + getFileName(rptDate);
//	}

}
