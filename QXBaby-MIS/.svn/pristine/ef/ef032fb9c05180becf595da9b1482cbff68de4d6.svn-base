package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Repository
public class ProductBarcodeDaoImpl  extends BaseDAO<ProductBarcode> {
	@Autowired
	private ProductDaoImpl productDaoImpl;
	@Autowired
	private ColorDaoImpl colorDaoImpl;
	@Autowired
	private SizeDaoImpl sizeDaoImpl;
	
	public int updateToDelete(int pbId){
		String queryString = "UPDATE ProductBarcode p SET p.status = ? where p.id = ?";
		
		Object[] values = {ProductBarcode.STATUS_DELETE, pbId};
		
        return this.executeHQLUpdateDelete(queryString, values, true);
	}
	
	public ProductBarcode getByBarcode(String barcode){
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class);
		criteria.add(Restrictions.eq("barcode", barcode));
		criteria.add(Restrictions.eq("status", ProductBarcode.STATUS_OK));
		
		List<ProductBarcode> productBarcodes =  this.getByCritera(criteria,true);
		
		if (productBarcodes != null && productBarcodes.size() != 0){
			return productBarcodes.get(0);
		} else 
			return null;
	}

	public Map<String, ProductBarcode> getProductMapByBarcode(Set<String> barcodes){
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class);

		criteria.add(Restrictions.in("barcode",barcodes));
		
		List<ProductBarcode> productBarcodes = this.getByCritera(criteria, false);
		
		System.out.println(productBarcodes.size());
		Map<String, ProductBarcode> productMap = new HashMap<String, ProductBarcode>();
		
		if (productBarcodes != null){
			for (ProductBarcode productBarcode : productBarcodes){
				productMap.put(productBarcode.getBarcode(), productBarcode);
			}
		}
		
		return productMap;
	}
	
	public List<ProductBarcode> getProductBarcodes(Set<String> barcodes){
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class,"pb");
		
		criteria.add(Restrictions.in("pb.barcode", barcodes));
		
		return this.getByCritera(criteria, false);
	}
	
	/**
	 * to get one product's barcodes
	 * @param serialNum
	 * @return
	 */
	public List<ProductBarcode> getProductBracodeFromSameGroup(Product product, ChainStore chainStore) {
		DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
		
		productBarcodeCriteria.add(Restrictions.eq("status", ProductBarcode.STATUS_OK));
		DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");
		productCriteria.add(Restrictions.eq("area.area_ID", product.getArea().getArea_ID()));
		productCriteria.add(Restrictions.eq("year.year_ID", product.getYear().getYear_ID()));
		productCriteria.add(Restrictions.eq("quarter.quarter_ID", product.getQuarter().getQuarter_ID()));
		productCriteria.add(Restrictions.eq("brand.brand_ID", product.getBrand().getBrand_ID()));
		productCriteria.add(Restrictions.eq("category.category_ID", product.getCategory().getCategory_ID()));
		productCriteria.add(Restrictions.eq("productCode", product.getProductCode()));
		if (chainStore != null)
			productBarcodeCriteria.add(Restrictions.eq("chainStore.chain_id", chainStore.getChain_id()));

		List<ProductBarcode> productBarcodeList =  this.getByCritera(productBarcodeCriteria, true);
		return productBarcodeList;
	}


	public void initialize(ProductBarcode productBarcode){
		productDaoImpl.initialize(productBarcode.getProduct());
		colorDaoImpl.initialize(productBarcode.getColor());
		sizeDaoImpl.initialize(productBarcode.getSize());
	}

	public List<ProductBarcode> getBarcodeFromProduct(int productId) {
		DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
		
		productBarcodeCriteria.add(Restrictions.eq("product.productId", productId));
		
		return this.getByCritera(productBarcodeCriteria, true);
	}
	
	/**
	 * 获取当年的所有barcode
	 * @param years
	 * @return
	 */
	public List<ProductBarcode> getBarcodeByYear(List<Year> years){
		List<Integer> values = new ArrayList<Integer>();

		for (int i = 0; i < years.size() ; i++){
			Year year = years.get(i);
			values.add(year.getYear_ID());
		}

		DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
		DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");
		DetachedCriteria yearCriteria = productCriteria.createCriteria("year");
		yearCriteria.add(Restrictions.in("year_ID", values));
			
		return this.getByCritera(productBarcodeCriteria, true);
	}
	
	/**
	 * to get the expected whole sale price
	 * @param productBarcode
	 * @return
	 */
	public double getSelectedSalePrice(ProductBarcode productBarcode) {
		Product product = productBarcode.getProduct();
		double wholePrice3 = product.getWholeSalePrice3();
		double wholePrice2 = product.getWholeSalePrice2();
		double wholePrice1 = product.getWholeSalePrice();
		double factoryPrice = product.getSalesPriceFactory();

		if (wholePrice3 != 0)
			return wholePrice3;
		else if (wholePrice2 != 0)
			return wholePrice2;
		else if (wholePrice1 != 0)
			return wholePrice1;
		else 
			return factoryPrice;
	}

	/**
	 * to get the expected whole sale price
	 * @param productBarcode
	 * @return
	 */
	public static double getWholeSalePrice(ProductBarcode productBarcode) {
		Product product = productBarcode.getProduct();
		double wholePrice3 = product.getWholeSalePrice3();
		double wholePrice2 = product.getWholeSalePrice2();
		double wholePrice1 = product.getWholeSalePrice();
		double factoryPrice = product.getSalesPriceFactory();
		double discount = product.getDiscount();
		
		if (wholePrice3 != 0)
			return wholePrice3;
		else if (wholePrice2 != 0)
			return wholePrice2;
		else if (wholePrice1 != 0)
			return wholePrice1;
		else 
			return factoryPrice * discount;
	}

	public int getNumOfProductsUnderYQB(int yearId, int quarterId, int brandId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class,"pb");
		
		DetachedCriteria productCriteria = criteria.createCriteria("product");

		productCriteria.add(Restrictions.eq("year.year_ID", yearId));
		productCriteria.add(Restrictions.eq("quarter.quarter_ID", quarterId));
		productCriteria.add(Restrictions.eq("brand.brand_ID", brandId));
		
		criteria.setProjection(Projections.countDistinct("pb.id"));
		
		int totalProducts = Common_util.getProjectionSingleValue(this.getByCriteriaProjection(criteria, true));

		return totalProducts;
	}

	public List<Object> getProductsUnderYQB(int yearId, int quarterId, int brandId, int start, int numberOfRecord) {
		String hql = "SELECT DISTINCT(pb.id) FROM ProductBarcode pb WHERE pb.product.year.year_ID =? AND pb.product.quarter.quarter_ID=? AND pb.product.brand.brand_ID=?";
		
		Object[] values = new Object[]{yearId, quarterId, brandId};
		Integer[] pager = new Integer[]{start, numberOfRecord};
		List<Object> productIds = this.executeHQLSelect(hql, values, pager, true);

		return productIds;
	}
	

}
