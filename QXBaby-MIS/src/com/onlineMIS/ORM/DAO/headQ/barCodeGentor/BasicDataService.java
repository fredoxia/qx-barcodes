package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.ColorMSDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.SizeMSDAOImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.headQ.barCodeGentor.BasicDataActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;

@Service
public class BasicDataService {
	@Autowired
    private BrandDaoImpl brandDaoImpl;
	@Autowired
    private CategoryDaoImpl categoryDaoImpl;
	@Autowired
	private ColorDaoImpl colorDaoImpl;

	@Autowired
	private SizeDaoImpl sizeDaoImpl;
	@Autowired
	private YearDaoImpl yearDaoImpl;
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	@Autowired
	private ProductUnitDaoImpl productUnitDaoImpl;
	@Autowired
	private NumPerHandDaoImpl numPerHandDaoImpl;
	@Autowired
	private ColorMSDAOImpl colorMSDAOImpl;
	@Autowired
	private SizeMSDAOImpl sizeMSDAOImpl;

	/**
	 * get the brand information by id
	 * @param id
	 * @return
	 */
	public Brand getBrand(int id){
		Brand brand = brandDaoImpl.get(id,true);

		return brand;
	}
	
	/**
	 * save or update brand inforamtion
	 * @param brand
	 */
	public void saveOrUpdateBrand(Brand brand) {
		this.brandDaoImpl.saveOrUpdate(brand,true);
		
	}
	
	/**
	 * save or update category inforamtion
	 * @param category
	 */
	public void saveOrUpdateCategory(Category category) {
		this.categoryDaoImpl.saveOrUpdate(category,true);
		
	}

	/**
	 * get category information by id
	 * @param id
	 * @return
	 */
	public Category getCategory(int id) {
		Category category = categoryDaoImpl.get(id,true);
		return category;
	}




