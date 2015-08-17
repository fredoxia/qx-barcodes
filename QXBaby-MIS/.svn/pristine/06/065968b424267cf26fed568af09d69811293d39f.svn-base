package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.loggerLocal;

public 	class ChainInventoryOrderProductSorter implements java.util.Comparator<ChainInventoryFlowOrderProduct>{
		 public int compare(ChainInventoryFlowOrderProduct obj1,ChainInventoryFlowOrderProduct obj2){
	     int qDiff1 = 0;
	     int qDiff2 = 0;
         int index1 = 0;
         int index2 = 0;
         
         String year1 = "";
         String year2 = "";
         String quarter1 = "";
         String quarter2 = "";
         String brand1 = "";
         String brand2 = "";
         String productCode1 = "";
         String productCode2 = "";
         
         try{
        	 qDiff1 = Math.abs(obj1.getQuantityDiff());
        	 qDiff2 = Math.abs(obj2.getQuantityDiff());
        	 
        	 index1 = obj1.getIndex();
             index2 = obj2.getIndex();
             
             Product product1 = obj1.getProductBarcode().getProduct();
             Product product2 = obj2.getProductBarcode().getProduct();
             
             if (product1 != null ){
                 year1 = product1.getYear().getYear();
                 quarter1 = product1.getQuarter().getQuarter_Name();
                 brand1 = product1.getBrand().getBrand_Name();
                 productCode1 = product1.getProductCode();
             }
             if (product2 != null ){
                 year2 = product2.getYear().getYear();
                 quarter2 = product2.getQuarter().getQuarter_Name();
                 brand2 = product2.getBrand().getBrand_Name();
                 productCode2 = product2.getProductCode();
             }
         } catch (NullPointerException e) {
        	 loggerLocal.error(e);
		 }
         
         if (qDiff1 > qDiff2){
        	  return -1;
          } else if (qDiff1 < qDiff2){
        	  return 1 ;
          } else {
        	  if (year1.compareTo(year2) != 0)
        		  return year1.compareTo(year2)*-1;
        	  else if (quarter1.compareTo(quarter2) != 0)
        		  return quarter1.compareTo(quarter2);
        	  else if (brand1.compareTo(brand2) != 0)
        		  return brand1.compareTo(brand2);
        	  else if (productCode1.compareTo(productCode2) != 0)
        		  return productCode1.compareTo(productCode2);
        	  else {
				  if(index1 < index2)
				   return -1;
				  else if(index1 > index2)
				   return 1;
				  else
				   return 0;
        	  }
		  }
		 }
	}
