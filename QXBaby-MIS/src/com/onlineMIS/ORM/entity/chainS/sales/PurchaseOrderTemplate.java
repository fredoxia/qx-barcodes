package com.onlineMIS.ORM.entity.chainS.sales;


import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.ExcelTemplate;


public class PurchaseOrderTemplate extends ExcelTemplate{
	private final int header_row1_ind = 1;
	private final int customerArea_column = 1;
	private final int header_row2_ind = 2;
	private final int customerName_column= 1;

	private final int inventoryKeeper_column= 3;
	private final int inventoryCounter_column= 3;
	private final int auditor_column= 5;
	private final int inventoryDate_column= 5;
	private final int order_type_column = 8;
	
	private final int header_row3_ind = 3;
	private final int order_comment_column= 1;
	
	private final int data_row = 5;
	private final int sequence_column = 0;
	private final int brand_column = 1;
	private final int productCode_column= 2;
	private final int color_column = 3;
	private final int barcode_column= 4;
	private final int year_column = 5;
	private final int quarter_column = 6;
	private final int unit_column= 7;
	private final int quantity_column= 8;
	private final int wholeCost_column= 9;
	private final int discount_column= 10;
	private final int discountPrice_column= 11;
	private final int wholeTotal_column= 12;
	private final int salesPrice_column= 13;
	private final int salesRevenue_column = 14;
	private final int comment_column = 15;

	
	private InventoryOrder order;

	
	public PurchaseOrderTemplate(InventoryOrder order, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);		
		this.order = order;	
	}
	
	public HSSFWorkbook process(boolean displayCost){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
        
		//write header information
		Row headerRow1 = sheet.getRow(header_row1_ind);
		headerRow1.createCell(customerArea_column).setCellValue(order.getClient_name());
		headerRow1.createCell(inventoryKeeper_column).setCellValue(order.getOrder_Keeper().getName());
		headerRow1.createCell(auditor_column).setCellValue(order.getOrder_Auditor().getName());
		headerRow1.createCell(order_type_column).setCellValue(order.getOrder_type_chain());
		
		
		Row headerRow2 = sheet.getRow(header_row2_ind);
		headerRow2.createCell(inventoryCounter_column).setCellValue(order.getOrder_Counter().getName());
		Cell inventoryDateCell = headerRow2.createCell(inventoryDate_column);
		inventoryDateCell.setCellValue(order.getOrder_StartTime());
		inventoryDateCell.setCellStyle(dateStyle);

		Row headerRow3 = sheet.getRow(header_row3_ind);
		headerRow3.createCell(order_comment_column).setCellValue(order.getComment());
		
		//write product infmration
		order.putSetToList();
		List<InventoryOrderProduct> orderProducts = order.getProduct_List();
		int totalDataRow = orderProducts.size();
		for (int i = 0; i < totalDataRow; i++){
			double salesRevenue = 0;
			double wholeTotal = 0;
			InventoryOrderProduct orderProduct = orderProducts.get(i);
			Row row = sheet.createRow(data_row + i);
			
			row.createCell(sequence_column).setCellValue(i +1);
			
			Product product = orderProduct.getProductBarcode().getProduct();
			row.createCell(brand_column).setCellValue(product.getBrand().getBrand_Name());
			
			row.createCell(productCode_column).setCellValue(product.getProductCode());
			
			Color color = orderProduct.getProductBarcode().getColor();
			if (color == null)
				row.createCell(color_column).setCellValue("");
			else 
				row.createCell(color_column).setCellValue(color.getName());
			
			row.createCell(barcode_column).setCellValue(orderProduct.getProductBarcode().getBarcode());

			row.createCell(year_column).setCellValue(product.getYear().getYear());
			row.createCell(quarter_column).setCellValue(product.getQuarter().getQuarter_Name());
			row.createCell(unit_column).setCellValue(product.getUnit());
			
			int q = orderProduct.getQuantity();
			row.createCell(quantity_column).setCellValue(q);
			
			if (displayCost){
				row.createCell(wholeCost_column).setCellValue(orderProduct.getSalePriceSelected());
				row.createCell(discount_column).setCellValue(orderProduct.getDiscount());
				
				double wholePrice = orderProduct.getWholeSalePrice();
				row.createCell(discountPrice_column).setCellValue(wholePrice);
				
				wholeTotal = q * wholePrice;
				row.createCell(wholeTotal_column).setCellValue(wholeTotal);
			} else {
				row.createCell(wholeCost_column).setCellValue("-");
				row.createCell(discount_column).setCellValue("-");
				row.createCell(discountPrice_column).setCellValue("-");
				row.createCell(wholeTotal_column).setCellValue("-");
			}
			double salesPrice = orderProduct.getSalesPrice();
			salesRevenue = q * salesPrice;

			row.createCell(salesRevenue_column).setCellValue(salesRevenue);
			row.createCell(salesPrice_column).setCellValue(salesPrice);
			
			int numPerHand = product.getNumPerHand();
			if (q >= numPerHand*2)
				row.createCell(comment_column).setCellValue("*");
		}
		
		//write footer
		int footerRowNum = data_row + totalDataRow;
		Row footerRow = sheet.createRow(footerRowNum);
		footerRow.createCell(sequence_column).setCellValue("总数");
		if (displayCost)
		   footerRow.createCell(wholeTotal_column).setCellValue(order.getTotalWholePrice());
		else 
			footerRow.createCell(wholeTotal_column).setCellValue("-");
		footerRow.createCell(quantity_column).setCellValue(order.getTotalQuantity());
		footerRow.createCell(salesRevenue_column).setCellValue(order.getTotalRetailPrice());
		return templateWorkbook;
	}

}
