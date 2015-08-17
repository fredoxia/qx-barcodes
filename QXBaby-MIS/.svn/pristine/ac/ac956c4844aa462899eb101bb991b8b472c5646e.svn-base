package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;


import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ProductsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;


@Repository
public class ProductDaoImpl extends BaseDAO<Product> {

	public Product getBySerialNum(String serialNum, ChainStore chainStore) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(Product.class,"p");
		productCriteria.add(Restrictions.eq("p.serialNum", serialNum));
		
		if (chainStore != null)
			productCriteria.add(Restrictions.eq("p.chainStore.chain_id", chainStore.getChain_id()));

		List<Product> productList =  this.getByCritera(productCriteria, true);
		if (productList != null && productList.size() ==1){
		   Product product = productList.get(0);
		   return product;
		}else 
		   return null;
	}

	public List<Product> getProductsByProductCode(String productCode, ChainStore chainStore) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class,"product");
		criteria.add(Restrictions.eq("product.productCode", productCode));

		if (chainStore != null)
			criteria.add(Restrictions.eq("product.chainStore.chain_id", chainStore.getChain_id()));

		
		List<Product> products =  this.getByCritera(criteria,true);
		
		return products;
	}
	
	public void initialize(Product product){
		initialize(product.getArea());
		initialize(product.getBrand());
		initialize(product.getCategory());
		initialize(product.getQuarter());
		initialize(product.getYear());
	}
	
	public void initialize(Collection<Product> products){
		for (Product product: products){
			initialize(product);
		}
	}

	public int getNumOfBrandsUnderYQ(int yearId, int quarterId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class,"p");

		criteria.add(Restrictions.eq("p.year.year_ID", yearId));
		criteria.add(Restrictions.eq("p.quarter.quarter_ID", quarterId));
		criteria.add(Restrictions.ne("p.brand.brand_ID", Brand.BRAND_NOT_COUNT_INVENTORY[0]));
		criteria.add(Restrictions.ne("p.brand.brand_ID", Brand.BRAND_NOT_COUNT_INVENTORY[1]));
		
		criteria.setProjection(Projections.countDistinct("p.brand.brand_ID"));
		
		int totalBrands = Common_util.getProjectionSingleValue(this.getByCriteriaProjection(criteria, true));

		return totalBrands;
	}
	
	public List<Object> getBrandIdsUnderYQ(int yearId, int quarterId, int start, int numberOfRecord) {
		String hql = "SELECT DISTINCT(p.brand.brand_ID) FROM Product p WHERE p.year.year_ID =? AND p.quarter.quarter_ID=? AND p.brand.brand_ID != ? AND p.brand.brand_ID != ?";
		
		Object[] values = new Object[]{yearId, quarterId,Brand.BRAND_NOT_COUNT_INVENTORY[0],Brand.BRAND_NOT_COUNT_INVENTORY[1]};
		Integer[] pager = new Integer[]{start, numberOfRecord};
		List<Object> brands = this.executeHQLSelect(hql, values, pager, true);

		return brands;
	}
	
	public int getNumOfBrandsUnderYQName(int yearId, int quarterId, String brandName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class,"p");

		criteria.add(Restrictions.eq("p.year.year_ID", yearId));
		criteria.add(Restrictions.eq("p.quarter.quarter_ID", quarterId));
		if (brandName != null && !brandName.trim().equals("")){
			DetachedCriteria brandCriteria = criteria.createCriteria("brand");
			brandCriteria.add(Restrictions.like("brand_Name", brandName, MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.countDistinct("p.brand.brand_ID"));
		
		int totalBrands = Common_util.getProjectionSingleValue(this.getByCriteriaProjection(criteria, true));

		return totalBrands;
	}

//	public List<Object> getBrandsUnderYQ(int yearId, int quarterId, int start, int numberOfRecord) {
//		String hql = "SELECT DISTINCT(p.brand.brand_ID) FROM Product p WHERE p.year.year_ID =? AND p.quarter.quarter_ID=?";
//		
//		Object[] values = new Object[]{yearId, quarterId};
//		Integer[] pager = new Integer[]{start, numberOfRecord};
//		List<Object> brands = this.executeHQLSelect(hql, values, pager, true);
//
//		return brands;
//	}
	
	
	public List<Object> getBrandIdsUnderYQName(int yearId, int quarterId, String brandName, int start, int numberOfRecord) {
		String hql = "SELECT DISTINCT(p.brand.brand_ID) FROM Product p WHERE p.year.year_ID =? AND p.quarter.quarter_ID=? AND p.brand.brand_Name LIKE ?";
		
		Object[] values = new Object[]{yearId, quarterId, "%"+brandName+"%"};
		Integer[] pager = new Integer[]{start, numberOfRecord};
		List<Object> brands = this.executeHQLSelect(hql, values, pager, true);

		return brands;
	}

}
