package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.common.loggerLocal;

public 	class ProductSortByIndex implements java.util.Comparator<BaseProduct>{
		 public int compare(BaseProduct obj1,BaseProduct obj2){
         int index1 = 0;
         int index2 = 0;
         
         try{
        	 index1 = obj1.getIndex();
             index2 = obj2.getIndex();
         } catch (NullPointerException e) {
        	 loggerLocal.error(e);
		  }
		  if(index1 < index2)
		   return -1;
		  else if(index1 > index2)
		   return 1;
		  else
		   return 0;
		 }
	}
