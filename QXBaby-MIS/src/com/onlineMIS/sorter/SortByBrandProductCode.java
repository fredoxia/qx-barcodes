package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

public 	class SortByBrandProductCode implements java.util.Comparator<InventoryOrderProduct>{
	 public int compare(InventoryOrderProduct obj1,InventoryOrderProduct obj2){
         String brand1 = "";
         String brand2 = "";
         String productCode1 = "";
         String productCode2 = "";
         
         try{
             brand1 = obj1.getProductBarcode().getProduct().getBrand().getBrand_Name();
             brand2 = obj2.getProductBarcode().getProduct().getBrand().getBrand_Name();
             productCode1 = obj1.getProductBarcode().getProduct().getProductCode();
             productCode2 = obj2.getProductBarcode().getProduct().getProductCode();
         } catch (NullPointerException e) {
       	  loggerLocal.error(e);
		  }
		  if(brand1.compareTo(brand2) < 0)
		   return -1;
		  else if(brand1.compareTo(brand2) > 0)
		   return 1;
		  else
		   return Common_util.compareString(productCode1,productCode2);
		 }
	}
