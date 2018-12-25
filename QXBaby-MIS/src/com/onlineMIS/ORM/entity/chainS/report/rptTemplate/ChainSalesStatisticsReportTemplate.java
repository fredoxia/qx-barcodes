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

public class ChainSalesStatisticsReportTemplate  extends ExcelTemplate{
	private List<ChainSalesStatisReportItem> items = new ArrayList<ChainSalesStatisReportItem>();
	private ChainSalesStatisReportItem totalItem = null;
	private int data_row = 5;
	private final int BARCODE_COLUMN = 0;
	private final int PRODUCT_CODE_COLUMN = 1;
	private final int COLOR_COLUMN = 2;
	private final int BRAND_COLUMN = 3;
	private final int QUARTER_COLUMN = 4;
	private final int CATEGORY_COLUMN =5;
	private final int SALE_Q_COLUMN =6;
	private final int RETURN_Q_COLUMN = 7;
	private final int NET_Q_COLUMN = 8;
	private final int FREE_Q_COLUMN = 9;
	private final int SALES_COLUMN = 10;
	private final int RETURN_COLUMN = 11;
	private final int NET_COLUMN = 12;
	private final int NET_COST_COLUMN = 13;
	private final int FREE_COST_COLUMN = 14;
	private final int PROFIT_COLUMN = 15;
	private final int SALES_DISCOUNT_COLUMN = 16;
	
	
	private ChainStore chainStore;
	private boolean showCost;
	private Date startDate = new Date();
	private Date endDate = new Date();
	private ChainUserInfor saler = new ChainUserInfor();
	private int salesQ = 0;
	private int returnQ = 0;
	private int netQ = 0;
	private int freeQ = 0;
	//销售额
	private double salesPrice = 0;
	//退货额
	private double returnPrice = 0;
	//净销售额
	private double netPrice =0;
	//销售折扣
	private double salesDiscount = 0;
	//销售成本
	private double salesCost = 0;
	//退货成本
	private double returnCost = 0;
	//净销售成本
	private double netCost = 0;
	//赠品成本
	private double freeCost = 0;
	//净利润
	private double netProfit = 0;


    public ChainSalesStatisticsReportTemplate(File file) throws IOException{
    	super(file);
    }
	
	public ChainSalesStatisticsReportTemplate(List<ChainSalesStatisReportItem> items, ChainSalesStatisReportItem totalItem, ChainStore chainStore, String templateWorkbookPath, boolean showCost, ChainUserInfor saler, Date startDate, Date endDate) throws IOException{
		super(templateWorkbookPath);	
		this.items = items;
		this.chainStore = chainStore;
		this.showCost = showCost;
		this.saler = saler;
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
		
		if (saler != null){
			Row header3 = sheet.getRow(3);
			header3.createCell(1).setCellValue(saler.getName());
		}
		
		//write product infmration
		int totalDataRow = items.size();

		for (int i = 0; i < totalDataRow; i++){

			ChainSalesStatisReportItem levelFourItem = items.get(i);
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
			row.createCell(SALE_Q_COLUMN).setCellValue(levelFourItem.getSalesQ());
			row.createCell(RETURN_Q_COLUMN).setCellValue(levelFourItem.getReturnQ());
			row.createCell(NET_Q_COLUMN).setCellValue(levelFourItem.getNetQ());
			row.createCell(FREE_Q_COLUMN).setCellValue(levelFourItem.getFreeQ());
			row.createCell(SALES_COLUMN).setCellValue(levelFourItem.getSalesPrice());
			row.createCell(RETURN_COLUMN).setCellValue(levelFourItem.getReturnPrice());
			row.createCell(NET_COLUMN).setCellValue(levelFourItem.getNetPrice());
			row.createCell(SALES_DISCOUNT_COLUMN).setCellValue(levelFourItem.getSalesDiscount());
			
			if (showCost){
				row.createCell(NET_COST_COLUMN).setCellValue(levelFourItem.getNetCost());
				row.createCell(FREE_COST_COLUMN).setCellValue(levelFourItem.getFreeCost());
				row.createCell(PROFIT_COLUMN).setCellValue(levelFourItem.getNetProfit());
			}
		}
		
		//把总数放进去
		Row row = sheet.createRow(data_row + totalDataRow);
		row.createCell(BARCODE_COLUMN).setCellValue("总计");
		row.createCell(SALE_Q_COLUMN).setCellValue(totalItem.getSalesQ());
		row.createCell(RETURN_Q_COLUMN).setCellValue(totalItem.getReturnQ());
		row.createCell(NET_Q_COLUMN).setCellValue(totalItem.getNetQ());
		row.createCell(FREE_Q_COLUMN).setCellValue(totalItem.getFreeQ());
		row.createCell(SALES_COLUMN).setCellValue(totalItem.getSalesPrice());
		row.createCell(RETURN_COLUMN).setCellValue(totalItem.getReturnPrice());
		row.createCell(NET_COLUMN).setCellValue(totalItem.getNetPrice());
		row.createCell(SALES_DISCOUNT_COLUMN).setCellValue(totalItem.getSalesDiscount());
		
		if (showCost){
			row.createCell(NET_COST_COLUMN).setCellValue(totalItem.getNetCost());
			row.createCell(FREE_COST_COLUMN).setCellValue(totalItem.getFreeCost());
			row.createCell(PROFIT_COLUMN).setCellValue(totalItem.getNetProfit());
		}
	
		return templateWorkbook;
	}
	

}
