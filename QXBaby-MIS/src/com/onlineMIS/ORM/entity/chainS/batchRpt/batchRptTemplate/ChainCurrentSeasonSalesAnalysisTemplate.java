package com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;

public class ChainCurrentSeasonSalesAnalysisTemplate extends ExcelTemplate{
	private final static String templateName = "CurrentSeasonSalesAnlysisTemplate.xls";
	public static final int NUM_OF_ITEM = -1;
	
	private ChainCurrentSeasonProductAnalysisRpt rpt;
	
	private final String header = "销售分析报表";
	private final int DATA_ROW = 3;
	private final int RANK_COL = 0;
	private final int CHANIN_NAME_COL = 1;
	private final int LAST_YEAR_PURCHASE_COL = 2;
	private final int NET_PURCHASE_COL = 3;
	private final int RETURN_RATIO_COL = 4;
	private final int INVENTORY_AMT_COL = 5;
	private final int INVENTORY_RATIO_COL = 6;
	private final int SALES_AMT_COL = 7;
	private final int SALES_RATIO_COL = 8;

	
	public ChainCurrentSeasonSalesAnalysisTemplate(ChainCurrentSeasonProductAnalysisRpt rpt, String templateWorkbookPath)
			throws IOException {
		super(templateWorkbookPath + "\\" + templateName);
		this.rpt = rpt;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		Year year = rpt.getYear();
		Quarter quarter = rpt.getQuarter();
	
		
		return templateWorkbook;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
