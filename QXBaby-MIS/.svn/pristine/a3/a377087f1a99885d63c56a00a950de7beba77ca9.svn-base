package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.ExcelUtil;
import com.onlineMIS.common.loggerLocal;

public class JinSuanOrderTemplate  extends ExcelTemplate{
	private InventoryOrder order = new InventoryOrder();
	private int serial_column = 0;
	private int barcode_column = 1;
	private int productCode_column = 2;
	private int unit_column = 4;
	private int quantity_column = 5;
	private int salePrice_column = 6;
	private int discount_column = 8;
	private int wholePrice_column = 9;
	private int productComment_column = 14;
	private int data_row = 1;
	
	public InventoryOrder getOrder() {
		return order;
	}
	public void setOrder(InventoryOrder order) {
		this.order = order;
	}

    public JinSuanOrderTemplate(File file) throws IOException{
    	super(file);
    }
	
	public JinSuanOrderTemplate(InventoryOrder order, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);	
		this.order = order;
	}
	
	/**
	 *  this is the function to inject the inventory order to Jinsuan order template
	 * @return
	 */
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		//write product infmration
		order.putSetToList();
		List<InventoryOrderProduct> orderProducts = order.getProduct_List();
		int totalDataRow = orderProducts.size();
		for (int i = 0; i < totalDataRow; i++){

			InventoryOrderProduct orderProduct = orderProducts.get(i);
			Row row = sheet.createRow(data_row + i);
			
			
			Product product = orderProduct.getProductBarcode().getProduct();
			row.createCell(barcode_column).setCellValue(orderProduct.getProductBarcode().getBarcode());
			
			Color color = orderProduct.getProductBarcode().getColor();
			if (color == null)
				row.createCell(productCode_column).setCellValue(product.getProductCode());
			else 
				row.createCell(productCode_column).setCellValue(product.getProductCode() + color.getName());
			
			row.createCell(unit_column).setCellValue(product.getUnit());
			int q = orderProduct.getQuantity();
			
			row.createCell(quantity_column).setCellValue(orderProduct.getQuantity());
			row.createCell(salePrice_column).setCellValue(orderProduct.getSalesPrice());
			
			String year = product.getYear().getYear();
			String quarter = product.getQuarter().getQuarter_Name();
			String brandName = product.getBrand().getBrand_Name();
			
			String productComment = year +"年" + brandName + quarter + "装";
					
			
			row.createCell(productComment_column).setCellValue(productComment);
		}

		return templateWorkbook;
	}
	
	/**
	 * the function to transfer the Excel order to objects
	 * @param file
	 * @return
	 */
	public InventoryOrder transferExcelToObj(){
		List<InventoryOrderProduct> orderProducts = new ArrayList<InventoryOrderProduct>();
		
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		int row_start = data_row;

		while (true){
			HSSFRow row  = sheet.getRow(row_start);
			if (row == null)
				break;
			else {
				HSSFCell cell = row.getCell(serial_column);
	
				if (cell == null)
					break;
				
				String barcode = getBarcodeFromExcel(row.getCell(barcode_column));
				loggerLocal.info("Import : " + barcode);
				
				int quantity = ExcelUtil.getPuzzleNum(row.getCell(quantity_column)).intValue();
				double salesPriceSelected = ExcelUtil.getPuzzleNum(row.getCell(salePrice_column));
				double wholeSalePrice = ExcelUtil.getPuzzleNum(row.getCell(wholePrice_column));
				double discount = ExcelUtil.getPuzzleNum(row.getCell(discount_column));
				
				InventoryOrderProduct orderProduct = new InventoryOrderProduct();
				orderProduct.setQuantity(quantity);
				orderProduct.setWholeSalePrice(wholeSalePrice);
				//orderProduct.setSalesPrice(salesPrice);
				orderProduct.setSalePriceSelected(salesPriceSelected);
				orderProduct.setDiscount(discount);
				orderProduct.getProductBarcode().setBarcode(barcode);
				
				
				orderProducts.add(orderProduct);
				
				row_start++;
			}
		}
		
		order.setProduct_List(orderProducts);
		
		return order;
	}
	
	private String getBarcodeFromExcel(HSSFCell cell){
		String barcode_s = "";
		if (cell != null){
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				Double barcode_d = cell.getNumericCellValue();
				long barcode_l = barcode_d.longValue();
				barcode_s = String.valueOf(barcode_l);
				break;
			case HSSFCell.CELL_TYPE_STRING:
				barcode_s = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
			    break;
			default:
				break;
			}
		} 
		return Common_util.getBarcode(barcode_s);
	}
	
	public static void main(String[] args) throws IOException{
		File file = new File ("f:\\a.xls");
		JinSuanOrderTemplate jinSuanOrderTemplate = new JinSuanOrderTemplate(file);
		
		InventoryOrder order = jinSuanOrderTemplate.transferExcelToObj();
	}

}
