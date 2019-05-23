package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.loggerLocal;

public class ChainPurchaseStatisticsReportTemplate  extends ExcelTemplate{
	private static final String TEMPLATE_NAME = "ChainPurchaseStatisticsReportTemplate.xls";
	private List<ChainPurchaseStatisReportItem> items = new ArrayList<ChainPurchaseStatisReportItem>();
	private ChainPurchaseStatisReportItem totalItem = null;
	private int data_row = 4;
	private final int BARCODE_COLUMN = 0;
	private final int PRODUCT_CODE_COLUMN = 1;
	private final int COLOR_COLUMN = 2;
	private final int BRAND_COLUMN = 3;
	private final int QUARTER_COLUMN = 4;
	private final int CATEGORY_COLUMN =5;
	private final int PURCHASE_Q_COLUMN =6;
	private final int RETURN_Q_COLUMN = 7;
	private final int NET_Q_COLUMN = 8;
	private final int PURCHASE_PRICE_COLUMN = 9;
	private final int PURCHASE_AMOUNT_COLUMN = 10;
	
	private ChainStore chainStore;
	private boolean showCost;
	private Date startDate = new Date();
	private Date endDate = new Date();


    public ChainPurchaseStatisticsReportTemplate(File file) throws IOException{
    	super(file);
    }
	
	public ChainPurchaseStatisticsReportTemplate(List<ChainPurchaseStatisReportItem> items, ChainPurchaseStatisReportItem totalItem, ChainStore chainStore, String templateWorkbookPath, boolean showCost, Date startDate, Date endDate) throws IOException{
		super(templateWorkbookPath + "\\" + TEMPLATE_NAME);	
		this.items = items;
		this.chainStore = chainStore;
		this.showCost = showCost;

		this.startDate = startDate;
		this.endDate = endDate;
		this.totalItem = totalItem;
	}
	
	/**
	 *  this is the function to inject the inventory order to Jinsuan order template
	 * @return
	 */
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//write header
		Row header1 = sheet.getRow(1);
		header1.createCell(1).setCellValue(Common_util.dateFormat.format(startDate));
		header1.createCell(3).setCellValue(Common_util.dateFormat.format(endDate));
		
		Row header2 = sheet.getRow(2);
		header2.createCell(1).setCellValue(chainStore.getChain_name());

		
		//write product infmration
		int totalDataRow = items.size();

		for (int i = 0; i < totalDataRow; i++){

			ChainPurchaseStatisReportItem levelFourItem = items.get(i);
			Row row = sheet.createRow(data_row + i);

			ProductBarcode pb = levelFourItem.getProductBarcode();
			Product product = pb.getProduct();
			
			row.createCell(BARCODE_COLUMN).setCellValue(pb.getBarcode());
			row.createCell(PRODUCT_CODE_COLUMN).setCellValue(product.getProductCode());
			Color color = levelFourItem.getProductBarcode().getColor();
			if (color == null)
				row.createCell(COLOR_COLUMN).setCellValue("");
			else 
				row.createCell(COLOR_COLUMN).setCellValue(color.getName());
			
			row.createCell(BRAND_COLUMN).setCellValue(product.getBrand().getBrand_Name());
			
			row.createCell(QUARTER_COLUMN).setCellValue(product.getYear().getYear() + "-" + product.getQuarter().getQuarter_Name());

			row.createCell(CATEGORY_COLUMN).setCellValue(product.getCategory().getCategory_Name());
			row.createCell(PURCHASE_Q_COLUMN).setCellValue(levelFourItem.getPurchaseQuantity());
			row.createCell(RETURN_Q_COLUMN).setCellValue(levelFourItem.getReturnQuantity());
			row.createCell(NET_Q_COLUMN).setCellValue(levelFourItem.getNetQuantity());

			if (showCost){
				row.createCell(PURCHASE_PRICE_COLUMN).setCellValue(levelFourItem.getAvgPrice());
				row.createCell(PURCHASE_AMOUNT_COLUMN).setCellValue(levelFourItem.getPurchaseTotalAmt());
			}
		}
		
		//把总数放进去
		Row row = sheet.createRow(data_row + totalDataRow);
		row.createCell(BARCODE_COLUMN).setCellValue("总计");
		row.createCell(PURCHASE_Q_COLUMN).setCellValue(totalItem.getPurchaseQuantity());
		row.createCell(RETURN_Q_COLUMN).setCellValue(totalItem.getReturnQuantity());
		row.createCell(NET_Q_COLUMN).setCellValue(totalItem.getNetQuantity());

		if (showCost){
			row.createCell(PURCHASE_PRICE_COLUMN).setCellValue(totalItem.getAvgPrice());
			row.createCell(PURCHASE_AMOUNT_COLUMN).setCellValue(totalItem.getPurchaseTotalAmt());
		}
	
		return templateWorkbook;
	}
	

}
