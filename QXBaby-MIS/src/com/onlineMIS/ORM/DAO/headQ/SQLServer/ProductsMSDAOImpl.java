package com.onlineMIS.ORM.DAO.headQ.SQLServer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAOMS;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ProductsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;

@Repository
public class ProductsMSDAOImpl extends BaseDAOMS<ProductsMS> {
	public static final String GET_INVENTORY_PROCEDURE = "{Call Ts_W_GetCanSaleQty(?,?,?,?,?)}";
	public static final int FLOOR_6_STORE_ID = 2;
	public static final int MENGYANG_STORE_ID = 11;
	public static final int XILE_STORE_ID = 8;
	
	/**
	 * to load all of the products from MS
	 * @return
	 */
	public HashMap<String, ProductsMS> retrieveProducts(){
		HashMap<String, ProductsMS> productMap = new HashMap<String, ProductsMS>();
		
		List<ProductsMS> productList =  this.getAll(true);
		for (ProductsMS productsMS: productList){
			String key = productsMS.getSerial_number();

			productMap.put(key, productsMS);
		}
		
		return productMap;
	}

	/**
	 * TO load the required products from MS
	 * @param barcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, ProductsMS> retrieveProducts(Set<String> barcodes){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(ProductsMS.class,"p");
		productCriteria.add(Restrictions.in("p.serial_number", barcodes));
		productCriteria.add(Restrictions.eq("p.deleted", ProductsMS.AVAILABLE_STATUS));
	    
		
		HashMap<String, ProductsMS> productMap = new HashMap<String, ProductsMS>();
		
		List<ProductsMS> productList =  this.getByCritera(productCriteria, false);
		for (ProductsMS productsMS: productList){
			String key = productsMS.getSerial_number();

			productMap.put(key, productsMS);
		}
		
		return productMap;
	}

	public ProductsMS getBySerialNum(String serialNum) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(ProductsMS.class,"p");
		productCriteria.add(Restrictions.eq("p.serial_number", serialNum));
		productCriteria.add(Restrictions.eq("p.deleted", ProductsMS.AVAILABLE_STATUS));
	    
		List<ProductsMS> productList =  this.getByCritera(productCriteria, true);
		if (productList != null && productList.size() ==1)
		   return productList.get(0);
		else 
		   return null;
	}

}
