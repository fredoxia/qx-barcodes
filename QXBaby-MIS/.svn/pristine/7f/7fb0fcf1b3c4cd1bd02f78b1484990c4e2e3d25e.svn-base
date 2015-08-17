package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.loggerLocal;
/**
 * the chain inventory product sort by year, quarter, brand, product code
 * @author fredo
 *
 */
public 	class ChainInveProductSort  implements java.util.Comparator<ChainInventoryFlowOrderProduct>{
		 public int compare(ChainInventoryFlowOrderProduct p1,ChainInventoryFlowOrderProduct p2){
	         Product product1 = p1.getProductBarcode().getProduct();
	         Product product2 = p2.getProductBarcode().getProduct();
	         
	         int year1 = 0;
	         int year2 = 0;
	         
	         int quarter1 = product1.getQuarter().getQuarter_ID();
	         int quarter2 = product2.getQuarter().getQuarter_ID();
	         
	         int brand1 = product1.getBrand().getBrand_ID();
	         int brand2 = product2.getBrand().getBrand_ID();
	         
	         String productCode1 = product1.getProductCode();
	         String productCode2 = product2.getProductCode();
	         
	         try{
		         year1 = product1.getYear().getYear_ID();
		         year2 = product2.getYear().getYear_ID();
		         
		         quarter1 = product1.getQuarter().getQuarter_ID();
		         quarter2 = product2.getQuarter().getQuarter_ID();
		         
		         brand1 = product1.getBrand().getBrand_ID();
		         brand2 = product2.getBrand().getBrand_ID();
		         
		         productCode1 = product1.getProductCode();
		         productCode2 = product2.getProductCode();
	         } catch (NullPointerException e) {
	        	 loggerLocal.error(e);
			 }
	         
	         if (year1 < year2)
	        	 return 1;
	         else if (year1 > year2)
	        	 return -1;
	         else {
		         if (quarter1 < quarter2)
		        	 return 1;
		         else if (quarter1 > quarter2)
		        	 return -1;
		         else {
			         if (brand1 < brand2)
			        	 return -1;
			         else if (brand1 > brand2)
			        	 return 1;
			         else {
				         return productCode1.compareTo(productCode2);
			         }
		         }
	         }
		 }
	}
