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
public 	class ChainLevelFourInventoryItemSort  implements java.util.Comparator<ChainLevelFourInventoryItem>{
		 public int compare(ChainLevelFourInventoryItem p1,ChainLevelFourInventoryItem p2){
	         String pb1 = p1.getProductBarcode().getProduct().getProductCode();
	         String pb2 = p2.getProductBarcode().getProduct().getProductCode();

	         return pb1.compareTo(pb2);
		 }
}
