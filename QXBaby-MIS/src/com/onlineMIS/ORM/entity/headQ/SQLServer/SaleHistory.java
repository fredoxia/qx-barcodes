package com.onlineMIS.ORM.entity.headQ.SQLServer;

import java.io.Serializable;

public class SaleHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4811239645688492625L;
	private ClientsMS client;
	private ProductsMS product;
	private int unit_id;
	private double salePrice;
	private double quantity;
	private double discount;
	

	public ClientsMS getClient() {
		return client;
	}
	public void setClient(ClientsMS client) {
		this.client = client;
	}
	public ProductsMS getProduct() {
		return product;
	}
	public void setProduct(ProductsMS product) {
		this.product = product;
	}
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}

	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	

}
