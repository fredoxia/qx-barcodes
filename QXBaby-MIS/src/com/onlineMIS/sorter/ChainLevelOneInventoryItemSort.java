package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelOneInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.loggerLocal;
/**
 * the chain inventory product sort by year, quarter, brand, product code
 * @author fredo
 *
 */
public 	class ChainLevelOneInventoryItemSort  implements java.util.Comparator<ChainLevelOneInventoryItem>{
		 public int compare(ChainLevelOneInventoryItem p1,ChainLevelOneInventoryItem p2){
	         Year year1 = p1.getYear();
	         Year year2 = p2.getYear();

	         
	         int year1S = 0;
	         int year2S = 0;

	         
	         try{
	        	 year1S = year1.getYear_ID();
	        	 year2S = year2.getYear_ID();

	         } catch (NullPointerException e) {
	        	 loggerLocal.error(e);
			 }
	         
	         return year1S - year2S;

		 }
	}
