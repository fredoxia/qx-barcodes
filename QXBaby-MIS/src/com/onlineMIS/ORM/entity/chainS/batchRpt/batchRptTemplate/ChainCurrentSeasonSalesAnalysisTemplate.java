package com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainCurrentSeasonSalesAnalysisTemplate extends ExcelTemplate{
	private final static String templateName = "CurrentSeasonSalesAnlysisTemplate.xls";
	public static final int NUM_OF_ITEM = -1;
	
	private ChainCurrentSeasonSalesAnalysisRpt rpt;
	
	private final String header = "销售分析报表";
	private final int DATA_ROW = 3;
	private final int RANK_COL = 0;
	private final int CHANIN_NAME_COL = 1;
	private final int LAST_YEAR_PURCHASE_COL = 2;
	private final int NET_PURCHASE_COL = 3;
	private final int RETURN_RATIO_COL = 4;
	private final int INVENTORY_AMT_COL = 5;
	private final int INVENTORY_RATIO_COL = 6;
	private final int SALES_AMT_COL = 7;
	private final int SALES_RATIO_COL = 8;
	private final int DELIVERY_AMT_COL = 9;
	private final int DELIVERY_RATIO_COL = 10;
	
	private int formulaStart = DATA_ROW + 1;
	private int formulaEnd = DATA_ROW;
	
	public ChainCurrentSeasonSalesAnalysisTemplate(ChainCurrentSeasonSalesAnalysisRpt rpt, String templateWorkbookPath)
			throws IOException {
		super(templateWorkbookPath + "\\" + templateName);
		this.rpt = rpt;
	}
	
	public HSSFWorkbook process(){
		HSSFCellStyle percentageStyle = templateWorkbook.createCellStyle();  
		percentageStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.0%"));  

		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		Year year = rpt.getYear();
		Quarter quarter = rpt.getQuarter();
	
		List<ChainCurrentSeasonSalesAnalysisItem> items = rpt.getRptItems();
		
		//报表头
		Row header1 = sheet.getRow(0);
		String generatedTime = Common_util.dateFormat.format((Common_util.getToday()));
		header1.createCell(0).setCellValue(year.getYear() + "-" + quarter.getQuarter_Name() + " " + header + " (截止  " + rpt.getEndDate() +  ") " + generatedTime);

		//写报表内容
		int totalDataRow = items.size();

		for (int i = 0; i < totalDataRow; i++){
			ChainCurrentSeasonSalesAnalysisItem item = items.get(i);
			
			Row row = sheet.createRow(DATA_ROW + i);
			
			row.createCell(RANK_COL).setCellValue(i+1);
			row.createCell(CHANIN_NAME_COL).setCellValue(item.getChainStore().getChain_name());
			row.createCell(LAST_YEAR_PURCHASE_COL).setCellValue(Common_util.formatDouble(item.getLastYearPurchase(), Common_util.df));
			row.createCell(NET_PURCHASE_COL).setCellValue(Common_util.formatDouble(item.getNetPurchaseAmt(), Common_util.df));
			
			if (item.getReturnRatio() != Common_util.ALL_RECORD){
				Cell cell = row.createCell(RETURN_RATIO_COL);
				
				cell.setCellValue(item.getReturnRatio());
				cell.setCellStyle(percentageStyle);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			}

			
			row.createCell(INVENTORY_AMT_COL).setCellValue(item.getInventoryAmt());
			
			if (item.getInventoryRatio() != Common_util.ALL_RECORD){
				Cell cell = row.createCell(INVENTORY_RATIO_COL);
				
				cell.setCellValue(item.getInventoryRatio());
				cell.setCellStyle(percentageStyle);
			}
			
			row.createCell(SALES_AMT_COL).setCellValue(item.getSalesAmt());
			
			if (item.getSalesRatio() != Common_util.ALL_RECORD){

				Cell cell = row.createCell(SALES_RATIO_COL);
				
				cell.setCellValue(item.getSalesRatio());
				cell.setCellStyle(percentageStyle);
			}

			row.createCell(DELIVERY_AMT_COL).setCellValue(item.getInDeliveryAmt());
			
			if (item.getInDeliveryRatio()!= Common_util.ALL_RECORD){

				Cell cell = row.createCell(DELIVERY_RATIO_COL);
				
				cell.setCellValue(item.getInDeliveryRatio());
				cell.setCellStyle(percentageStyle);
			}
		}	
		
		Row footerRow = sheet.createRow(totalDataRow + DATA_ROW);
		formulaEnd = totalDataRow + DATA_ROW;
		footerRow.createCell(LAST_YEAR_PURCHASE_COL).setCellFormula(writeFormula("C"));
		footerRow.createCell(NET_PURCHASE_COL).setCellFormula(writeFormula("D"));
		footerRow.createCell(RETURN_RATIO_COL).setCellFormula(writeFormula("E"));
		footerRow.createCell(INVENTORY_AMT_COL).setCellFormula(writeFormula("F"));
		footerRow.createCell(INVENTORY_RATIO_COL).setCellFormula(writeFormula("G"));
		footerRow.createCell(SALES_AMT_COL).setCellFormula(writeFormula("H"));
		footerRow.createCell(SALES_RATIO_COL).setCellFormula(writeFormula("I"));
		
		return templateWorkbook;
	}

	private String writeFormula(String column){
		return "AVERAGE("+ column  + formulaStart + ":" + column + formulaEnd + ")";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
