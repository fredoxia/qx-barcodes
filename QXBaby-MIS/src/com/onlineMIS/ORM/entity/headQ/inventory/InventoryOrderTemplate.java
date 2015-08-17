package com.onlineMIS.ORM.entity.headQ.inventory;


import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.ExcelTemplate;


public class InventoryOrderTemplate extends ExcelTemplate{
	private final int title_row = 0;
	private final int order_title_column = 2;
	
	private final int header_row1_ind = 1;
	private final int customerArea_column = 1;
	private final int header_row2_ind = 2;

	private final int inventoryKeeper_column= 3;
	private final int inventoryCounter_column= 3;
	private final int auditor_column= 5;
	private final int inventoryDate_column= 5;
	
	private final int data_row = 4;
	private final int productName_column= 0;
	private final int productCode_column= 1;
	private final int year_column = 2;
	private final int quarter_column = 3;
	private final int quantity_column= 4;
	private final int wholePrice_column= 5;
	private final int wholeTotal_column= 6;
	private final int salesPrice_column= 7;
	private final int salesRevenue_column = 8;
	private final int unit_column= 9;
	private final int barcode_column= 10;
	private final int comment_column = 11;

	
	private InventoryOrder order;

	
	public InventoryOrderTemplate(InventoryOrder order, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);		
		this.order = order;	
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//write title information
		Row titleRow = sheet.getRow(title_row);
		titleRow.createCell(order_title_column).setCellValue("千禧宝贝" + order.getOrder_type_ws());

		//write header information
		Row headerRow1 = sheet.getRow(header_row1_ind);
		headerRow1.createCell(customerArea_column).setCellValue(order.getClient_name());
		headerRow1.createCell(inventoryKeeper_column).setCellValue(order.getOrder_Keeper().getName());
		headerRow1.createCell(auditor_column).setCellValue(order.getOrder_Auditor().getName());
		
		Row headerRow2 = sheet.getRow(header_row2_ind);
//		headerRow2.createCell(customerName_column).setCellValue(order.getCustomer_Name());
		headerRow2.createCell(inventoryCounter_column).setCellValue(order.getOrder_Counter().getName());
		Cell inventoryDateCell = headerRow2.createCell(inventoryDate_column);
		inventoryDateCell.setCellValue(order.getOrder_StartTime());
		inventoryDateCell.setCellStyle(dateStyle);


		
		//write product infmration
		order.putSetToList();
		List<InventoryOrderProduct> orderProducts = order.getProduct_List();
		int totalDataRow = orderProducts.size();
		for (int i = 0; i < totalDataRow; i++){
			double salesRevenue = 0;
			double wholeTotal = 0;
			InventoryOrderProduct orderProduct = orderProducts.get(i);
			Row row = sheet.createRow(data_row + i);
			
			Product product = orderProduct.getProductBarcode().getProduct();
			row.createCell(productName_column).setCellValue(product.getBrand().getBrand_Name());
			
			Color color = orderProduct.getProductBarcode().getColor();
			if (color == null)
			   row.createCell(productCode_column).setCellValue(product.getProductCode());
			else 
				row.createCell(productCode_column).setCellValue(product.getProductCode() + color.getName());
			
			row.createCell(year_column).setCellValue(product.getYear().getYear());
			row.createCell(quarter_column).setCellValue(product.getQuarter().getQuarter_Name());
			
			int q = orderProduct.getQuantity();
			double wholePrice = orderProduct.getWholeSalePrice();
			double salesPrice = orderProduct.getSalesPrice();
			salesRevenue = q * salesPrice;
			wholeTotal = q * wholePrice;
			row.createCell(quantity_column).setCellValue(q);
			
			row.createCell(wholePrice_column).setCellValue(wholePrice);
			row.createCell(wholeTotal_column).setCellValue(wholeTotal);
			row.createCell(salesRevenue_column).setCellValue(salesRevenue);
			row.createCell(salesPrice_column).setCellValue(salesPrice);
			row.createCell(unit_column).setCellValue(product.getUnit());
			row.createCell(barcode_column).setCellValue(orderProduct.getProductBarcode().getBarcode());

			int numPerHand = product.getNumPerHand();
			if (q >= numPerHand*2)
				row.createCell(comment_column).setCellValue("*");
		}
		
		//write footer
		int footerRowNum = data_row + totalDataRow;
		Row footerRow = sheet.createRow(footerRowNum);
		footerRow.createCell(productName_column).setCellValue("总数");
		footerRow.createCell(wholeTotal_column).setCellValue(order.getTotalWholePrice());
		footerRow.createCell(quantity_column).setCellValue(order.getTotalQuantity());
		footerRow.createCell(salesRevenue_column).setCellValue(order.getTotalRetailPrice());
		return templateWorkbook;
	}

}
