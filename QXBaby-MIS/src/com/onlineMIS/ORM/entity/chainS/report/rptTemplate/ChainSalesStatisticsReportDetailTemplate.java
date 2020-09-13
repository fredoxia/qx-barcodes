package com.onlineMIS.ORM.entity.chainS.report.rptTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import com.onlineMIS.common.Excel2007Template;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.loggerLocal;

public class ChainSalesStatisticsReportDetailTemplate  extends Excel2007Template{

	private List<ChainSalesStatisReportItem> detailItems = new ArrayList<ChainSalesStatisReportItem>();
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
	
	private final int SALE_DATE_DETAIL_COLUMN = 0;
	private final int CHAIN_DETAIL_COLUMN = 1;
	private final int BARCODE_DETAIL_COLUMN = 2;
	private final int PRODUCT_CODE_DETAIL_COLUMN = 3;
	private final int COLOR_DETAIL_COLUMN = 4;
	private final int BRAND_DETAIL_COLUMN = 5;
	private final int QUARTER_DETAIL_COLUMN = 6;
	private final int CATEGORY_DETAIL_COLUMN =7;
	private final int SALE_Q_DETAIL_COLUMN =8;
	private final int RETURN_Q_DETAIL_COLUMN = 9;
	private final int NET_Q_DETAIL_COLUMN = 10;
	private final int FREE_Q_DETAIL_COLUMN = 11;
	private final int SALES_DETAIL_COLUMN = 12;
	private final int RETURN_DETAIL_COLUMN = 13;
	private final int NET_DETAIL_COLUMN = 14;
	private final int NET_COST_DETAIL_COLUMN = 15;
	private final int FREE_COST_DETAIL_COLUMN = 16;
	private final int PROFIT_DETAIL_COLUMN = 17;
	private final int SALES_DISCOUNT_DETAIL_COLUMN = 18;
	
	
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


    public ChainSalesStatisticsReportDetailTemplate(File file) throws IOException{
    	super(file);
    }
	
	public ChainSalesStatisticsReportDetailTemplate(List<ChainSalesStatisReportItem> detailItems, ChainSalesStatisReportItem totalItem, ChainStore chainStore, String templateWorkbookPath, boolean showCost, ChainUserInfor saler, Date startDate, Date endDate) throws Exception{
		super(templateWorkbookPath);	
		this.detailItems = detailItems;
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
	public XSSFWorkbook process(){
		
		//@明细sheet 2
		XSSFSheet sheetDetail = templateWorkbook.getSheetAt(0);
		//write header
		XSSFRow headerDetail1 = sheetDetail.getRow(1);
		headerDetail1.createCell(1).setCellValue(Common_util.dateFormat.format(startDate));
		headerDetail1.createCell(3).setCellValue(Common_util.dateFormat.format(endDate));
		
		XSSFRow headerDetail2 = sheetDetail.getRow(2);
		headerDetail2.createCell(1).setCellValue(chainStore.getChain_name());
		
		if (saler != null){
			XSSFRow headerDetail3 = sheetDetail.getRow(3);
			headerDetail3.createCell(1).setCellValue(saler.getName());
		}
		
		//write product infmration
		int totalDataDetailRow = detailItems.size();

		for (int i = 0; i < totalDataDetailRow; i++){

			ChainSalesStatisReportItem levelFourItem = detailItems.get(i);
			XSSFRow rowDetail = sheetDetail.createRow(data_row + i);

			ProductBarcode pb = levelFourItem.getProductBarcode();
			Product product = pb.getProduct();
			
			rowDetail.createCell(SALE_DATE_DETAIL_COLUMN).setCellValue(levelFourItem.getDate());
			rowDetail.createCell(CHAIN_DETAIL_COLUMN).setCellValue(levelFourItem.getChainStore().getChain_name());
			rowDetail.createCell(BARCODE_DETAIL_COLUMN).setCellValue(pb.getBarcode());
			rowDetail.createCell(PRODUCT_CODE_DETAIL_COLUMN).setCellValue(product.getProductCode());
			Color color = levelFourItem.getProductBarcode().getColor();
			if (color == null)
				rowDetail.createCell(COLOR_DETAIL_COLUMN).setCellValue("");
			else 
				rowDetail.createCell(COLOR_DETAIL_COLUMN).setCellValue(color.getName());
			
			rowDetail.createCell(BRAND_DETAIL_COLUMN).setCellValue(product.getBrand().getBrand_Name());
			
			rowDetail.createCell(QUARTER_DETAIL_COLUMN).setCellValue(product.getYear().getYear() + "-" + product.getQuarter().getQuarter_Name());

			rowDetail.createCell(CATEGORY_DETAIL_COLUMN).setCellValue(product.getCategory().getCategory_Name());
			rowDetail.createCell(SALE_Q_DETAIL_COLUMN).setCellValue(levelFourItem.getSalesQ());
			rowDetail.createCell(RETURN_Q_DETAIL_COLUMN).setCellValue(levelFourItem.getReturnQ());
			rowDetail.createCell(NET_Q_DETAIL_COLUMN).setCellValue(levelFourItem.getNetQ());
			rowDetail.createCell(FREE_Q_DETAIL_COLUMN).setCellValue(levelFourItem.getFreeQ());
			rowDetail.createCell(SALES_DETAIL_COLUMN).setCellValue(levelFourItem.getSalesPrice());
			rowDetail.createCell(RETURN_DETAIL_COLUMN).setCellValue(levelFourItem.getReturnPrice());
			rowDetail.createCell(NET_DETAIL_COLUMN).setCellValue(levelFourItem.getNetPrice());
			rowDetail.createCell(SALES_DISCOUNT_DETAIL_COLUMN).setCellValue(levelFourItem.getSalesDiscount());
			
			if (showCost){
				rowDetail.createCell(NET_COST_DETAIL_COLUMN).setCellValue(levelFourItem.getNetCost());
				rowDetail.createCell(FREE_COST_DETAIL_COLUMN).setCellValue(levelFourItem.getFreeCost());
				rowDetail.createCell(PROFIT_DETAIL_COLUMN).setCellValue(levelFourItem.getNetProfit());
			}
		}

	
		return templateWorkbook;
	}
	

}
