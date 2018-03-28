package com.onlineMIS.action.headQ.ipad;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;

public class InventoryOrderProductVO {
	private int id;
	private String barcode;
	private String brand;
	private String productCode;
	private String color;
	private String size;
	private String wholeSalePrice;
	private int quantity;
	
	public InventoryOrderProductVO(InventoryOrderProduct orderProduct){
		ProductBarcode pb = orderProduct.getProductBarcode();
		
		this.setId(pb.getId());
		this.setBarcode(pb.getBarcode());
		
		Product product = pb.getProduct();
		this.setProductCode(product.getProductCode());

		this.setWholeSalePrice(String.valueOf((int)orderProduct.getWholeSalePrice()));
		this.setBrand(product.getBrand().getBrand_Name());
		
		Color color = pb.getColor();
		if (color == null)
			this.setColor("");
		else 
			this.setColor(color.getName());
		
		this.setQuantity(orderProduct.getQuantity());
	}
	
	public static List<InventoryOrderProductVO> parse(List<InventoryOrderProduct> orderProducts){
		List<InventoryOrderProductVO> productVOs = new ArrayList<InventoryOrderProductVO>();
		
		for (InventoryOrderProduct product : orderProducts){
			InventoryOrderProductVO productVO = new InventoryOrderProductVO(product);
			productVOs.add(productVO);
		}
		
		return productVOs;
	}
	
	public InventoryOrderProductVO(){
		
	}
	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
