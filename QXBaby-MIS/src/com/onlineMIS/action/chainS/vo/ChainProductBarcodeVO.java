package com.onlineMIS.action.chainS.vo;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;

public class ChainProductBarcodeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -752545011209343000L;
	private int chainId;
	private String year = "";
	private String quarter = "";
	private String brand = "";
	private String productCode = "";
	private String color = "";
	private String unit = "";
	private String category = "";
	private String barcode = "";
	private int productBarcodeId = 0;
	/**
	 * 0: 总部的
	 * > 0：  连锁店的
	 */
	private int barcodeBelong = 0;
	/**
	 * 连锁店统一售价
	 */
	private double chainSalePrice = 0;
	/**
	 * 我的零售价
	 */
	private double mySalePrice = 0;
	/**
	 * 我的零售价是统一价的%
	 */
	private String mySalesPricePercentage = "";
	private double cost = 0;
	private int inventoryLevel = 0;
	private double discount = 1;

	public ChainProductBarcodeVO(){
		
	}
	
	public ChainProductBarcodeVO(ProductBarcode barcode2, Double mySalePrice) {
		Product product = barcode2.getProduct();
		
		this.year = product.getYear().getYear();
		this.quarter = product.getQuarter().getQuarter_Name();
		this.brand = product.getBrand().getBrand_Name();
		this.productCode = product.getProductCode();
		
		if (barcode2.getColor() != null)
		    this.color = barcode2.getColor().getName();
		
		this.category = product.getCategory().getCategory_Name();
		
		this.unit = product.getUnit();
		this.barcode = barcode2.getBarcode();
		this.productBarcodeId = barcode2.getId();
		this.chainSalePrice = product.getSalesPrice();
		if (mySalePrice != null)
			this.mySalePrice = mySalePrice.doubleValue();
		this.cost = ProductBarcodeDaoImpl.getWholeSalePrice(barcode2);
		if (barcode2.getChainStore() != null){
			this.barcodeBelong = barcode2.getChainStore().getChain_id();
		}
	}

	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getBarcodeBelong() {
		return barcodeBelong;
	}

	public void setBarcodeBelong(int barcodeBelong) {
		this.barcodeBelong = barcodeBelong;
	}

	public String getMySalesPricePercentage() {
		return Common_util.roundDouble(mySalePrice/chainSalePrice * 100, 0)+"%";
	}

	public void setMySalesPricePercentage(String mySalesPricePercentage) {
		this.mySalesPricePercentage = mySalesPricePercentage;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getProductBarcodeId() {
		return productBarcodeId;
	}
	public void setProductBarcodeId(int productBarcodeId) {
		this.productBarcodeId = productBarcodeId;
	}
	public double getChainSalePrice() {
		return chainSalePrice;
	}
	public void setChainSalePrice(double chainSalePrice) {
		this.chainSalePrice = chainSalePrice;
	}
	public double getMySalePrice() {
		return mySalePrice;
	}
	public void setMySalePrice(double mySalePrice) {
		this.mySalePrice = mySalePrice;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getInventoryLevel() {
		return inventoryLevel;
	}
	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	
	
}
