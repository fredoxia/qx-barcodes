package com.onlineMIS.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;


public class Excel2007Template {
	protected XSSFWorkbook  templateWorkbook;
	protected String templateWorkbookPath;
	protected XSSFCreationHelper createHelper;
	protected XSSFCellStyle dateStyle;
	protected XSSFCellStyle aroundLineStyle;
	protected XSSFCellStyle highLigntStyle;
	
	
	public XSSFWorkbook getTemplateWorkbook() {
		return templateWorkbook;
	}

	public void setTemplateWorkbook(XSSFWorkbook templateWorkbook) {
		this.templateWorkbook = templateWorkbook;
	}

	public String getTemplateWorkbookPath() {
		return templateWorkbookPath;
	}

	public void setTemplateWorkbookPath(String templateWorkbookPath) {
		this.templateWorkbookPath = templateWorkbookPath;
	}

	public XSSFCreationHelper getCreateHelper() {
		return createHelper;
	}

	public void setCreateHelper(XSSFCreationHelper createHelper) {
		this.createHelper = createHelper;
	}

	public XSSFCellStyle getDateStyle() {
		return dateStyle;
	}

	public void setDateStyle(XSSFCellStyle dateStyle) {
		this.dateStyle = dateStyle;
	}
	
	public Excel2007Template(){
		
	}

	public Excel2007Template(String templateWorkbookPath) throws IOException{
		this.templateWorkbookPath = templateWorkbookPath;
		
		InputStream is;   
		XSSFWorkbook wb = null;
		try {
			is = new FileInputStream(this.templateWorkbookPath);
			wb = new XSSFWorkbook(is);   
		} catch (FileNotFoundException e) {
			loggerLocal.error(e);
			throw e;
		} catch (IOException e) {
			loggerLocal.error(e);
			throw e;
		}   
		
        initialize(wb);
		
	}
	
	public  Excel2007Template(File templateWorkbookFile) throws IOException{
		InputStream is;   
		XSSFWorkbook wb = null;
		try {
			is = new FileInputStream(templateWorkbookFile);
			wb = new XSSFWorkbook(is);   
		} catch (FileNotFoundException e) {
			loggerLocal.error(e);
			throw e;
		} catch (IOException e) {
			loggerLocal.error(e);
			throw e;
		}   
		
        initialize(wb);
	}
	
	private void initialize(XSSFWorkbook wb){
		this.templateWorkbook = wb;
		
		createHelper = templateWorkbook.getCreationHelper();

		dateStyle = templateWorkbook.createCellStyle();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
	
		highLigntStyle = templateWorkbook.createCellStyle();
		highLigntStyle.setFillBackgroundColor(XSSFFont.COLOR_NORMAL);
		XSSFFont boldFont = templateWorkbook.createFont();
		boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		highLigntStyle.setFont(boldFont);
		
		aroundLineStyle = templateWorkbook.createCellStyle();
		aroundLineStyle.setBorderLeft((short) 1);
		aroundLineStyle.setBorderRight((short) 1);
		aroundLineStyle.setBorderTop((short) 1);
		aroundLineStyle.setBorderBottom((short) 1);
		XSSFFont font = templateWorkbook.createFont();
		font.setFontHeightInPoints((short)17);
		aroundLineStyle.setFont(font);
		aroundLineStyle.setWrapText(true);
	}
	
	protected XSSFCell createCell(XSSFRow row, int column){
		XSSFCell cell = row.createCell(column);
		return cell;
	}

}
