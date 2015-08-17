package com.onlineMIS.ORM.entity.headQ.chain;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javassist.expr.NewArray;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.ExcelTemplate;

public class ChainSalesReportTemplate extends ExcelTemplate {

	private int CUSTOMER_NAME_ROW = 0;
	private int DATE_ROW = 1;
	private int HEAD_DATA_COL = 1;
	private int BRAND_COLUMN = 0;
	private int BRAND_NAME_COLUMN = 1;
	private int PRODUCT_CODE_COLUMN = 0;
	private int PRODUCT_TYPE_COLUMN = 1;
	private int BARCODE_COLUMN = 2;
	private int NUM_PER_HAND_COLUMN = 3;
	private int QUANTITY_IN_COLUMN = 4;
	private int WHOLE_SALES_PRICE_COLUMN = 5;
	private int WHOLE_REVENUE_COLUMN = 6;
	private int SALES_PRICE_COLUMN = 7;
	private int PRODUCT_REVENUE_COLUMN = 8;
	private int BRAND_SUM = 0;
	private int BRAND_Q = 4;
	private int BRAND_WR = 6;
	private int BRAND_R = 8;
	private int DATA_START = 2;
	private SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");

	
	
	private ChainSalesReportBean reportBean;
	
	public ChainSalesReportTemplate(ChainSalesReportBean reportBean ,String templateWorkbookPath)
			throws IOException {
		super(templateWorkbookPath);
		this.reportBean = reportBean;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		Row row = null;
		row =sheet.createRow(CUSTOMER_NAME_ROW);
		row.createCell(HEAD_DATA_COL).setCellValue(reportBean.getCustomerName());
		
		row = sheet.createRow(DATE_ROW);
		row.createCell(HEAD_DATA_COL).setCellValue(dateformat.format(reportBean.getStartDate()) + " to " + dateformat.format(reportBean.getEndDate()));
		int dataStart = DATA_START;
		
		for (ChainSalesReportBrandBean brandItem: reportBean.getBrandItems()){
			row =sheet.createRow(dataStart++);
			Cell cell_a = row.createCell(BRAND_COLUMN);
			  cell_a.setCellStyle(highLigntStyle);
			  cell_a.setCellValue("品牌");
			Cell cell_b = row.createCell(BRAND_NAME_COLUMN);
			  cell_b.setCellValue(brandItem.getBrand());
			  cell_b.setCellStyle(highLigntStyle);
			
			row =sheet.createRow(dataStart++);
			writeBrandHead(row);
			
			for (ChainSalesReportResultItem item: brandItem.getItems()){

				row =sheet.createRow(dataStart++);
				Product product = item.getProductBarcode().getProduct();
				row.createCell(PRODUCT_CODE_COLUMN).setCellValue(product.getProductCode());
				row.createCell(PRODUCT_TYPE_COLUMN).setCellValue(product.getCategory().getCategory_Name());
				row.createCell(BARCODE_COLUMN).setCellValue(item.getProductBarcode().getBarcode());
				row.createCell(NUM_PER_HAND_COLUMN).setCellValue(product.getNumPerHand());
				row.createCell(QUANTITY_IN_COLUMN).setCellValue(item.getQuantity());
				row.createCell(WHOLE_SALES_PRICE_COLUMN).setCellValue(product.getWholeSalePrice());
				row.createCell(WHOLE_REVENUE_COLUMN).setCellValue(item.getWholeRevenue());
				row.createCell(SALES_PRICE_COLUMN).setCellValue(product.getSalesPrice());
				row.createCell(PRODUCT_REVENUE_COLUMN).setCellValue(item.getSalesRevenue());
			}
			row =sheet.createRow(dataStart++);
			Cell cell1 = row.createCell(BRAND_SUM);
			     cell1.setCellValue("单品牌汇总");
			     cell1.setCellStyle(highLigntStyle);
			Cell cell2 = row.createCell(BRAND_Q);
			     cell2.setCellValue(brandItem.getTotalQ());
			     cell2.setCellStyle(highLigntStyle);
			Cell cell3 = row.createCell(BRAND_WR);
				 cell3.setCellValue(brandItem.getTotalWholeSaleRevenue());		
				 cell3.setCellStyle(highLigntStyle);			  
			Cell cell4 = row.createCell(BRAND_R);
			     cell4.setCellValue(brandItem.getTotalRevenue());		
			     cell4.setCellStyle(highLigntStyle);
			dataStart++;
		}
		row =sheet.createRow(dataStart++);
		Cell cell1 = row.createCell(BRAND_SUM);
		     cell1.setCellValue("所有品牌汇总");
		     cell1.setCellStyle(highLigntStyle);
		Cell cell2 = row.createCell(BRAND_Q);
		     cell2.setCellValue(reportBean.getTotalQ());
		     cell2.setCellStyle(highLigntStyle);
		Cell cell3 = row.createCell(BRAND_WR);
		     cell3.setCellValue(reportBean.getTotalWholeRevenue());	
		     cell3.setCellStyle(highLigntStyle);
		Cell cell4 = row.createCell(BRAND_R);
			 cell4.setCellValue(reportBean.getTotalRevenue());	
			 cell4.setCellStyle(highLigntStyle);		
		return templateWorkbook;
	}
	
	private void writeBrandHead(Row row ){
		Cell cell1 = row.createCell(PRODUCT_CODE_COLUMN);
			 cell1.setCellValue("货号");
			 cell1.setCellStyle(highLigntStyle);
		Cell cell2 = row.createCell(PRODUCT_TYPE_COLUMN);
			 cell2.setCellValue("货品类别");
			 cell2.setCellStyle(highLigntStyle);
		Cell cell3 = row.createCell(BARCODE_COLUMN);
			 cell3.setCellValue("条码");
			 cell3.setCellStyle(highLigntStyle);
		Cell cell4 = row.createCell(NUM_PER_HAND_COLUMN);
		     cell4.setCellValue("每手数量");
		     cell4.setCellStyle(highLigntStyle);
		Cell cell5 = row.createCell(QUANTITY_IN_COLUMN);
		     cell5.setCellValue("配货数量");
		     cell5.setCellStyle(highLigntStyle);
		Cell cell6 = row.createCell(WHOLE_SALES_PRICE_COLUMN);
			 cell6.setCellValue("批发价格");
			 cell6.setCellStyle(highLigntStyle);
		Cell cell7 = row.createCell(WHOLE_REVENUE_COLUMN);
			 cell7.setCellValue("批发总价");
			 cell7.setCellStyle(highLigntStyle);
        Cell cell8 = row.createCell(SALES_PRICE_COLUMN);
		     cell8.setCellValue("零售价格");
		     cell8.setCellStyle(highLigntStyle);
		Cell cell9 = row.createCell(PRODUCT_REVENUE_COLUMN);
		     cell9.setCellValue("零售总价");
		     cell9.setCellStyle(highLigntStyle);
	}
}
