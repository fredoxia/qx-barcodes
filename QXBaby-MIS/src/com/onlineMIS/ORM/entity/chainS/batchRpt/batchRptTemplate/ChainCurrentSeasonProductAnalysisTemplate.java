package com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainCurrentSeasonProductAnalysisTemplate extends ExcelTemplate{
	private final static String templateName = "CurrentSeasonProductAnlysisTemplate.xls";
	public static final int NUM_OF_ITEM = -1;
	
	private ChainCurrentSeasonProductAnalysisRpt rpt;
	
	private final String header = "货品款式分析报表";
	private final int DATA_ROW = 3;
	private final int RANK_COL = 0;
	private final int BARCODE_COL = 1;
	private final int BRAND_COL = 2;
	private final int PRODUCT_CODE_COL = 3;
	private final int COLOR_COL = 4;
	private final int PRODUCER_COL = 5;
	private final int CATEGORY_COL = 6;
	private final int MARKET_DATE_COL = 7;
	private final int WEEK_PURCHASE_COL = 8;
	private final int ACCUMULATED_PURCHASE_COL = 9;
	private final int WEEK_SALES_COL = 10;
	private final int ACCUMULATED_SALES_COL = 11;
	private final int WEEK_DIGEST_COL = 12;
	private final int ACCUMULATED_DIGEST_COL = 13;
	private final int INVENTORY_COL = 14;
	private final int IN_DELIVERY_COL = 15;
	
	public ChainCurrentSeasonProductAnalysisTemplate(ChainCurrentSeasonProductAnalysisRpt rpt, String templateWorkbookPath)
			throws IOException {
		super(templateWorkbookPath + "\\" + templateName);
		this.rpt = rpt;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		Year year = rpt.getYear();
		Quarter quarter = rpt.getQuarter();
		List<ChainCurrentSeasonProductAnalysisItem> items = rpt.getRptItems();
		
		//报表头
		Row header1 = sheet.getRow(0);
		String generatedTime = Common_util.dateFormat.format((Common_util.getToday()));
		header1.createCell(0).setCellValue(year.getYear() + "-" + quarter.getQuarter_Name() + " " + header + " (" + rpt.getRptDate() + " 至  " + rpt.getEndDate() +  ") " + generatedTime);

		
		//写报表内容
		int totalDataRow = items.size();

		for (int i = 0; i < totalDataRow; i++){
			ChainCurrentSeasonProductAnalysisItem item = items.get(i);
			Row row = sheet.createRow(DATA_ROW + i);

			ProductBarcode pb = item.getPb();
			Product product = pb.getProduct();
			
			row.createCell(RANK_COL).setCellValue(i+1);
			row.createCell(BARCODE_COL).setCellValue(pb.getBarcode());
			row.createCell(BRAND_COL).setCellValue(product.getBrand().getBrand_Name());
			row.createCell(PRODUCT_CODE_COL).setCellValue(product.getProductCode());
			
			if (pb.getColor() == null)
				row.createCell(COLOR_COL).setCellValue("");
			else 
				row.createCell(COLOR_COL).setCellValue(pb.getColor().getName());
			
			ChainStore store = pb.getChainStore();
			if (store != null){
				String chainStoreName = store.getChain_name();
				if (chainStoreName.length() >=4)
					row.createCell(PRODUCER_COL).setCellValue(chainStoreName.substring(0, 4));
				else 
					row.createCell(PRODUCER_COL).setCellValue(chainStoreName);
			}
				
			
			row.createCell(CATEGORY_COL).setCellValue(product.getCategory().getCategory_Name());
			
			if (item.getMarketDate() == null)
				row.createCell(MARKET_DATE_COL).setCellValue("-");
			else 
				row.createCell(MARKET_DATE_COL).setCellValue(Common_util.dateFormat.format(item.getMarketDate()));
			
			row.createCell(WEEK_PURCHASE_COL).setCellValue(item.getPurchaseWeekly());
			row.createCell(ACCUMULATED_PURCHASE_COL).setCellValue(item.getPurchaseAccumulated());
			row.createCell(WEEK_SALES_COL).setCellValue(item.getSalesWeekly());
			row.createCell(ACCUMULATED_SALES_COL).setCellValue(item.getSalesAccumulated());
			row.createCell(WEEK_DIGEST_COL).setCellValue(item.getDigestRatioWeekly());
			row.createCell(ACCUMULATED_DIGEST_COL).setCellValue(item.getDigestRatioAccumulated());
			row.createCell(INVENTORY_COL).setCellValue(item.getCurrentInentory());
			row.createCell(IN_DELIVERY_COL).setCellValue(item.getQuantityInDelivery());
		}
		
		return templateWorkbook;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
