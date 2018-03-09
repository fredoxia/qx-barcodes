package com.onlineMIS.ORM.entity.headQ.barcodeGentor;

import java.io.Serializable;

public class BrandPriceIncrease implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 226429547275187337L;
	private Year year = new Year();
    private Quarter quarter = new Quarter();
    private Brand brand = new Brand();
    private double increase = 0;
    
    public BrandPriceIncrease(){
    	
    }
    
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public double getIncrease() {
		return increase;
	}
	public void setIncrease(double increase) {
		this.increase = increase;
	}
    
    
}
