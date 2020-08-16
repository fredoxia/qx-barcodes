package com.onlineMIS.ORM.entity.headQ.barcodeGentor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.common.ExcelTemplate;

public class BarcodeCreationTemplate extends ExcelTemplate{
	public final String SPLITTER = ",";
	protected final int data_row = 1;
	protected final int year_column= 0;
	protected final int quarter_column= 1;
	protected final int brand_column= 2;
	protected final int productCode_column= 3;
	protected final int barcode_column= 4;
	protected final int color_column= 5;
	protected final int category_column= 6;
	protected final int recCost_column= 7;
	protected final int factoryPrice_column= 8;
	protected final int discount_column= 9;
	protected final int wholePrice_column= 10;
	protected final int salePrice_column= 11;
	protected final int unit_column= 12;
	protected final int numPerHand_column= 13;
	protected final int productNum_column= 14;
	
	private List<ProductBarcode> products = new ArrayList<ProductBarcode>();

	public BarcodeCreationTemplate(){
		super();
	}
	
	/**
	 * 传入barcode对象，生成文件
	 * @param ProductBarcodes
	 * @param templateWorkbookPath
	 * @throws IOException
	 */
	public BarcodeCreationTemplate(List<ProductBarcode> ProductBarcodes, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);
		this.products = ProductBarcodes;	
	}
	
	/**
	 * 传入文件，生出条码
	 */
	public BarcodeCreationTemplate(File file){
		super();
		try {
		  templateWorkbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 通过条码创建excel下载
	 * @return
	 */
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		if (products != null)
			for (int i = 0; i < products.size(); i++){
				ProductBarcode productBarcode = products.get(i);
				Product product = productBarcode.getProduct();
				
				Row row = sheet.createRow(data_row + i);
				
				row.createCell(year_column).setCellValue(product.getYear().getYear());
				row.createCell(quarter_column).setCellValue(product.getQuarter().getQuarter_Name());
				row.createCell(brand_column).setCellValue(product.getBrand().getBrand_Name() + SPLITTER + product.getBrand().getBrand_ID());
				row.createCell(productCode_column).setCellValue(product.getProductCode());
				row.createCell(barcode_column).setCellValue(productBarcode.getBarcode());
				Color color = productBarcode.getColor();
				if (color != null)
				    row.createCell(color_column).setCellValue(productBarcode.getColor().getName() + SPLITTER + productBarcode.getColor().getColorId());
				row.createCell(category_column).setCellValue(product.getCategory().getCategory_Name() + SPLITTER+ product.getCategory().getCategory_ID());
				
				row.createCell(recCost_column).setCellValue(product.getRecCost());
				row.createCell(factoryPrice_column).setCellValue(product.getSalesPriceFactory());
				row.createCell(discount_column).setCellValue(product.getDiscount());
				row.createCell(wholePrice_column).setCellValue(product.getWholeSalePrice());
				row.createCell(salePrice_column).setCellValue(product.getSalesPrice());
				row.createCell(unit_column).setCellValue(product.getUnit());
				row.createCell(numPerHand_column).setCellValue(product.getNumPerHand());
				row.createCell(productNum_column).setCellValue(product.getSerialNum());
			}

		return templateWorkbook;
	}

}

