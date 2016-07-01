package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;


public class CustPreOrderSummaryTemplate  extends ExcelTemplate{
	private CustPreOrder order = new CustPreOrder();

	private int DATA_ROW = 3;
	private int DATE_ROW = 1;
	private int BRAND_ROW = 2;

	private int CUST_COL = 0;
	private int DATA_COL = 1;
	private int ORDER_IDENTITY_COL = 3;
	private String orderIdentity = "";
	private List<Brand> brands ;
	private List<CustPreOrderSummaryData> summaryDatas ;
	
	public CustPreOrderSummaryTemplate(String orderIdentity, List<Brand> brands, List<CustPreOrderSummaryData> summaryDatas, String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath + "\\CustomerPreOrderSummaryTemplate.xls");	
		this.orderIdentity = orderIdentity;
		this.brands = brands;
		this.summaryDatas = summaryDatas;
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
		dateRow.createCell(ORDER_IDENTITY_COL).setCellValue(orderIdentity);
		
		HSSFRow brandRow = sheet.getRow(BRAND_ROW);
		for (int i = 0; i < brands.size(); i++){
			brandRow.createCell(DATA_COL + i).setCellValue(brands.get(i).getBrand_Name());
		}
		brandRow.createCell(DATA_COL + brands.size()).setCellValue("客户小计");

		for (int i = 0; i < summaryDatas.size(); i++){
			CustPreOrderSummaryData summaryData = summaryDatas.get(i);
			HSSFRow summaryDataRow = sheet.createRow(DATA_ROW + i);
			
			summaryDataRow.createCell(CUST_COL).setCellValue(summaryData.getCustName());
			List<Integer> data = summaryData.getQ();
			for (int j = 0; j < data.size(); j++){
				summaryDataRow.createCell(DATA_COL + j).setCellValue(data.get(j));
			}
			summaryDataRow.createCell(DATA_COL + data.size()).setCellValue(summaryData.getSum());
		}
		
		return templateWorkbook;
	}
	
	

}
