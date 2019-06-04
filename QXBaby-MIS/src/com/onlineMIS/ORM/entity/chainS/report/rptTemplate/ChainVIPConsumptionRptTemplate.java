package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.report.ChainSalesVIPPercentageItem;
import com.onlineMIS.ORM.entity.chainS.report.VIPReportVO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainVIPConsumptionRptTemplate extends ExcelTemplate {
	private final static String templateName = "VIPConsumptionTemplate.xls";

	
	private List<VIPReportVO> items;
	private Date fromDate = null;
	private Date toDate = null;
	private int DATE_ROW = 1;
	private int DATA_ROW = 3;
	private int CARD_NUM_COL = 0;
	private int VIP_CATE_COL = 1;
	private int ISSUE_CHAIN_COL = 2;
	private int NAME_COL = 3;
	private int SALES_Q_COL = 4;
	private int RETURN_Q_COL = 5;
	private int NET_Q_COL = 6;
	private int SALES_COL = 7;
	private int RETURN_COL = 8;
	private int NET_COL = 9;
	private int PAY_COL = 10;
	private int DISCOUNT_COL = 11;
	private int COUPON_COL = 12;
	private int PREPAID_COL = 13;
	private int PURCHASE_COUNT_COL = 14;
	

	public ChainVIPConsumptionRptTemplate(List<VIPReportVO> items, String templateWorkbookPath, Date fromDate, Date toDate) throws IOException{
		super(templateWorkbookPath + "\\" + templateName);
		this.items = items;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//写日期
		Row header1 = sheet.getRow(DATE_ROW);
		header1.createCell(1).setCellValue(Common_util.dateFormat.format(fromDate));
		header1.createCell(3).setCellValue(Common_util.dateFormat.format(toDate));

		int i = 0;
		Row dataRow = null;
		for (VIPReportVO item : items){
			dataRow = sheet.createRow(DATA_ROW + i);
			
			dataRow.createCell(CARD_NUM_COL).setCellValue(item.getVip().getVipCardNo());
			dataRow.createCell(VIP_CATE_COL).setCellValue(item.getVip().getVipType().getVipTypeName());
			dataRow.createCell(ISSUE_CHAIN_COL).setCellValue(item.getVip().getIssueChainStore().getChain_name());
			dataRow.createCell(NAME_COL).setCellValue(item.getVip().getCustomerName());
			dataRow.createCell(SALES_Q_COL).setCellValue(item.getSaleQ());
			dataRow.createCell(RETURN_Q_COL).setCellValue(item.getReturnQ());
			dataRow.createCell(NET_Q_COL).setCellValue(item.getNetQ());
			dataRow.createCell(SALES_COL).setCellValue(item.getSalesAmt());
			dataRow.createCell(RETURN_COL).setCellValue(item.getReturnAmt());
			dataRow.createCell(NET_COL).setCellValue(item.getNetAmt());
			dataRow.createCell(PAY_COL).setCellValue(item.getReceiveAmt());
			dataRow.createCell(DISCOUNT_COL).setCellValue(item.getDiscountAmt());
			dataRow.createCell(COUPON_COL).setCellValue(item.getCouponSum());
			dataRow.createCell(PREPAID_COL).setCellValue(item.getPrepaidAmt());
			dataRow.createCell(PURCHASE_COUNT_COL).setCellValue(item.getConsumpCount());
		
			i++;
		}
		
		return templateWorkbook;
	}


}
