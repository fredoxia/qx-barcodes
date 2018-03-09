package com.onlineMIS.ORM.DAO.headQ4Chain.barcodeGentor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.AreaDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.CategoryDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ColorDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.NumPerHandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductUnitDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.SizeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Area;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BarcodeTemplate;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.HeadQInputHelp;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ4Chain.barcodeGentor.ChainBarcodeTemplate;
import com.onlineMIS.action.headQ.barCodeGentor.BarcodeGenBasicData;
import com.onlineMIS.action.headQ4Chain.barcodeGentor.BarcodeGenActionFormBean;
import com.onlineMIS.action.headQ4Chain.barcodeGentor.BarcodeGenActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.sorter.SortYear;

/**
 * 这个服务类专门用作连锁店客户登陆之后去见条码的相关功能
 * @author fredo
 *
 */
@Service
public class BarcodeGenService {
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	@Autowired
	private AreaDaoImpl areaDaoImpl;
	@Autowired
	private YearDaoImpl yearDaoImpl;
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	@Autowired
	private CategoryDaoImpl categoryDaoImpl;
	@Autowired
	private ColorDaoImpl colorDaoImpl;

	@Autowired
	private ProductUnitDaoImpl productUnitDaoImpl;
	@Autowired
	private NumPerHandDaoImpl numPerHandDaoImpl;
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	@Autowired
	private ProductDaoImpl productDaoImpl;
	/**
	 * 准备连锁店创建条码的页面
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareGenBarcodeUI(BarcodeGenActionFormBean formBean, BarcodeGenActionUIBean uiBean){
 
		List<Year> yearList =  yearDaoImpl.getAll(true); 
		Collections.sort(yearList, new SortYear());

		List<Category> categoryList =  categoryDaoImpl.getChainCategry(true); 
		
		List<Quarter> quarterList =  quarterDaoImpl.getAll(true);  

		List<ProductUnit> unitList = productUnitDaoImpl.getAll(true);

		List<NumPerHand> numPerHandList = numPerHandDaoImpl.getAll(true);

		uiBean.setYears(yearList);
		uiBean.setCategories(categoryList);
		uiBean.setQuarters(quarterList);
		uiBean.setUnits(unitList);
		uiBean.setNumPerHands(numPerHandList);
	}

	public Response searchBrands(String brandName, ChainStore myChainStore) {
		Response response = new Response(Response.SUCCESS);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Brand.class);
		if (brandName != null && !brandName.trim().equals(""))
		    criteria.add(Restrictions.like("brand_Name", "%" + brandName + "%"));
		criteria.add(Restrictions.eq("chainStore.chain_id", myChainStore.getChain_id()));
		criteria.addOrder(Order.asc("brand_Name"));

		List<Brand> brands = new ArrayList<Brand>();
		try {
			brands = brandDaoImpl.getByCritera(criteria, true);
			response.setReturnValue(brands);
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, e.getMessage());
		}
		
		return response;
	}

	/**
	 * 检查 product code和serial number
	 * @param product
	 * @return
	 */
	public Response checkProductCodeSerial(Product product, ChainStore chainStore) {
		Response response = new Response();
		
		String serialNum = product.getSerialNum();
		String productCode = product.getProductCode();

		//1. check the serial number valid in the product table
		if (serialNum != null && !serialNum.trim().equals("")){
			Product product2 = productDaoImpl.getBySerialNum(serialNum, chainStore);
			if (product2 == null){
				response.setReturnCode(Response.FAIL);
				response.setMessage("此商品编码不存在于条码系统中");
				return response;
			}  else if (!product.equals(product2)) {
				response.setReturnCode(Response.FAIL);
				response.setMessage("此商品编码的基础资料和以前所建基础资料不同,如果要修改基础资料。请用修改条码功能");
				return response;
			} 
		}  else {
		
			List<Product> products = productDaoImpl.getProductsByProductCode(productCode, chainStore);
	
			if (products != null && products.size() >0){
				String msg = "";
				for (Product product2 : products){
					productDaoImpl.initialize(product2);
				    msg += "          " + product2.toString() + "\n";
				}
				
				response.setReturnCode(Response.WARNING);
				response.setMessage("系统内发现相同货号产品,请确认不是重复制作货品. : \n" + msg );
				return response;
			}
		}
		return response;
	}

