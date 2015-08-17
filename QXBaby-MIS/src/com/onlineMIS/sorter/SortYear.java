package com.onlineMIS.sorter;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class SortYear implements java.util.Comparator<Year>{
	@Override
	public int compare(Year arg0, Year arg1) {
	       int year1 = 0;
	       int year2 = 0;
	       
	       year1 = arg0.getYear_ID();
	       year2 = arg1.getYear_ID();

           return year2 - year1;
	 }
}	
