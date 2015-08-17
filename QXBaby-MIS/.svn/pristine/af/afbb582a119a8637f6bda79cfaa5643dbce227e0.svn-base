package com.onlineMIS.ORM.entity.headQ.inventory;


import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.ExcelTemplate;


public class PDAOrderTemplate extends ExcelTemplate{
	private final int header_row1_ind = 0;
	private final int header_row2_ind = 1;
	private final int header_data_col = 3;
	private final int content_start_row = 3;
	private final int sequence_col_1 = 0;
	private final int year_col_1 = 1;
	private final int quarter_col_1 = 2;
	private final int productCode_col_1 = 3;
	private final int quantity_col_1 = 4;
	private final int unit_col_1 = 5;
	private final int sequence_col_2 = 8;
	private final int year_col_2 = 9;
	private final int quarter_col_2 = 10;
	private final int productCode_col_2 = 11;
	private final int quantity_col_2 = 12;
	private final int unit_col_2 = 13;	
	
	private InventoryOrder order;

	public PDAOrderTemplate(InventoryOrder order, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);		
		this.order = order;	
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
        
		//write header information
		Row headerRow1 = sheet.getRow(header_row1_ind);
		headerRow1.createCell(header_data_col).setCellValue(order.getClient_name());

		Row headerRow2 = sheet.getRow(header_row2_ind);
		UserInfor pdaScanner = order.getPdaScanner();
		if (pdaScanner != null)
             headerRow2.createCell(header_data_col).setCellValue("PDA录入 : " + pdaScanner.getName());
		headerRow2.createCell(year_col_2).setCellValue(order.getOrder_StartTime().toString());
		
		//write product infmration
		order.putSetToList();
		List<InventoryOrderProduct> orderProducts = order.getProduct_List();
		int totalDataRow = orderProducts.size();
		int numOfRow = (totalDataRow + 1)/2;
		for (int i = 0; i < numOfRow; i++){
			//1. write the first one
			InventoryOrderProduct orderProduct = orderProducts.get(i);
			Row row = sheet.createRow(content_start_row + i);
			
			ProductBarcode productBarcode = orderProduct.getProductBarcode();
			Color color = productBarcode.getColor();
			String colorName = "";
			if (color != null)
				colorName = color.getName();
			
			Product product = orderProduct.getProductBarcode().getProduct();
			Cell sequenceCell1 = row.createCell(sequence_col_1);
			 sequenceCell1.setCellValue(i + 1);
			 sequenceCell1.setCellStyle(aroundLineStyle);
			Cell productCell1 = row.createCell(productCode_col_1);
			 productCell1.setCellValue(product.getBrand().getBrand_Name() + " " + product.getProductCode() + " " + colorName);
			 productCell1.setCellStyle(aroundLineStyle);
			Cell yearCell1 = row.createCell(year_col_1);
			 yearCell1.setCellValue(product.getYear().getYear());
			 yearCell1.setCellStyle(aroundLineStyle);
			Cell quarterCell1 = row.createCell(quarter_col_1);
			 quarterCell1.setCellValue(product.getQuarter().getQuarter_Name());
			 quarterCell1.setCellStyle(aroundLineStyle);
			Cell quantityCell1 = row.createCell(quantity_col_1);
			 quantityCell1.setCellValue(orderProduct.getQuantity());
			 quantityCell1.setCellStyle(aroundLineStyle);
			Cell unitCell1 = row.createCell(unit_col_1);
			 unitCell1.setCellValue(product.getUnit());
			 unitCell1.setCellStyle(aroundLineStyle);
			Cell emptyCell1 = row.createCell(unit_col_1+1);
			 emptyCell1.setCellValue("");
			 emptyCell1.setCellStyle(aroundLineStyle);
			
			//2. write the second one
			if (i + numOfRow < totalDataRow){
				int j = i + numOfRow;
				InventoryOrderProduct orderProduct2 = orderProducts.get(j);
				
				ProductBarcode productBarcode2 = orderProduct2.getProductBarcode();
				Color color2 = productBarcode2.getColor();
				String colorName2 = "";
				if (color2 != null)
					colorName2 = color2.getName();
				
				Product product2 = productBarcode2.getProduct();
				Cell sequenceCell2 = row.createCell(sequence_col_2);
				 sequenceCell2.setCellValue(i + 1 + numOfRow);
				 sequenceCell2.setCellStyle(aroundLineStyle);
				Cell productCell2 = row.createCell(productCode_col_2);
				 productCell2.setCellValue(product2.getBrand().getBrand_Name() + " " + product2.getProductCode() + " " + colorName2);
				 productCell2.setCellStyle(aroundLineStyle);
				Cell yearCell2 = row.createCell(year_col_2);
				 yearCell2.setCellValue(product2.getYear().getYear());
				 yearCell2.setCellStyle(aroundLineStyle);
				Cell quarterCell2 = row.createCell(quarter_col_2);
				 quarterCell2.setCellValue(product2.getQuarter().getQuarter_Name());
				 quarterCell2.setCellStyle(aroundLineStyle);
				Cell quantityCell2 = row.createCell(quantity_col_2);
				 quantityCell2.setCellValue(orderProduct2.getQuantity());
				 quantityCell2.setCellStyle(aroundLineStyle);
				Cell unitCell2 = row.createCell(unit_col_2);
				 unitCell2.setCellValue(product2.getUnit());
				 unitCell2.setCellStyle(aroundLineStyle);
				Cell emptyCell2 = row.createCell(unit_col_2+1);
				 emptyCell2.setCellValue("");
				 emptyCell2.setCellStyle(aroundLineStyle);
			}

		}
		
		//write footer
		int footerRowNum = content_start_row + numOfRow;
		Row footerRow = sheet.createRow(footerRowNum);
		Cell totalCCell = footerRow.createCell(year_col_2);
		 totalCCell.setCellValue("总数");
		 totalCCell.setCellStyle(aroundLineStyle);
		Cell totalQCell = footerRow.createCell(quantity_col_2);
		 totalQCell.setCellValue(order.getTotalQuantity());
		 totalQCell.setCellStyle(aroundLineStyle);
		return templateWorkbook;
	}

}
