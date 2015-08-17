package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelThreeInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.loggerLocal;
/**
 * the chain inventory product sort by year, quarter, brand, product code
 * @author fredo
 *
 */
public 	class ChainLevelThreeInventoryItemSort  implements java.util.Comparator<ChainLevelThreeInventoryItem>{
		 public int compare(ChainLevelThreeInventoryItem p1,ChainLevelThreeInventoryItem p2){
	         Year year1 = p1.getYear();
	         Year year2 = p2.getYear();
  
	         Quarter quarter1 = p1.getQuarter();
	         Quarter quarter2 = p2.getQuarter();
	         
	         Brand brand1 = p1.getBrand();
	         Brand brand2 = p2.getBrand();
	         
	         int year1S = 0;
	         int year2S = 0;
	         
	         int quarter1S = 0;
	         int quarter2S = 0;
	         
	         int brand1S =0;
	         int brand2S =0;
	         
	         try{
	        	 year1S = year1.getYear_ID();
	        	 year2S = year2.getYear_ID();
		         
		         quarter1S = quarter1.getQuarter_ID();
		         quarter2S = quarter2.getQuarter_ID();
		         
		         brand1S = brand1.getBrand_ID();
		         brand2S = brand2.getBrand_ID();
	         } catch (NullPointerException e) {
	        	 loggerLocal.error(e);
			 }
	         
	         if (year1S != year2S)
	        	 return year1S - year2S;
	         else {
		         if (quarter1S != quarter2S)
		        	 return quarter1S - quarter2S;
		         else {
		        	 if (brand1S != brand2S)
			        	 return brand1S - brand2S;
			         else {
				         return 0;
			         }
		         }
	         }
		 }
}
