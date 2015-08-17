package com.onlineMIS.ORM.entity.headQ.SQLServer;

import java.io.Serializable;

public class PriceMS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1506262127602909012L;
	/**
	 * 厂家零售价
	 */
    private double retailprice;
    /**
     * 最近进价
     */
    private double recprice;
    /**
     * 预设价1
     */
    private double price1;
    /**
     * 预设价2
     */
    private double price2;
    /**
     * 预设价3
     */
    private double price3;
    /**
     * 预设价4
     */
    private double price4;
    /**
     * 连锁店零售价
     */
    private double glprice;
    private int productID;
    private ProductsMS product;
    
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public double getRetailprice() {
		return retailprice;
	}
	public void setRetailprice(double retailprice) {
		this.retailprice = retailprice;
	}
	public double getRecprice() {
		return recprice;
	}
	public void setRecprice(double recprice) {
		this.recprice = recprice;
	}
	public double getPrice1() {
		return price1;
	}
	public void setPrice1(double price1) {
		this.price1 = price1;
	}
	
	public double getPrice2() {
		return price2;
	}
	public void setPrice2(double price2) {
		this.price2 = price2;
	}
	public double getPrice3() {
		return price3;
	}
	public void setPrice3(double price3) {
		this.price3 = price3;
	}
	public double getPrice4() {
		return price4;
	}
	public void setPrice4(double price4) {
		this.price4 = price4;
	}
	public double getGlprice() {
		return glprice;
	}
	public void setGlprice(double glprice) {
		this.glprice = glprice;
	}
	public ProductsMS getProduct() {
		return product;
	}
	public void setProduct(ProductsMS product) {
		this.product = product;
	}
    
    
}
