package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.report.ChainSalesVIPPercentageItem;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainSalesReportVIPPercentageTemplate extends ExcelTemplate {
	private final static String templateName = "template\\ChainSalesReportForVIPTemplate.xls";
	public final static String RPTOUTPUTPATH = "D:\\QXBaby-MIS\\BatchReport\\DailyVIPSalesAnalysis\\";
	public final static String RPTOUTPUTNAME = "VIP.xls";
	
	private List<ChainSalesVIPPercentageItem> items;
	private Date rptDate = null;
	private int DATA_ROW = 3;
	private int CHAIN_COL = 0;
	private int NET_SALES_COL = 1;
	private int VIP1_SALES_COL = 2;
	private int VIP1_SALES_PER = 3;
	private int VIP2_SALES_COL = 4;
	private int VIP2_SALES_PER = 5;
	private int VIP3_SALES_COL = 6;
	private int VIP3_SALES_PER = 7;
	private int DATE_ROW = 1;

	public ChainSalesReportVIPPercentageTemplate(List<ChainSalesVIPPercentageItem> items, String templateWorkbookPath, Date rptDate) throws IOException{
		super(templateWorkbookPath + "\\" + templateName);
		this.items = items;
		this.rptDate = rptDate;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//写日期
		Row header1 = sheet.getRow(DATE_ROW);
		header1.createCell(NET_SALES_COL).setCellValue(Common_util.dateFormat.format(rptDate));

		int i = 0;
		Row dataRow = null;
		for (ChainSalesVIPPercentageItem item : items){
			dataRow = sheet.createRow(DATA_ROW + i);
			
			dataRow.createCell(CHAIN_COL).setCellValue(item.getChainStore().getChain_name());
			dataRow.createCell(NET_SALES_COL).setCellValue(item.getNetSales());
			
			if (item.getVip1NetSales() != 0)
				dataRow.createCell(VIP1_SALES_COL).setCellValue(item.getVip1NetSales());
			if (item.getVip1NetSalesPercentage() > 0)
				dataRow.createCell(VIP1_SALES_PER).setCellValue(item.getVip1NetSalesPercentage());
			
			if (item.getVip2NetSales() != 0)
				dataRow.createCell(VIP2_SALES_COL).setCellValue(item.getVip2NetSales());
			if (item.getVip2NetSalesPercentage() > 0)
				dataRow.createCell(VIP2_SALES_PER).setCellValue(item.getVip2NetSalesPercentage());
			
			if (item.getVip3NetSales() != 0)
				dataRow.createCell(VIP3_SALES_COL).setCellValue(item.getVip3NetSales());
			if (item.getVip3NetSalesPercentage() > 0)
				dataRow.createCell(VIP3_SALES_PER).setCellValue(item.getVip3NetSalesPercentage());
			
			i++;
		}
		
		return templateWorkbook;
	}
	
	public static String getFileName(Date rptDate){
		return Common_util.dateFormat.format(rptDate) + ChainSalesReportVIPPercentageTemplate.RPTOUTPUTNAME;
	}

	public static String getFilePath(Date rptDate) {
		return ChainSalesReportVIPPercentageTemplate.RPTOUTPUTPATH + getFileName(rptDate);
	}

}
