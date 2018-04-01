package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.loggerLocal;

public class InventoryOrderProductSort implements java.util.Comparator<InventoryOrderProduct>{

	@Override
	public int compare(InventoryOrderProduct obj1, InventoryOrderProduct obj2) {
        String year1 = "";
        String year2 = "";
        String quarter1 = "";
        String quarter2 = "";
        String brand1 = "";
        String brand2 = "";
        String productCode1 = "";
        String productCode2 = "";
        String color1 = "";
        String color2 = "";
        
        try{

            Product product1 = obj1.getProductBarcode().getProduct();
            Product product2 = obj2.getProductBarcode().getProduct();
            
            if (product1 != null ){
                year1 = product1.getYear().getYear();
                quarter1 = product1.getQuarter().getQuarter_Name();
                brand1 = product1.getBrand().getBrand_Name();
                productCode1 = product1.getProductCode();
                Color colorObj1 = obj1.getProductBarcode().getColor();
                if (colorObj1 != null)
                	color1 = colorObj1.getName();
            }
            if (product2 != null ){
                year2 = product2.getYear().getYear();
                quarter2 = product2.getQuarter().getQuarter_Name();
                brand2 = product2.getBrand().getBrand_Name();
                productCode2 = product2.getProductCode();
                Color colorObj2 = obj2.getProductBarcode().getColor();
                if (colorObj2 != null)
                	color2 = colorObj2.getName();
            }
        } catch (NullPointerException e) {
       	 loggerLocal.error(e);
		 }

       	  if (year1.compareTo(year2) != 0)
       		  return year1.compareTo(year2)*-1;
       	  else if (quarter1.compareTo(quarter2) != 0)
       		  return quarter1.compareTo(quarter2);
       	  else if (brand1.compareTo(brand2) != 0)
       		  return brand1.compareTo(brand2);
       	  else if (productCode1.compareTo(productCode2) != 0)
       		  return productCode1.compareTo(productCode2);
       	  else if (color1.compareTo(color2) != 0)
       		  return color1.compareTo(color2);
       	  else
			 return 0;
	}

}
