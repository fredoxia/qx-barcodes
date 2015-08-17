package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelFourInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelThreeInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.loggerLocal;
/**
 * the chain inventory product sort by year, quarter, brand, product code
 * @author fredo
 *
 */
public 	class ChainInventoryReportSort  implements java.util.Comparator<ChainLevelFourInventoryItem>{
		 public int compare(ChainLevelFourInventoryItem p1,ChainLevelFourInventoryItem p2){
			 ProductBarcode pb1 = p1.getProductBarcode();
			 ProductBarcode pb2 = p2.getProductBarcode();
			 
			 int y1 = pb1.getProduct().getYear().getYear_ID();
			 int y2 = pb2.getProduct().getYear().getYear_ID();
			 
	         if (y1 == y2){
	        	 int q1 = pb1.getProduct().getQuarter().getQuarter_ID();
	        	 int q2 = pb2.getProduct().getQuarter().getQuarter_ID();
	        	 if (q1 == q2){
	        		 int b1 = pb1.getProduct().getBrand().getBrand_ID();
	        		 int b2 = pb2.getProduct().getBrand().getBrand_ID();
	        		 if (b1 == b2){
	        			 String productCode1 = pb1.getProduct().getProductCode();
	        			 String productCode2 = pb2.getProduct().getProductCode();
	        			 return productCode1.compareTo(productCode2);
	        			 
	        		 } else {
	        			 return b1 - b2;
	        		 }
	        	 }else 
	        		 return q1 - q2;	 
	         } else 
	        	 return y1 - y2;
		 }
}
