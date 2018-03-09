package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;


import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.report.ChainSalesReport;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;


public class ChainSalesReportTemplate extends ExcelTemplate{
	private final int date_row_ind = 1;
	private final int data_row_ind = 3;
	
	private final int chain_column = 0;
	private final int sales_q_column= 1;
	private final int return_q_column= 2;
	private final int net_sales_q_column= 3;
	private final int free_q_column= 4;
	private final int sales_amt_column= 5;
	private final int return_amt_column = 6;
	private final int net_amt_column= 7;
	private final int net_cost_column = 8;
	private final int free_cost_column = 9;
	private final int profit_column= 10;
	private final int discount_column = 11;
	private final int sales_discount_column= 12;
	private final int coupon_column = 13;
	private final int card_amt_column = 14;
	private final int cash_amt_column= 15;
	private final int alipay_amt_column = 16;
	private final int wechat_amtr_column = 17;
	private final int qx_net_amt_column = 18;
	private final int qx_net_amt_lastYear_column = 19;
	
	private List<ChainSalesReport> salesRptEle = new ArrayList<ChainSalesReport>();
	private Map<Integer, Double> lastYearMap = null;
	private ChainSalesReport salesRptFooter = null;
	private Date startDate;
	private Date endDate;

	public ChainSalesReportTemplate(List<ChainSalesReport> salesRptEle, Map lastYearMap, ChainSalesReport salesRptFooter, Date startDate, Date endDate, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);		
		this.salesRptEle = salesRptEle;
		this.salesRptFooter = salesRptFooter;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastYearMap = lastYearMap;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
        
		//write header information
		String date = Common_util.dateFormat.format(startDate) + " 至 " + Common_util.dateFormat.format(endDate);
		Row headerRow1 = sheet.getRow(date_row_ind);
		headerRow1.createCell(1).setCellValue(date);
		
		for (int i = 0; i < salesRptEle.size(); i++){
			ChainSalesReport saleReport = salesRptEle.get(i);
			Row row = sheet.createRow(i + data_row_ind);
			row.createCell(chain_column).setCellValue(saleReport.getChainStore().getChain_name());
			row.createCell(sales_q_column).setCellValue(saleReport.getSaleQuantitySum());
			row.createCell(return_q_column).setCellValue(saleReport.getReturnQuantitySum());
			row.createCell(net_sales_q_column).setCellValue(saleReport.getNetQuantitySum());
			row.createCell(free_q_column).setCellValue(saleReport.getFreeQantitySum());
			row.createCell(sales_amt_column).setCellValue(saleReport.getSalesAmtSum());
			row.createCell(return_amt_column).setCellValue(saleReport.getReturnAmtSum());
			row.createCell(net_amt_column).setCellValue(saleReport.getNetAmtSum());
			row.createCell(net_cost_column).setCellValue(saleReport.getNetSaleCostSum());
			row.createCell(free_cost_column).setCellValue(saleReport.getFreeCostSum());
			row.createCell(profit_column).setCellValue(saleReport.getNetProfit());
			row.createCell(discount_column).setCellValue(saleReport.getDiscountSum());
			row.createCell(sales_discount_column).setCellValue(saleReport.getSalesDiscountAmtSum());
			row.createCell(coupon_column).setCellValue(saleReport.getCouponSum());
			row.createCell(card_amt_column).setCellValue(saleReport.getCardAmtSum());
			row.createCell(cash_amt_column).setCellValue(saleReport.getCashNetSum());

			row.createCell(alipay_amt_column).setCellValue(saleReport.getAlipaySum());
			row.createCell(wechat_amtr_column).setCellValue(saleReport.getWechatAmtSum());
			
			row.createCell(qx_net_amt_column).setCellValue(saleReport.getQxAmount());
			
			int chainId = saleReport.getChainStore().getChain_id();
			Double lastYearData = lastYearMap.get(chainId);
			
			if (lastYearData == null)
			   row.createCell(qx_net_amt_lastYear_column).setCellValue("-");
			else 
			   row.createCell(qx_net_amt_lastYear_column).setCellValue(lastYearData);
		}
		
		if (salesRptFooter != null){
			Row row = sheet.createRow(salesRptEle.size() + data_row_ind);
			ChainSalesReport saleReport = salesRptFooter;
			row.createCell(chain_column).setCellValue("- 总计 -");
			row.createCell(sales_q_column).setCellValue(saleReport.getSaleQuantitySum());
			row.createCell(return_q_column).setCellValue(saleReport.getReturnQuantitySum());
			row.createCell(net_sales_q_column).setCellValue(saleReport.getNetQuantitySum());
			row.createCell(free_q_column).setCellValue(saleReport.getFreeQantitySum());
			row.createCell(sales_amt_column).setCellValue(saleReport.getSalesAmtSum());
			row.createCell(return_amt_column).setCellValue(saleReport.getReturnAmtSum());
			row.createCell(net_amt_column).setCellValue(saleReport.getNetAmtSum());
			row.createCell(net_cost_column).setCellValue(saleReport.getNetSaleCostSum());
			row.createCell(free_cost_column).setCellValue(saleReport.getFreeCostSum());
			row.createCell(profit_column).setCellValue(saleReport.getNetProfit());
			row.createCell(discount_column).setCellValue(saleReport.getDiscountSum());
			row.createCell(sales_discount_column).setCellValue(saleReport.getSalesDiscountAmtSum());
			row.createCell(coupon_column).setCellValue(saleReport.getCouponSum());
			row.createCell(card_amt_column).setCellValue(saleReport.getCardAmtSum());
			row.createCell(cash_amt_column).setCellValue(saleReport.getCashNetSum());
			
			row.createCell(alipay_amt_column).setCellValue(saleReport.getAlipaySum());
			row.createCell(wechat_amtr_column).setCellValue(saleReport.getWechatAmtSum());
			
			row.createCell(qx_net_amt_column).setCellValue(saleReport.getQxAmount());
			Double lastYearData = lastYearMap.get(Common_util.ALL_RECORD);
			
			if (lastYearData == null)
			   row.createCell(qx_net_amt_lastYear_column).setCellValue("-");
			else 
			   row.createCell(qx_net_amt_lastYear_column).setCellValue(lastYearData);
		}

		return templateWorkbook;
	}

}
