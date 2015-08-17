package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.HashMap;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

public class BasicDataJSPAction extends BasicDataAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4965927215338212593L;
	/**
	 * 改变basic data下拉菜单时候，改动
	 * @return
	 */
	public String changeBasicData(){
		String basicData = formBean.getBasicData();
		
		//Map saleReport = new HashMap();
		//basicDataService.prepareListUI(basicData,this.getPage(), this.getRows(), this.getSort(), this.getOrder(),saleReport);
		
		if (basicData.equalsIgnoreCase("quarter")){
			return "listQuarter";
		} else if (basicData.equalsIgnoreCase("brand")){
			return "listBrand";	
		} else if (basicData.equalsIgnoreCase("category")){
			return "listCategory";	
		} else if (basicData.equalsIgnoreCase("color")){ 
			return "listColor";	
		} else if (basicData.equalsIgnoreCase("numPerHand")){
			return "listNumPerHand";	
		} else if (basicData.equalsIgnoreCase("productUnit")){ 
			return "listProductUnit";				
		} else {
			return "listYear";	
		}
	}

	
	public String preMaintainBasic(){
		loggerLocal.info("MaintainBarCodeDataAction");
		
		return "preMaintain";
		
	}
	
	/**
	 * 准备basic data的页面
	 * @return
	 */
	public String preAddBasicData(){
		String basicData = formBean.getBasicData();
		
		//Map saleReport = new HashMap();
		Object object = null ;
		object = basicDataService.getBasicData(basicData, formBean.getBasicDataId());
		
		if (basicData.equalsIgnoreCase("quarter")){
			if (object != null)
				formBean.setQuarter((Quarter)object);
			return "updateQuarter";
		} else if (basicData.equalsIgnoreCase("brand")){
			if (object != null)
				formBean.setBrand((Brand)object);
			return "updateBrand";	
		} else if (basicData.equalsIgnoreCase("category")){
			if (object != null)
				formBean.setCategory((Category)object);
			return "updateCategory";	
		} else if (basicData.equalsIgnoreCase("color")){ 
			if (object != null)
				formBean.setColor((Color)object);
			return "updateColor";	
		} else if (basicData.equalsIgnoreCase("year")){
			if (object != null)
				formBean.setYear((Year)object);
			else 
				formBean.getYear().setYear(String.valueOf((Common_util.getToday().getYear() + 1900)));
			return "updateYear";	
		} else if (basicData.equalsIgnoreCase("numPerHand")){
			if (object != null)
				formBean.setNumPerHand((NumPerHand)object);
			return "updateNumPerHand";	
		} else if (basicData.equalsIgnoreCase("productUnit")){ 
			if (object != null)
				formBean.setProductUnit((ProductUnit)object);
			return "updateProductUnit";	
		}
		
		return ERROR;
	}
	
	/**
	 * save or update the brand information
	 * @return
	 */
	public String saveUpdateBrand(){
		loggerLocal.info("CreateBrandAction - update");
		
		basicDataService.saveOrUpdateBrand(formBean.getBrand());
		
		return "updateSuccess";
	}
	
	/**
	 * preload the brand inforamtion and inject it in the bean
	 * @return
	 */
	public String preUpdateBrand(){
		loggerLocal.info("BasicDataAction - preUpdateBrand");
		
		int brand_id = formBean.getBrand().getBrand_ID();
		formBean.setBrand(basicDataService.getBrand(brand_id));
		
		return "brand";
	}


	/**
	 * save or update the brand information
	 * @return
	 */
	public String saveUpdateCategory(){
		loggerLocal.info("BasicDataAction - saveUpdateCategory");
		
		basicDataService.saveOrUpdateCategory(formBean.getCategory());
		
		return "updateSuccess";
	}
	
	/**
	 * preload the brand inforamtion and inject it in the bean
	 * @return
	 */
	public String preUpdateCategory(){
		loggerLocal.info("BasicDataAction - preUpdateBrand");
		
		int category_id = formBean.getCategory().getCategory_ID();
		formBean.setCategory(basicDataService.getCategory(category_id));
		
		return "category";
	}
	

}
