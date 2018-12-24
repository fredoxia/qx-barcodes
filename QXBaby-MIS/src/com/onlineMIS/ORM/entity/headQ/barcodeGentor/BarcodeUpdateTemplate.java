package com.onlineMIS.ORM.entity.headQ.barcodeGentor;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.CategoryDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ColorDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductUnitDaoImpl;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.ExcelUtil;

public class BarcodeUpdateTemplate extends BarcodeTemplate{

	private final int productCode_column = 3;
	private final int barcode_column= 4;
	private final int category_column= 5;
	private final int recCost_column= 6;
	private final int factorySalesPrice_column= 7;
	private final int discount_column= 8;
	private final int wholePrice_column= 9;
	private final int salePrice_column= 10;	
	private final int unit_column = 11;
	private final int fullHandQ_column = 12;
	private boolean isSuccess = true;
	private String validateMsg = "";
	
	private Set<Product> wsData = new HashSet<Product>();
	/**
	 * 传入文件，生出条码
	 */
	public BarcodeUpdateTemplate(File file){
		super();
		try {
			  templateWorkbook = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception e){
				e.printStackTrace();
			}
	}
	
	/**
	 * 验证所有信息正确
	 * @param categoryDaoImpl
	 * @param colorDaoImpl
	 * @return
	 */
	private void validate(ProductBarcodeDaoImpl pbBarcodeDaoImpl, CategoryDaoImpl categoryDaoImpl, ProductUnitDaoImpl productUnitDaoImpl){
		StringBuilder errorMsg = new StringBuilder();
		boolean validate = true;
		
		if (templateWorkbook == null){
			isSuccess = false;
			validateMsg = "上传的文件是空文件";
			return ;
		}
		
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		int rowDisplay = 0;
		boolean rowError = true;
		for (int i = data_row; ;i++){
			rowError = true;
			rowDisplay = i +1;
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				break;
			
			String productCode = ExcelUtil.getPuzzleString(row.getCell(productCode_column));
			if (productCode!=null && productCode.length() != 0){
                if (Common_util.isContainChinese(productCode)){
					productCode = Common_util.filterChinese(productCode);
					if (productCode.length()== 0){
						rowError = false;
						errorMsg.append("行" + rowDisplay + ": 产品货号不正确. ");
					}
				}
			}
		
			String barcode = ExcelUtil.getPuzzleString(row.getCell(barcode_column));
			ProductBarcode pb = null;
			if (barcode==null || barcode.length() != ProductBarcode.BARCODE_LENGTH){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 产品条码长度不正确. ");
			} else {
				pb = pbBarcodeDaoImpl.getByBarcode(barcode);
				if (pb == null) {
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 产品条码不正确. ");
				}
			}
		
			HSSFCell categoryCell = row.getCell(category_column);
			if (categoryCell != null){
				String categoryString = categoryCell.getStringCellValue().trim();
				
				if (!categoryString.equals("") && !categoryDaoImpl.checkCategoryExist(categoryString)){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 产品种类 (" + categoryString + ") 不存在. ");
				}
			}
			
			try {
			   double recCost = row.getCell(recCost_column).getNumericCellValue();
			   if (recCost < 0) {
			     rowError = false;
				 errorMsg.append("行" + rowDisplay + ": 进价("+ recCost+")错误. ");
			   }
			   } catch (NullPointerException nException){
			   } catch (Exception e){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 进价错误. ");
			}
			
			try {
				   double factorySalesPrice = row.getCell(factorySalesPrice_column).getNumericCellValue();
				   if (factorySalesPrice < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 厂家零售价("+ factorySalesPrice+")错误. ");
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 厂家零售价错误. ");
				}
			
			try {
				   double discount = row.getCell(discount_column).getNumericCellValue();
				   if (discount < 0 || discount >1) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 折扣("+ discount+")错误. ");
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 折扣错误. ");
				}
			
			try {
				   double wholePrice = row.getCell(wholePrice_column).getNumericCellValue();
				   if (wholePrice < 0 ) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 发价("+ wholePrice+")错误. ");
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 发价错误. ");
				}
			
			try {
				   double salePrice = row.getCell(salePrice_column).getNumericCellValue();
				   if (salePrice < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 发价("+ salePrice+")错误. ");
				   }
			} catch (NullPointerException nException){
				
			} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 发价错误. ");
				}
			
			String unit = ExcelUtil.getPuzzleString(row.getCell(unit_column));
			
			if (unit != null && !unit.equals("")){
				if (!productUnitDaoImpl.checkExist(unit)){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 产品单位 (" + unit + ") 不存在. ");
				}
			}
			
			try {
				   int numPerHand = (int) row.getCell(fullHandQ_column).getNumericCellValue();
				   if (numPerHand < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 齐手数量("+ numPerHand+")错误. ");
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 齐手数量 错误. ");
				}
			
			
			if (rowError == false){
				validate = false;
				errorMsg.append("<br>");
			}
		}
		
		isSuccess = validate;
		
		validateMsg =  errorMsg.toString();
	}
	
	/**
	 * 通过文件读出条码，返回
	 * @return
	 * @throws Exception 
	 */
	public void proccess(ProductBarcodeDaoImpl pbBarcodeDaoImpl,CategoryDaoImpl categoryDaoImpl, ProductUnitDaoImpl productUnitDaoImpl) throws Exception{
		validate(pbBarcodeDaoImpl, categoryDaoImpl, productUnitDaoImpl);
		
		if (!isSuccess)
			return ;

		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		for (int i = data_row; ;i++){
            boolean updated = false;
            
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				break;
			

			
			String barcode = ExcelUtil.getPuzzleString(row.getCell(barcode_column));
			Product product = null;
			if (barcode==null || barcode.length() != ProductBarcode.BARCODE_LENGTH){
				throw new Exception("第" + i + "行,格式错误");
			} else {
				ProductBarcode pb = pbBarcodeDaoImpl.getByBarcode(barcode);
				if (pb == null) {
					throw new Exception("第" + i + "行,条码错误");
				} else {
					product = pb.getProduct();
					if (wsData.contains(product))
						continue;
				}
			}
			
			String productCode = ExcelUtil.getPuzzleString(row.getCell(productCode_column));
			if (productCode!=null && productCode.length() != 0){
                if (Common_util.isContainChinese(productCode)){
					productCode = Common_util.filterChinese(productCode);
					if (productCode.length()== 0){
						throw new Exception("第" + i + "行,货号错误");
					}
				}
				
				if (!product.getProductCode().equals(productCode)){
					updated = true;
					product.setProductCode(productCode);
				}
			}
		
			HSSFCell categoryCell = row.getCell(category_column);
			if (categoryCell != null){
				String categoryString = categoryCell.getStringCellValue().trim();
				
				if (!categoryString.equals("") && !categoryDaoImpl.checkCategoryExist(categoryString)){
					Category newCategory = categoryDaoImpl.getCategoryByName(categoryString);
					
					if (product.getCategory().getCategory_ID() != newCategory.getCategory_ID()){
						product.setCategory(newCategory);
						updated = true;
					}
				}
			}
			
			try {
			   double recCost = row.getCell(recCost_column).getNumericCellValue();
			   if (recCost > 0) {
			     product.setRecCost(recCost);
			     updated = true;
			   }
			   } catch (NullPointerException nException){
			   } catch (Exception e){
			   }
			
			try {
				   double factorySalesPrice = row.getCell(factorySalesPrice_column).getNumericCellValue();
				   if (factorySalesPrice > 0) {
				       product.setSalesPriceFactory(factorySalesPrice);   
				       updated = true;
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){

				}
			
			try {
				   double discount = row.getCell(discount_column).getNumericCellValue();
				   if (discount < 0 || discount >1) {
				     product.setDiscount(discount);
				     updated = true;
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){

				}
			
			try {
				   double wholePrice = row.getCell(wholePrice_column).getNumericCellValue();
				   if (wholePrice > 0 ) {
				     product.setWholeSalePrice(wholePrice);
				     updated = true;
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
				}
			
			try {
				   double salePrice = row.getCell(salePrice_column).getNumericCellValue();
				   if (salePrice > 0) {
				     product.setSalesPrice(salePrice);
				     updated = true;
				   }
				} catch (NullPointerException nException){
				} catch (Exception e){
				}
			
			String unit = ExcelUtil.getPuzzleString(row.getCell(unit_column));
			
			if (unit != null && !unit.equals("")){
				if (productUnitDaoImpl.checkExist(unit)){
					product.setUnit(unit);
					updated = true;
				}
			}
			
			try {
				   int numPerHand = (int) row.getCell(fullHandQ_column).getNumericCellValue();
				   if (numPerHand > 0) {
				     product.setNumPerHand(numPerHand);
				     updated = true;
				   }
			    } catch (NullPointerException nException){
				} catch (Exception e){
				}
			
			if (updated && !wsData.contains(product)){
				
				wsData.add(product);
			}
		}
	}

	public boolean isSuccess() {
		return isSuccess;
	}


	public String getValidateMsg() {
		return validateMsg;
	}


	public Set<Product> getWsData() {
		return wsData;
	}



}
