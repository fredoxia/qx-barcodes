package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.sorter.ChainInventoryOrderProductSorter;

public class ChainInventoryFlowOrderTemplate  extends ExcelTemplate{
	private ChainInventoryFlowOrder order = new ChainInventoryFlowOrder();
	private boolean showCost;
	private int year_column = 0;
	private int quarter_column = 1;
	private int brand_column = 2;
	private int productCode_column = 3;
	private int colour_column = 4;
	private int unit_column = 5;
	private int barcode_column =6;
	private int quantity_column = 7;
	private int totalCost_column = 8;
	private int totalSales_column = 9;
	private int computerQ_column = 10;
	private int qDiff_column = 11;
	private int data_row = 3;


//    public ChainInventoryFlowOrderTemplate(File file) throws IOException{
//    	super(file);
//    }
	
	public ChainInventoryFlowOrderTemplate(ChainInventoryFlowOrder order, String templateWorkbookPath, boolean showCost) throws IOException{
		super(templateWorkbookPath + "\\ChainInventoryFlowOrderTemplate.xls");	
		this.order = order;
		this.showCost = showCost;
	}
	
	/**
	 *  this is the function to inject the inventory order to Jinsuan order template
	 * @return
	 */
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		ChainStore chainStore = order.getChainStore();
		List<ChainInventoryFlowOrderProduct> items = order.getProductList();
		
		Row header2 = sheet.getRow(0);
		header2.createCell(1).setCellValue(order.getTypeChainS());
		header2.createCell(3).setCellValue(order.getId());
		header2.createCell(5).setCellValue(Common_util.dateFormat_f.format(new Date()));
		
		//write header
		Row header1 = sheet.getRow(1);
		header1.createCell(1).setCellValue(chainStore.getChain_name());
		header1.createCell(4).setCellValue(order.getComment());
		
		//write product infmration
		int totalDataRow = items.size();
		int totalQuantity = 0;
		double totalCost = 0;
		double totalSales = 0;
		
		int i = 0;
		for (ChainInventoryFlowOrderProduct levelFourItem : items){
			Row row = sheet.createRow(data_row + i);

			Product product = levelFourItem.getProductBarcode().getProduct();
			
			Year year = product.getYear();
			row.createCell(year_column).setCellValue(year.getYear());
			
			Quarter quarter =  product.getQuarter();
			row.createCell(quarter_column).setCellValue(quarter.getQuarter_Name());
			
			Brand brand = product.getBrand();
			row.createCell(brand_column).setCellValue(brand.getBrand_Name());
			
			row.createCell(productCode_column).setCellValue(product.getProductCode());

			row.createCell(barcode_column).setCellValue(levelFourItem.getProductBarcode().getBarcode());
			
			Color color = levelFourItem.getProductBarcode().getColor();
			if (color == null)
				row.createCell(colour_column).setCellValue("");
			else 
				row.createCell(colour_column).setCellValue(color.getName());
			
			row.createCell(unit_column).setCellValue(product.getUnit());

			row.createCell(quantity_column).setCellValue(levelFourItem.getQuantity());
			
			double cost = 0;
			double sales = 0;
			if (showCost){
				cost = levelFourItem.getQuantity() * ProductBarcodeDaoImpl.getWholeSalePrice(levelFourItem.getProductBarcode());
				row.createCell(totalCost_column).setCellValue(cost);
			}
			
			sales = levelFourItem.getQuantity() * product.getSalesPrice();
			row.createCell(totalSales_column).setCellValue(sales);
				
			row.createCell(computerQ_column).setCellValue(levelFourItem.getInventoryQ());
			row.createCell(qDiff_column).setCellValue(levelFourItem.getQuantityDiff());
						
			totalQuantity += levelFourItem.getQuantity();
			totalCost += cost;
			totalSales += sales;
			i++;
		}
		
		Row row = sheet.createRow(totalDataRow + 3);
		row.createCell(0).setCellValue("合计");
		row.createCell(quantity_column).setCellValue(totalQuantity);
		if (showCost){
		   row.createCell(totalCost_column).setCellValue(totalCost);
		}
		   row.createCell(totalSales_column).setCellValue(totalSales);
		
		return templateWorkbook;
	}
	

}
