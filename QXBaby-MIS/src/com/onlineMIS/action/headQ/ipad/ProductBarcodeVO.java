package com.onlineMIS.action.headQ.ipad;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
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
	private int inventory;
	private int orderHis;
	private int orderCurrent;
	private String category;
	
	public ProductBarcodeVO(ProductBarcode pb, int inventory, int orderHis, int orderCurrent){
		this.setId(pb.getId());
		this.setBarcode(pb.getBarcode());
		
		Product product = pb.getProduct();
		this.setProductCode(product.getProductCode());
		this.setNumPerHand(product.getNumPerHand());
		this.setWholeSalePrice(String.valueOf((int)ProductBarcodeDaoImpl.getWholeSalePrice(pb)));
		this.setBrand(product.getBrand().getBrand_Name());
		
		Color color = pb.getColor();
		if (color == null)
			this.setColor("");
		else 
			this.setColor(color.getName());
		
		this.setInventory(inventory);
		this.setOrderCurrent(orderCurrent);
		this.setOrderHis(orderHis);
		this.setCategory(product.getCategory().getCategory_Name());
	}
	
	public ProductBarcodeVO(){
		
	}
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getOrderHis() {
		return orderHis;
	}

	public void setOrderHis(int orderHis) {
		this.orderHis = orderHis;
	}

	public int getOrderCurrent() {
		return orderCurrent;
	}

	public void setOrderCurrent(int orderCurrent) {
		this.orderCurrent = orderCurrent;
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
	
	public String toString(){
		return brand + " " + productCode + " " +color + "<br/>" +
			   "类型  : " + category +  "<br/>" +
	           "批发价  : " + wholeSalePrice +  "<br/>" +
			   "库存          : " + inventory + "<br/>" +
			   "历史订货 : " + orderHis + "<br/>" + 
			   "当前已订 : " + orderCurrent;
	}
}