	@Transactional
	public Response saveProduct(Product product, List<Integer> colorIds,
			List<Integer> sizeIds, ChainStore chainStore) {
		Response response = new Response();

		String serialNum = product.getSerialNum();

		/**
		 * 3.0 if it is existing product, no need to save information again, save the product information
		 */
		product.setChainStore(chainStore);
		if (serialNum == null || serialNum.trim().equals("")){
	    	double discount = product.getDiscount();
	    	if (discount <= 0 || discount >1)
	    		product.setDiscount(1);
	    	
			productDaoImpl.save(product, true);
			product.setSerialNum(String.valueOf(product.getProductId()));
			productDaoImpl.update(product, true);
		} else {
			product = productDaoImpl.getBySerialNum(serialNum, chainStore);
		}
				
		/**
		 * 4.0 generate the barcode
		 */
		List<Color> colors = new ArrayList<Color>();
		List<Size> sizes = new ArrayList<Size>();
		
		if (colorIds == null || colorIds.size()==0)
			colors.add(null);
		else{
			for (Integer colorId: colorIds){
				if (colorId != 0)
					colors.add(new Color(colorId));
			}
		}
		
		if (sizeIds == null || sizeIds.size() == 0)
			sizes.add(null);
		else {
			for (Integer sizeId: sizeIds){
				if (sizeId != 0)
					sizes.add(new Size(sizeId));
			}
		}
			
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getBarcodeFromProduct(product.getProductId());
		
		for (Color color: colors){
			for (Size size: sizes){	
				//save the productBarcode information
				ProductBarcode productBarcode = new ProductBarcode(product, color, size);
				productBarcode.setChainStore(chainStore);
				if (!productBarcodes.contains(productBarcode)){
					productBarcodeDaoImpl.save(productBarcode, true);
					
					//generate the barcode
					int id = productBarcode.getId();
					String barcode = ProductBarcodeService.generateBarcode(id,chainStore);
					productBarcode.setBarcode(barcode);
					productBarcodeDaoImpl.update(productBarcode, true);
				}
			}
		}	
		
		response.setReturnCode(Response.SUCCESS);
		response.setMessage("成功生成条形码");
		
		return response;
		
	}

	@Transactional
	public Response getSameGroupProductBarcodes(Product product, ChainStore chainStore) {
        Response response = new Response();
		
        try {
			List<ProductBarcode> barcodes = productBarcodeDaoImpl.getProductBracodeFromSameGroup(product,chainStore);
			for (ProductBarcode barcode: barcodes)
			      productBarcodeDaoImpl.initialize(barcode);
			response.setReturnValue(barcodes);
        } catch (Exception e) {
        	loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}

		return response;
	}

