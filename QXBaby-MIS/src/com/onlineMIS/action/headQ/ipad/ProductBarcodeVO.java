package com.onlineMIS.action.headQ.ipad;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;

public class ProductBarcodeVO {
	private int id;
	private String barcode;
	private String brand;
	private String productCode;
	private int numPerHand;
	private String color;
	private String size;
	private String wholeSalePrice;
	
	public ProductBarcodeVO(ProductBarcode pb){
		this.setId(pb.getId());
		this.setBarcode(pb.getBarcode());
		
		Product product = pb.getProduct();
		this.setProductCode(product.getProductCode());
		this.setNumPerHand(product.getNumPerHand());
		this.setWholeSalePrice(String.valueOf((int)product.getWholeSalePrice()));
		this.setBrand(product.getBrand().getBrand_Name());
		
		Color color = pb.getColor();
		if (color == null)
			this.setColor("");
		else 
			this.setColor(color.getName());
	}
	
	public ProductBarcodeVO(){
		
	}
	
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getNumPerHand() {
		return numPerHand;
	}
	public void setNumPerHand(int numPerHand) {
		this.numPerHand = numPerHand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getWholeSalePrice() {
		return wholeSalePrice;
	}
	public void setWholeSalePrice(String wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}
	
	
}
