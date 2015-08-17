package com.onlineMIS.ORM.entity.headQ4Chain.barcodeGentor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.ExcelTemplate;

public class ChainBarcodeTemplate extends ExcelTemplate{
	private final int data_row = 2;
	private final int year_column= 0;
	private final int quarter_column= 1;
	private final int brand_column= 2;
	private final int productCode_column= 3;
	private final int color_column= 4;
	private final int barcode_column= 5;
	private final int wholePrice_column= 6;
	private final int salePrice_column= 7;
	
	private List<ProductBarcode> products = new ArrayList<ProductBarcode>();
	
	public ChainBarcodeTemplate(List<ProductBarcode> ProductBarcodes, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);
		this.products = ProductBarcodes;	
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		if (products != null)
			for (int i = 0; i < products.size(); i++){
				ProductBarcode productBarcode = products.get(i);
				Product product = productBarcode.getProduct();
				
				Row row = sheet.createRow(data_row + i);
				
				row.createCell(year_column).setCellValue(product.getYear().getYear());
				row.createCell(quarter_column).setCellValue(product.getQuarter().getQuarter_Name());
				row.createCell(brand_column).setCellValue(product.getBrand().getBrand_Name());
				row.createCell(productCode_column).setCellValue(product.getProductCode());
				Color color = productBarcode.getColor();
				if (color == null)
				    row.createCell(color_column).setCellValue("-");
				else 
					row.createCell(color_column).setCellValue(color.getName());
				row.createCell(barcode_column).setCellValue(productBarcode.getBarcode());
				row.createCell(wholePrice_column).setCellValue(product.getWholeSalePrice());
				row.createCell(salePrice_column).setCellValue(product.getSalesPrice());
			}

		return templateWorkbook;
	}

}
