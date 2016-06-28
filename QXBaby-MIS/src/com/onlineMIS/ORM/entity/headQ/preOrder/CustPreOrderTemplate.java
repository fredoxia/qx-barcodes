package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;


public class CustPreOrderTemplate  extends ExcelTemplate{
	private CustPreOrder order = new CustPreOrder();
	private boolean showCost;
	private int DATA_ROW = 4;
	private int DATE_ROW = 1;
	private int CUST_ROW = 2;

	private int YEAR_COLUMN = 0;
	private int QUARTER_COLUMN =1;
	private int BRAND_COLUMN =2;
	private int PRODUCT_CODE_COLUMN =3;
	private int ORDER_IDENTITY_COLUMN =4;
	private int COLOR_COLUMN =4;
	private int CATEGORY_COLUMN =5;
	private int NUM_PER_HAND_COLUMN =6;
	private int QUANTITY_COLUMN =7;
	private int QUANTITY_SUM_COLUMN =8;
	private int WHOLE_PRICE_COLUMN = 9;
	private int WHOLE_PRICE_SUM_COLUMN = 10;
	private int RETAIL_PRICE_COLUMN =11;
	private int RETAIL_PRICE_SUM_COLUMN =12;


//    public ChainInventoryFlowOrderTemplate(File file) throws IOException{
//    	super(file);
//    }
	
	public CustPreOrderTemplate(CustPreOrder order, String templateWorkbookPath, boolean showCost) throws IOException{
		super(templateWorkbookPath + "\\CustomerPreOrderTemplate.xls");	
		this.order = order;
		this.showCost = showCost;
	}
	
	/**
	 *  this is the function to inject the inventory order to Jinsuan order template
	 * @return
	 */
	public HSSFWorkbook process(){
		//1. process data
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		HSSFRow dateRow = sheet.getRow(DATE_ROW);
		dateRow.createCell(1).setCellValue(Common_util.dateFormat.format(order.getCreateDate()));
		dateRow.createCell(ORDER_IDENTITY_COLUMN).setCellValue(order.getOrderIdentity());
		
		HSSFRow custRow = sheet.getRow(CUST_ROW);
		custRow.createCell(1).setCellValue(order.getCustName() + "-" + order.getChainStoreName());

		
		//write product infmration
		List<CustPreOrderProduct> orderProducts = order.getProductList();
		int totalDataRow = orderProducts.size();
		
		int totalHands = 0;
		int totalQuantity = 0;
		double totalWholePrice = 0;
		double totalRetailPrice = 0;
		int subTotalHands = 0;
		int subTotalQuantity = 0;
		double subTotalWholePrice = 0;
		double subTotalRetailPrice = 0;
		
		//2. process elements
		int brandId = 0;
		int countSubTotal = 0;
		for (int i =0; i < orderProducts.size(); i++){
			CustPreOrderProduct cop = orderProducts.get(i);
			ProductBarcode pb = cop.getProductBarcode();
			Product p = pb.getProduct();
			Color color = pb.getColor();
			
			if (i != 0 && p.getBrand().getBrand_ID() != brandId){
				//1. 写subtotal
				HSSFRow dataRow = sheet.createRow(DATA_ROW + i + countSubTotal);
				
				dataRow.createCell(QUARTER_COLUMN).setCellValue("小计:");
				dataRow.createCell(QUANTITY_COLUMN).setCellValue(subTotalHands);
				dataRow.createCell(QUANTITY_SUM_COLUMN).setCellValue(subTotalQuantity);
				if (showCost)
				  dataRow.createCell(WHOLE_PRICE_SUM_COLUMN).setCellValue(subTotalWholePrice);
				dataRow.createCell(RETAIL_PRICE_SUM_COLUMN).setCellValue(subTotalRetailPrice);
				
				subTotalHands = 0;
				subTotalQuantity = 0;
				subTotalWholePrice = 0;
				subTotalRetailPrice = 0;
				countSubTotal++;
			}
			brandId = p.getBrand().getBrand_ID();
			
			HSSFRow dataRow = sheet.createRow(DATA_ROW + i + countSubTotal);
			//dataRow.createCell(BARCODE_COLUMN).setCellValue(pb.getBarcode());
			dataRow.createCell(YEAR_COLUMN).setCellValue(p.getYear().getYear());
			dataRow.createCell(QUARTER_COLUMN).setCellValue(p.getQuarter().getQuarter_Name());
			dataRow.createCell(BRAND_COLUMN).setCellValue(p.getBrand().getBrand_Name());
			dataRow.createCell(PRODUCT_CODE_COLUMN).setCellValue(p.getProductCode());
			if (color != null)
				dataRow.createCell(COLOR_COLUMN).setCellValue(color.getName());
			dataRow.createCell(CATEGORY_COLUMN).setCellValue(p.getCategory().getCategory_Name());
			dataRow.createCell(NUM_PER_HAND_COLUMN).setCellValue(p.getNumPerHand());
			dataRow.createCell(QUANTITY_COLUMN).setCellValue(cop.getTotalQuantity());
			int qSum = p.getNumPerHand() * cop.getTotalQuantity();
			dataRow.createCell(QUANTITY_SUM_COLUMN).setCellValue(qSum);
			
			if (showCost){
				dataRow.createCell(WHOLE_PRICE_COLUMN).setCellValue(ProductBarcodeDaoImpl.getWholeSalePrice(pb));
				dataRow.createCell(WHOLE_PRICE_SUM_COLUMN).setCellValue(cop.getSumWholePrice());
				subTotalWholePrice += cop.getSumWholePrice();
				totalWholePrice += cop.getSumWholePrice(); 
			}
			
			dataRow.createCell(RETAIL_PRICE_COLUMN).setCellValue(p.getSalesPrice());
			dataRow.createCell(RETAIL_PRICE_SUM_COLUMN).setCellValue(cop.getSumRetailPrice());
			
			subTotalHands += cop.getTotalQuantity();
			subTotalRetailPrice += cop.getSumRetailPrice();
			subTotalQuantity += qSum;
			totalRetailPrice += cop.getSumRetailPrice();
			totalQuantity += qSum;
			totalHands += cop.getTotalQuantity();
		}
		
		if (subTotalQuantity != 0 && subTotalWholePrice != 0){
			HSSFRow dataRow = sheet.createRow(DATA_ROW + totalDataRow + countSubTotal);
			
			dataRow.createCell(QUARTER_COLUMN).setCellValue("小计:");
			dataRow.createCell(QUANTITY_COLUMN).setCellValue(subTotalHands);
			dataRow.createCell(QUANTITY_SUM_COLUMN).setCellValue(subTotalQuantity);
			if (showCost)
			   dataRow.createCell(WHOLE_PRICE_SUM_COLUMN).setCellValue(subTotalWholePrice);
			dataRow.createCell(RETAIL_PRICE_SUM_COLUMN).setCellValue(subTotalRetailPrice);

			countSubTotal++;
		}

		HSSFRow dataRow = sheet.createRow(DATA_ROW + totalDataRow + countSubTotal);
		dataRow.createCell(YEAR_COLUMN).setCellValue("总计:");
		dataRow.createCell(QUANTITY_COLUMN).setCellValue(totalHands);
		dataRow.createCell(QUANTITY_SUM_COLUMN).setCellValue(totalQuantity);
		dataRow.createCell(WHOLE_PRICE_SUM_COLUMN).setCellValue(totalWholePrice);
		dataRow.createCell(RETAIL_PRICE_SUM_COLUMN).setCellValue(totalRetailPrice);
		
		
		return templateWorkbook;
	}
	
	

}
