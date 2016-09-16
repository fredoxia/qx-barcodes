package com.onlineMIS.sorter;

import java.util.List;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

public 	class ChainDailySalesSorter implements java.util.Comparator<Object>{
		 public int compare(Object obj1,Object obj2){
	         Object[] arrayObj1 =  (Object[])obj1;
	         
	         //sum(netAmount - netAmountR) - sum(discountAmount)
	         double netSale1 = Common_util.getDouble(arrayObj1[13]) - Common_util.getDouble(arrayObj1[7]);
	         
	         Object[] arrayObj2 =  (Object[])obj2;
	         double netSale2 = Common_util.getDouble(arrayObj2[13]) - Common_util.getDouble(arrayObj2[7]);		
	         
	         return Double.compare(netSale1, netSale2) * (-1);
		 }
	}
