package com.onlineMIS.ORM.DAO.headQ.chain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderProductDAOImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportBean;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportBrandBean;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportResultItem;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportTemplate;
import com.onlineMIS.ORM.entity.headQ.chain.ChainSalesReportActionFormBean;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Service
public class ChainSalesService {
	@Autowired
	private InventoryOrderProductDAOImpl inventoryOrderProductDAOImpl;

    /**
     * to generate the chain sales report
     * @param reportBean
     * @return
     */
	public ChainSalesReportBean generateChainSalesReport(ChainSalesReportActionFormBean reportBean){
		int customer_id = reportBean.getOrder().getClient_id();
		int year = reportBean.getYear();
		int quarter = reportBean.getQuarter();
		List<Integer> brands = reportBean.getBrands();
		Date startDate = Common_util.formStartDate(reportBean.getStartDate());
		Date endDate = Common_util.formEndDate(reportBean.getEndDate());
		
		DetachedCriteria orderProductCriteria = DetachedCriteria.forClass(InventoryOrderProduct.class,"p");
		
		DetachedCriteria orderCriteria = orderProductCriteria.createCriteria("order");
		orderCriteria.add(Restrictions.between("order_EndTime",startDate,endDate));
		orderCriteria.add(Restrictions.eq("client_id", customer_id));
		
		DetachedCriteria productCriteria = orderProductCriteria.createCriteria("product");
		
		productCriteria.add(Restrictions.eq("year.year_ID", year));
		productCriteria.add(Restrictions.eq("quarter.quarter_ID", quarter));
		productCriteria.add(Restrictions.in("brand.brand_ID", brands));
		
		List<InventoryOrderProduct> orderProducts = inventoryOrderProductDAOImpl.getByCritera(orderProductCriteria,false);
		
		/**
		 * to build the map result
		 * brand Name : <product code: sales revenue>
		 */
		Map<String, Map<String, ChainSalesReportResultItem>> mapResult = new HashMap<String, Map<String,ChainSalesReportResultItem>>();
		String brand = "";
		String productCode = "";
		int quantity = 0;
		double salesPrice = 0;
		double wholePrice = 0;
		double wholeSaleRevenue = 0;
		double salesRevenue = 0;
		for (InventoryOrderProduct orderProduct: orderProducts){
			Product product = orderProduct.getProductBarcode().getProduct();
		    brand = product.getBrand().getBrand_Name();
		    productCode = product.getProductCode();

		    salesPrice = orderProduct.getSalesPrice();
		    wholePrice = orderProduct.getWholeSalePrice();
		    
		    int orderType = orderProduct.getOrder().getOrder_type();
		    
		    if (orderType ==  InventoryOrder.TYPE_SALES_ORDER_W)
			    quantity = orderProduct.getQuantity();
		    else if (orderType ==  InventoryOrder.TYPE_SALES_RETURN_ORDER_W)
			    quantity = orderProduct.getQuantity() * (-1);
		      
		    salesRevenue = salesPrice * quantity;
		    wholeSaleRevenue = wholePrice * quantity;

		    Map<String, ChainSalesReportResultItem> mapResult2 = mapResult.get(brand);
		    if (mapResult2 != null){
		    	int totalQ = 0;
		    	ChainSalesReportResultItem result = mapResult2.get(productCode);
		    	 if (result != null){
		    		 totalQ = result.getQuantity() + quantity;
		    		 result.setQuantity(totalQ);
		    		 result.setSalesRevenue(salesRevenue + result.getSalesRevenue());
		    		 result.setWholeRevenue(wholeSaleRevenue + result.getWholeRevenue());
		    	 } else {
		    		result = new ChainSalesReportResultItem(orderProduct); 
		    		result.setSalesRevenue(salesRevenue);
		    		result.setWholeRevenue(wholeSaleRevenue);
		    	 }
		    	 
		    	 mapResult2.put(productCode, result);
		    } else {
		    	mapResult2 = new HashMap<String, ChainSalesReportResultItem>();
		    	ChainSalesReportResultItem result = new ChainSalesReportResultItem(orderProduct); 
		    	result.setSalesRevenue(salesRevenue);
		    	result.setWholeRevenue(wholeSaleRevenue);
		    	mapResult2.put(productCode, result);
		    	mapResult.put(brand, mapResult2);
		    }
		}
		
		/**
		 * format the report result
		 */
		int totalQuantityReport = 0;
		double totalRevenue = 0;
		double totalWholeRevenue = 0;
		
		List<ChainSalesReportBrandBean> brand_result_list = new ArrayList<ChainSalesReportBrandBean>();
		Iterator<String> key_brand_it = mapResult.keySet().iterator();
		while (key_brand_it.hasNext()){
			ChainSalesReportBrandBean result = new ChainSalesReportBrandBean();
			String key_brand = key_brand_it.next();
			int totalQ_brand = 0;
			double sales_revenue_brand = 0;
			double whole_revenue_brand = 0;
            result.setBrand(key_brand);
			
			Map<String, ChainSalesReportResultItem> result2_map = mapResult.get(key_brand);
			List<ChainSalesReportResultItem> result2_list = new ArrayList<ChainSalesReportResultItem>();
			Iterator<String> key_productCode_it = result2_map.keySet().iterator();
			while (key_productCode_it.hasNext()){
				ChainSalesReportResultItem result2 = result2_map.get(key_productCode_it.next());
				result2_list.add(result2);
				
				totalQ_brand += result2.getQuantity();
				sales_revenue_brand += result2.getSalesRevenue();
				whole_revenue_brand += result2.getWholeRevenue();
			}
			
			Collections.sort(result2_list, new SortProductCode());
			result.setTotalQ(totalQ_brand);
			result.setTotalRevenue(sales_revenue_brand);
			result.setTotalWholeSaleRevenue(whole_revenue_brand);
			result.setItems(result2_list);
			
			totalQuantityReport += totalQ_brand;
			totalRevenue += sales_revenue_brand;
			totalWholeRevenue += whole_revenue_brand;
			
			brand_result_list.add(result);
		}
		
		ChainSalesReportBean resultBean = new ChainSalesReportBean();
		resultBean.setCustomerName(reportBean.getOrder().getClient_name());
		resultBean.setStartDate(startDate);
		resultBean.setEndDate(endDate);
		resultBean.setTotalQ(totalQuantityReport);
		resultBean.setTotalRevenue(totalRevenue);
		resultBean.setTotalWholeRevenue(totalWholeRevenue);
		resultBean.setBrandItems(brand_result_list);
		return resultBean;
	}
	
	public Map<String,Object> exportSalesReportExcel(ChainSalesReportBean reportBean, String path){
		
		
		Map<String,Object> returnMap=new HashMap<String, Object>();   

        
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			ChainSalesReportTemplate saleTemplate = new ChainSalesReportTemplate(reportBean, path);

			wb = saleTemplate.process();

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
	
	class SortProductCode implements java.util.Comparator<ChainSalesReportResultItem>{
		 public int compare(ChainSalesReportResultItem obj1,ChainSalesReportResultItem obj2){
        String productCode1 = "";
        String productCode2 = "";
        
        try{
        	productCode1 = obj1.getProductBarcode().getProduct().getProductCode();
        	productCode2 = obj2.getProductBarcode().getProduct().getProductCode();
        } catch (NullPointerException e) {
        	loggerLocal.error(e);
		  }

         return Common_util.compareString(productCode1,productCode2);
		 }
	}


}