	/**
	 * 准备List UI数据
	 * @param uiBean
	 * @param basicData
	 */
	public Response prepareListUI(String basicData, Integer page, Integer rowPerPage, String sort, String sortOrder, Brand brand, Category category) {
		Response response = new Response();
		Map dataMap = new HashMap();

		if (basicData.equalsIgnoreCase("quarter")){
			//1. 获取总条数
			DetachedCriteria quarterTotalCriteria = DetachedCriteria.forClass(Quarter.class);
			quarterTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(quarterDaoImpl.getByCriteriaProjection(quarterTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria quarterCriteria = DetachedCriteria.forClass(Quarter.class);
			List<Quarter> brands = quarterDaoImpl.getByCritera(quarterCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", brands);
			dataMap.put("total", total);
		} else if (basicData.equalsIgnoreCase("brand")){
			//1. 获取总条数
			DetachedCriteria brandTotalCriteria = DetachedCriteria.forClass(Brand.class);
			if (brand != null) {
				String brandName = brand.getBrand_Name().trim();
				String supplier = brand.getSupplier().trim();
				String comment = brand.getComment().trim();
				
				if (!brandName.isEmpty())
					brandTotalCriteria.add(Restrictions.ilike("pinyin", "%"+brandName+"%"));
				if (!supplier.isEmpty())
					brandTotalCriteria.add(Restrictions.ilike("supplier", "%"+supplier+"%"));
				if (!comment.isEmpty())
					brandTotalCriteria.add(Restrictions.like("comment", "%"+comment+"%"));
			}
			brandTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(brandDaoImpl.getByCriteriaProjection(brandTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria brandCriteria = DetachedCriteria.forClass(Brand.class);
			if (brand != null) {
				String brandName = brand.getBrand_Name().trim();
				String supplier = brand.getSupplier().trim();
				String comment = brand.getComment().trim();
				
				if (!brandName.isEmpty())
					brandCriteria.add(Restrictions.ilike("pinyin", "%"+brandName+"%"));
				if (!supplier.isEmpty())
					brandCriteria.add(Restrictions.ilike("supplier", "%"+supplier+"%"));
				if (!comment.isEmpty())
					brandCriteria.add(Restrictions.like("comment", "%"+comment+"%"));
			}
			if (sortOrder.equalsIgnoreCase("desc"))
				brandCriteria.addOrder(Order.desc(sort));
			else 
				brandCriteria.addOrder(Order.asc(sort));
			List<Brand> brands = brandDaoImpl.getByCritera(brandCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", brands);
			dataMap.put("total", total);
		} else if (basicData.equalsIgnoreCase("category")){
			//1. 获取总条数
			DetachedCriteria categoryTotalCriteria = DetachedCriteria.forClass(Category.class);
			if (category != null) {
				String categoryName = category.getCategory_Name().trim();
				
				if (!categoryName.isEmpty())
					categoryTotalCriteria.add(Restrictions.like("category_Name", "%"+categoryName+"%"));
			}
			categoryTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(categoryDaoImpl.getByCriteriaProjection(categoryTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria categoryCriteria = DetachedCriteria.forClass(Category.class);
			if (category != null) {
				String categoryName = category.getCategory_Name().trim();
				
				if (!categoryName.isEmpty())
					categoryCriteria.add(Restrictions.like("category_Name", "%"+categoryName+"%"));
			}
			List<Category> categories = categoryDaoImpl.getByCritera(categoryCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", categories);
			dataMap.put("total", total);
		} else if (basicData.equalsIgnoreCase("color")){ 
			//1. 获取总条数
			DetachedCriteria colorTotalCriteria = DetachedCriteria.forClass(Color.class);
			colorTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(colorDaoImpl.getByCriteriaProjection(colorTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria colorCriteria = DetachedCriteria.forClass(Color.class);
			if (sortOrder.equalsIgnoreCase("desc"))
				colorCriteria.addOrder(Order.desc(sort));
			else 
				colorCriteria.addOrder(Order.asc(sort));
			List<Color> colors = colorDaoImpl.getByCritera(colorCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", colors);
			dataMap.put("total", total);
		} else if (basicData.equalsIgnoreCase("productUnit")){ 
			//1. 获取总条数
			DetachedCriteria productUnitTotalCriteria = DetachedCriteria.forClass(ProductUnit.class);
			productUnitTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(productUnitDaoImpl.getByCriteriaProjection(productUnitTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria productUnitCriteria = DetachedCriteria.forClass(ProductUnit.class);
			List<ProductUnit> productUnits = productUnitDaoImpl.getByCritera(productUnitCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", productUnits);
			dataMap.put("total", total);
		} else if (basicData.equalsIgnoreCase("numPerHand")){ 
			//1. 获取总条数
			DetachedCriteria numPerHandTotalCriteria = DetachedCriteria.forClass(NumPerHand.class);
			numPerHandTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(numPerHandDaoImpl.getByCriteriaProjection(numPerHandTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria numPerHandCriteria = DetachedCriteria.forClass(NumPerHand.class);
			List<NumPerHand> numPerHands = numPerHandDaoImpl.getByCritera(numPerHandCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", numPerHands);
			dataMap.put("total", total);
		} else {
			//1. 获取总条数
			DetachedCriteria yearTotalCriteria = DetachedCriteria.forClass(Year.class);
			yearTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(yearDaoImpl.getByCriteriaProjection(yearTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria yearCriteria = DetachedCriteria.forClass(Year.class);
			List<Year> years = yearDaoImpl.getByCritera(yearCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", years);
			dataMap.put("total", total);
		}
		
		response.setReturnValue(dataMap);
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

	public Object getBasicData(String basicData, Integer basicDataId) {
		if (basicDataId != null)
			if (basicData.equalsIgnoreCase("quarter")){
				Quarter quarter = quarterDaoImpl.get(basicDataId, true);
				return quarter;
			} else if (basicData.equalsIgnoreCase("brand")){
				Brand brand = brandDaoImpl.get(basicDataId, true);
				return brand;	
			} else if (basicData.equalsIgnoreCase("category")){
				Category category = categoryDaoImpl.get(basicDataId, true);
				return category;
			} else if (basicData.equalsIgnoreCase("color")){ 
				Color color = colorDaoImpl.get(basicDataId, true);
				return color;
			} else if (basicData.equalsIgnoreCase("year")){ 
				Year year = yearDaoImpl.get(basicDataId, true);
				return year;
			} else if (basicData.equalsIgnoreCase("numPerHand")){
                NumPerHand numPerHand = numPerHandDaoImpl.get(basicDataId, true);
				return numPerHand;	
			} else if (basicData.equalsIgnoreCase("productUnit")){ 
				ProductUnit productUnit = productUnitDaoImpl.get(basicDataId, true);
				return productUnit;
			}
			
		return null;
	}

	@Transactional
	public Response updateYear(Year year) {
		Response response = new Response();
		
		int yearId = year.getYear_ID();
		String yearS = year.getYear();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Year.class);
		criteria.add(Restrictions.eq("year", yearS));
		List<Year> years = yearDaoImpl.getByCritera(criteria, true);
		
		if (years == null || years.size() == 0){
			year.setYear_Code(yearS.substring(3));
			yearDaoImpl.saveOrUpdate(year, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			Year year2 = years.get(0);
			if (yearId != year2.getYear_ID()){
				response.setQuickValue(Response.FAIL, "年份 重复,请检查重新输入");
			} else {
				year.setYear_Code(yearS.substring(3));
				yearDaoImpl.saveOrUpdate(year, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}

	@Transactional
	public Response updateQuarter(Quarter quarter) {
		Response response = new Response();
		
		int quarterId = quarter.getQuarter_ID();
		String quarterS = quarter.getQuarter_Name();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Quarter.class);
		criteria.add(Restrictions.eq("quarter_Name", quarterS));
		List<Quarter> quarters = quarterDaoImpl.getByCritera(criteria, true);
		
		if (quarters == null || quarters.size() == 0){
			quarter.setQuarter_Code(String.valueOf(quarterId).substring(0,1));
			quarterDaoImpl.saveOrUpdate(quarter, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			Quarter quarter2 = quarters.get(0);
			if (quarterId != quarter2.getQuarter_ID()){
				response.setQuickValue(Response.FAIL, "季度 重复,请检查重新输入");
			} else {
				quarter.setQuarter_Code(String.valueOf(quarterId).substring(0,1));
				quarterDaoImpl.saveOrUpdate(quarter, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}

	@Transactional
	public synchronized Response updateBrand(Brand brand) {
		Response response = new Response();
		
		try {
			int brandId = brand.getBrand_ID();
			String brandName = brand.getBrand_Name().trim();
			String comment = brand.getComment().trim();
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Brand.class);
			criteria.add(Restrictions.ne("brand_ID",brandId));
			criteria.add(Restrictions.or(Restrictions.eq("brand_Name", brandName), Restrictions.eq("comment", comment)));
			criteria.add(Restrictions.isNull("chainStore.chain_id"));
			
			List<Brand> brands = brandDaoImpl.getByCritera(criteria, true);

			if (brands == null || brands.size() == 0){
				System.out.println(Brand.START_CHAR);
				if (brandName.equalsIgnoreCase(Brand.START_CHAR)) {
					brandName += (getMaxBrandNum()+1);
					brand.setBrand_Name(brandName);
				}
					
				
				brand.setBrand_Code(String.valueOf(brandId).substring(0,1));
				brand.setPinyin(Common_util.getPinyinCode(brandName, true));
				
				brandDaoImpl.saveOrUpdate(brand, true);
				response.setReturnCode(Response.SUCCESS);
			} else {
				Brand brand2 = brands.get(0);
				if (brandName.equalsIgnoreCase(brand2.getBrand_Name()))
					response.setQuickValue(Response.FAIL, "品牌 重复,请检查重新输入");
				else if (comment.equalsIgnoreCase(brand2.getComment()))
					response.setQuickValue(Response.FAIL, "品牌备注 重复,请检查重新输入");
				else 
					response.setQuickValue(Response.FAIL, "品牌或者备注 重复,请检查重新输入");

			}
		} catch (Exception e) {
			response.setFail(e.getMessage());
		}
		
		return response;
	}
	
	private int getMaxBrandNum() {
		DetachedCriteria brandCriteria = DetachedCriteria.forClass(Brand.class);
		brandCriteria.add(Restrictions.like("brand_Name", Brand.START_CHAR+"%"));
		List<Brand> brands = brandDaoImpl.getByCritera(brandCriteria, true);
		
		int maxBrandNum = 0;
		for (Brand brand : brands) {
			String brandName = brand.getBrand_Name();
			String brandNumS = brandName.replace(Brand.START_CHAR, "").trim();
			if (!brandNumS.isEmpty()) {
				int brandNum = 0;
				try {
				    brandNum = Integer.parseInt(brandNumS);
				} catch (Exception e) {
//					loggerLocal.error(e);
				}
				
				if (brandNum > maxBrandNum)
					maxBrandNum = brandNum;
			}
			brandDaoImpl.evict(brand);
		}
		
		return maxBrandNum;
	}

	@Transactional
	public Response updateCategory(Category category) {
		Response response = new Response();
		
		int categoryId = category.getCategory_ID();
		String categoryName = category.getCategory_Name();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.add(Restrictions.eq("category_Name", categoryName));
		List<Category> categories = categoryDaoImpl.getByCritera(criteria, true);
		
		if (categories == null || categories.size() == 0){
			category.setCategory_Code(String.valueOf(categoryId).substring(0,1));
			categoryDaoImpl.saveOrUpdate(category, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			Category category2 = categories.get(0);
			if (categoryId != category2.getCategory_ID()){
				response.setQuickValue(Response.FAIL, "类别 重复,请检查重新输入");
			} else {
                category2.setCategory_Name(category.getCategory_Name());
                category2.setChainId(category.getChainId());
				categoryDaoImpl.saveOrUpdate(category2, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}

	public Response updateProductUnit(ProductUnit productUnit) {
		Response response = new Response();
		
		int id = productUnit.getId();
		String unit = productUnit.getProductUnit();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductUnit.class);
		criteria.add(Restrictions.eq("productUnit", unit));
		List<ProductUnit> units = productUnitDaoImpl.getByCritera(criteria, true);
		
		if (units == null || units.size() == 0){
			productUnitDaoImpl.saveOrUpdate(productUnit, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			ProductUnit unit2 = units.get(0);
			if (id != unit2.getId()){
				response.setQuickValue(Response.FAIL, "货品单位 重复,请检查重新输入");
			} else {
				productUnitDaoImpl.saveOrUpdate(productUnit, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}

	public Response updateNumPerHand(NumPerHand numPerHand) {
		Response response = new Response();
		
		int id = numPerHand.getId();
		int num = numPerHand.getNumPerHand();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NumPerHand.class);
		criteria.add(Restrictions.eq("numPerHand", num));
		List<NumPerHand> numPerHands = numPerHandDaoImpl.getByCritera(criteria, true);
		
		if (numPerHands == null || numPerHands.size() == 0){
			numPerHandDaoImpl.saveOrUpdate(numPerHand, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			NumPerHand num2 = numPerHands.get(0);
			if (id != num2.getId()){
				response.setQuickValue(Response.FAIL, "齐码数量 重复,请检查重新输入");
			} else {
				numPerHandDaoImpl.saveOrUpdate(numPerHand, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}

	public Response updateColor(Color color) {
		Response response = new Response();
		
		int id = color.getColorId();
		String colorName = color.getName();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Color.class);
		criteria.add(Restrictions.eq("colorName", colorName));
		List<Color> colors = colorDaoImpl.getByCritera(criteria, true);
		
		if (colors == null || colors.size() == 0){
			String code = Common_util.getPinyinCode(color.getName(), true);
			color.setCode(code);
			colorDaoImpl.saveOrUpdate(color, true);
			response.setReturnCode(Response.SUCCESS);
		} else {
			Color colorName2 = colors.get(0);
			if (id != colorName2.getColorId()){
				response.setQuickValue(Response.FAIL, "相同颜色 重复,请检查重新输入");
			} else {
				String code = Common_util.getPinyinCode(color.getName(), true);
				color.setCode(code);
				colorDaoImpl.saveOrUpdate(color, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		
		return response;
	}
 
	
	/**
	 * to synchnize the color information from the jinsuan
	 * @return
	 */
	public Response synchnizeColor() {
		Response response = new Response();
		List<Color> colorsMS = colorMSDAOImpl.getAll(true);
		
		colorDaoImpl.deleteAll();
		
		for (Color color : colorsMS){
			String colorName = color.getName();
			if (colorName.indexOf("/") == -1){
			   String colorNamePinyin = Common_util.getPinyinCode(colorName, true);
			   color.setCode(colorNamePinyin);
		       colorDaoImpl.save(color, true);
			}
		}
		
		response.setReturnCode(Response.SUCCESS);
		return response;
	}
	
	/**
	 * to synchnize the size information from the jinsuan
	 * @return
	 */
	public Response synchnizeSize() {
		Response response = new Response();
		List<Size> sizesMS = sizeMSDAOImpl.getAll(true);
		
		sizeDaoImpl.deleteAll();
		
		for (Size size : sizesMS){
			String sizeName = size.getName();
			String sizeNamePinyin = Common_util.getPinyinCode(sizeName, true);
			size.setCode(sizeNamePinyin);
		    sizeDaoImpl.save(size, true);
		}
		
		response.setReturnCode(Response.SUCCESS);
		return response;
	}
}