	public Response getProductsByBarcode(String barcode,
			ChainStore myChainStore) {
		Response response = new Response();

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class);
			criteria.add(Restrictions.eq("barcode", barcode));
			if (myChainStore != null)
				criteria.add(Restrictions.eq("chainStore.chain_id", myChainStore.getChain_id()));
		    List<ProductBarcode> barcodes = productBarcodeDaoImpl.getByCritera(criteria, true);
		    if (barcodes != null && barcodes.size() == 1){
		    	response.setReturnValue(barcodes.get(0));
		    } else 
		    	response.setQuickValue(Response.FAIL, "无法在系统中找到当前条码");
		} catch (Exception e) {
			response.setQuickValue(Response.FAIL, "发生错误 : " + e.getMessage());
		}
		return response;
	}

	public Response searchBarcodeForChain(ProductBarcode productBarcode,
			Date startDate, Date endDate, String createDate,
			ChainStore myChainStore) {
		List<ProductBarcode> barcodes = new ArrayList<ProductBarcode>();
		Response response = new Response();
		try {
			DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
			DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");

			if (myChainStore != null){
				productBarcodeCriteria.add(Restrictions.eq("chainStore.chain_id", myChainStore.getChain_id()));
				productCriteria.add(Restrictions.eq("chainStore.chain_id", myChainStore.getChain_id()));
			}
			if (createDate != null && !createDate.equals("")){
	        	java.util.Date endDate2 = new java.util.Date(endDate.getTime());
	        	endDate2.setHours(23);
	        	endDate2.setMinutes(59);
	        	endDate2.setSeconds(59);
				productBarcodeCriteria.add(Restrictions.between("createDate",startDate,endDate2));
	        }
	        
	        if (!productBarcode.getBarcode().trim().equals(""))
	        	productBarcodeCriteria.add(Restrictions.eq("barcode", productBarcode.getBarcode()));
	        else {
		        if (!productBarcode.getProduct().getProductCode().trim().equals(""))
				    productCriteria.add(Restrictions.like("productCode", productBarcode.getProduct().getProductCode(),MatchMode.ANYWHERE));
		        
				if (productBarcode.getProduct().getYear().getYear_ID() != Common_util.ALL_RECORD)
					productCriteria.add(Restrictions.eq("year.year_ID", productBarcode.getProduct().getYear().getYear_ID()));
				if (productBarcode.getProduct().getQuarter().getQuarter_ID() != Common_util.ALL_RECORD)
					productCriteria.add(Restrictions.eq("quarter.quarter_ID", productBarcode.getProduct().getQuarter().getQuarter_ID()));
				if (productBarcode.getProduct().getBrand().getBrand_ID() != Common_util.ALL_RECORD && productBarcode.getProduct().getBrand().getBrand_ID() != 0){
					productCriteria.add(Restrictions.eq("brand.brand_ID", productBarcode.getProduct().getBrand().getBrand_ID()));
				}
				if (productBarcode.getProduct().getCategory().getCategory_ID() != Common_util.ALL_RECORD)
					productCriteria.add(Restrictions.eq("category.category_ID", productBarcode.getProduct().getCategory().getCategory_ID()));
	        }

	        productBarcodeCriteria.add(Restrictions.eq("status", ProductBarcode.STATUS_OK));
	        
			barcodes =  productBarcodeDaoImpl.getByCritera(productCriteria,true);
			
			if (barcodes.size() > 50){
				response.setQuickValue(Response.WARNING, "返回条码数量太大,超过50条条码。下次请你缩小搜索条码的限制，否则系统会自动暂停你制作条码的功能。比如,将录入时间缩短,选择品牌,或者输入货号");
				loggerLocal.warn(myChainStore.toString() + " 大批量搜索条码");
			}
			response.setReturnValue(barcodes);
		} catch (Exception e) {
			e.printStackTrace();
			response.setFail(e.getMessage());
		}

		return response;
	}

	public Map<String, Object> generateBarcodeInExcel(
			List<String> selectedBarcodes, String filePath) {
	Map<String,Object> returnMap=new HashMap<String, Object>();   

        
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			
			//to get the order information from database
			List<ProductBarcode> products = new ArrayList<ProductBarcode>();
			
			if (selectedBarcodes != null && selectedBarcodes.size()>0){
				DetachedCriteria productCriteria = DetachedCriteria.forClass(ProductBarcode.class,"p");
				productCriteria.add(Restrictions.in("p.barcode", selectedBarcodes));
		        
				products = productBarcodeDaoImpl.getByCritera(productCriteria,true);
		    }
			
//			initializeProductBarcode(products);
			
			ChainBarcodeTemplate barcodeTemplate = new ChainBarcodeTemplate(products, filePath);
			
			wb = barcodeTemplate.process();

			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
		        e.printStackTrace();   
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
         
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);    
		 }   
		return null;   
	}

	@Transactional
	public Response updateChainProduct(ProductBarcode productBarcode, ChainStore chainStore) {
		Response response = new Response();
		
        Product product = productDaoImpl.get(productBarcode.getProduct().getProductId(), true);
        if (product == null){
        	response.setFail("无法找到当前产品，请检查之后重新查询");
        } else {
        	if (product.getChainStore() == null)
        		response.setFail("你没有权限修改总部条码");
        	else if (product.getChainStore().getChain_id() != chainStore.getChain_id())
        	    response.setFail("你没有权限修改其他连锁店条码");
        	else {
        		try {
		        	Product newProduct = productBarcode.getProduct();
		        	product.setNumPerHand(newProduct.getNumPerHand());
		        	product.setProductCode(newProduct.getProductCode());
		        	product.setSalesPrice(newProduct.getSalesPrice());
		        	product.setWholeSalePrice(newProduct.getWholeSalePrice());
		        	product.setUnit(newProduct.getUnit());
		        	product.setCategory(newProduct.getCategory());
		        	product.setYear(newProduct.getYear());
		        	product.setQuarter(newProduct.getQuarter());
		        	product.setBrand(newProduct.getBrand());
	
		        	productDaoImpl.saveOrUpdate(product,true);
        		} catch (Exception e) {
					response.setFail(e.getMessage());
				}
        	}
        }

		return response;
	}

	/**
	 * 连锁店用户继续为商品添加其他颜色
	 * @param productBarcode
	 * @param myChainStore
	 * @return
	 */
	public Response continueCreateBarcode(ProductBarcode pb,
			ChainStore myChainStore) {
		Response response = new Response();
		
		String serialNum = pb.getProduct().getSerialNum();
		
		Product product = productDaoImpl.getBySerialNum(serialNum, myChainStore);
		
		if (product == null){
			response.setFail("找不到产品信息");
		} else {
			int productId = product.getProductId();	
			List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getBarcodeFromProduct(productId);
			  
			String colors = "";
			if (productBarcodes != null && productBarcodes.size() > 0){
				colors = "此款货品已经添加的颜色 : ";
				for (ProductBarcode productBarcode: productBarcodes)
					if (productBarcode.getColor() != null)
					    colors += productBarcode.getColor().getName() + " ";
			}
			
			
			Map dataMap = new HashMap<String, Object>();
			dataMap.put("product", product);
			dataMap.put("color", colors);
			
			response.setReturnValue(dataMap);
			response.setReturnCode(Response.SUCCESS);
	    }
		
		return response;
	}

	public Response deleteChainBarcode(ProductBarcode productBarcode,
			ChainStore myChainStore) {
		Response response = new Response();
		try {
			ProductBarcode pb = productBarcodeDaoImpl.get(productBarcode.getId(), true);
			if (pb.getChainStore().getChain_id() != myChainStore.getChain_id()){
				response.setFail("你无权删除其他连锁店条码");
			} else {
				productBarcodeDaoImpl.updateToDelete(productBarcode.getId());
			}
				
			response.setSuccess("成功删除条码");
		} catch (Exception e) {
			response.setFail("删除条码发生错误 : " + e.getMessage());
		}
		return response;
	}

	public Response getBasicDataList(String basicData, int page, int rowPerPage,
			String sort, String sortOrder, ChainStore chainStore) {
		Response response = new Response();
		Map dataMap = new HashMap();
		
		if (basicData.equalsIgnoreCase("brand")){
			//1. 获取总条数
			DetachedCriteria brandTotalCriteria = DetachedCriteria.forClass(Brand.class);
			brandTotalCriteria.add(Restrictions.eq("chainStore.chain_id", chainStore.getChain_id()));
			brandTotalCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(brandDaoImpl.getByCriteriaProjection(brandTotalCriteria, true));
			
			//2. 获取当页数据
			DetachedCriteria brandCriteria = DetachedCriteria.forClass(Brand.class);
			brandCriteria.add(Restrictions.eq("chainStore.chain_id", chainStore.getChain_id()));
			
			if (sortOrder.equalsIgnoreCase("desc"))
				brandCriteria.addOrder(Order.desc(sort));
			else 
				brandCriteria.addOrder(Order.asc(sort));
			
			List<Brand> brands = brandDaoImpl.getByCritera(brandCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
			
			dataMap.put("rows", brands);
			dataMap.put("total", total);
			response.setReturnValue(dataMap);
		} else {
			response.setFail("无法添加选择的条码基础资料");
		}
		return response;
	}

	@Transactional
	public Response updateBrand(Brand brand, ChainStore myChainStore) {
	    Response response = new Response();
		
		try {
			int brandId = brand.getBrand_ID();
			String brandName = brand.getBrand_Name();
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Brand.class);
			criteria.add(Restrictions.eq("brand_Name", brandName));
			
			if (myChainStore != null)
			    criteria.add(Restrictions.eq("chainStore.chain_id", myChainStore.getChain_id()));
			else 
				criteria.add(Restrictions.isNull("chainStore.chain_id"));
			
			List<Brand> brands = brandDaoImpl.getByCritera(criteria, true);
			
			if (brands == null || brands.size() == 0){
				brand.setBrand_Code(String.valueOf(brandId).substring(0,1));
				brand.setPinyin(Common_util.getPinyinCode(brandName, true));
				brand.setChainStore(myChainStore);
				brandDaoImpl.saveOrUpdate(brand, true);
				response.setReturnCode(Response.SUCCESS);
			} else {
				Brand brand2 = brands.get(0);
				if (brandId != brand2.getBrand_ID()){
					response.setQuickValue(Response.FAIL, "品牌 重复,请检查重新输入");
				} else {
					brandDaoImpl.evict(brand2);
					brand.setBrand_Code(String.valueOf(brandId).substring(0,1));
					brand.setPinyin(Common_util.getPinyinCode(brandName, true));
					brand.setChainStore(myChainStore);
					brandDaoImpl.saveOrUpdate(brand, true);
					response.setReturnCode(Response.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setFail(e.getMessage());
		}
		
		return response;
	}

	public Response getBasicData(String basicData, Integer basicDataId, ChainStore myChainStore) {
	    Response response = new Response();
		
	    if (basicDataId != null)
			if (basicData.equalsIgnoreCase("brand")){
				Brand brand = brandDaoImpl.get(basicDataId, true);
				if (brand == null || brand.getChainStore().getChain_id() != myChainStore.getChain_id())
					response.setFail("无法找到对应的基础资料");
				else 
					response.setReturnValue(brand);
			} else 
				response.setFail("无法找到对应的基础资料");
			
		return response;
	}

}
