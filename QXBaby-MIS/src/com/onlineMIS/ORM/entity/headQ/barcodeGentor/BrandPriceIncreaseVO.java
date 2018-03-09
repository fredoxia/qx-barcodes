package com.onlineMIS.ORM.entity.headQ.barcodeGentor;

import java.io.Serializable;

import com.onlineMIS.common.Common_util;

public class BrandPriceIncreaseVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 226429547275187337L;
	private int yearId;
    private int quarterId;
    private int brandId;
    private String year;
    private String quarter;
    private String brand;
    private String increaseString;
    
    public BrandPriceIncreaseVO(){
    	
    }
    
    public BrandPriceIncreaseVO(BrandPriceIncrease b){
    	this.setBrand(b.getBrand().getBrand_Name());
    	this.setBrandId(b.getBrand().getBrand_ID());
    	this.setYear(b.getYear().getYear());
    	this.setYearId(b.getYear().getYear_ID());
    	this.setQuarter(b.getQuarter().getQuarter_Name());
    	this.setQuarterId(b.getQuarter().getQuarter_ID());
    	
    	double increase = b.getIncrease();
    	String increaseString = (int)increase + "%";
    	
    	this.setIncreaseString("调价为原来的 " + increaseString);
    }

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getQuarterId() {
		return quarterId;
	}

	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getIncreaseString() {
		return increaseString;
	}

	public void setIncreaseString(String increaseString) {
		this.increaseString = increaseString;
	}
    
    
}
