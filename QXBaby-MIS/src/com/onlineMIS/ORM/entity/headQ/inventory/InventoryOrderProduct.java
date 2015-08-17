package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.Serializable;
import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class InventoryOrderProduct extends BaseProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6371647275286053468L;
	private int ID;
    private ProductBarcode productBarcode = new ProductBarcode();
    private int quantity;
    private  InventoryOrder order = new InventoryOrder();
    private boolean moreThanTwoHan;
    /**
     * the chain store's sale price连锁店零售价
     */
    private double salesPrice;
    /**
     * ** to store the two data in the object is to prevent the price change on the oringal price
     * the whole saler's sale price/chain store's cost,批发商发价/连锁店进价
     */
    private double wholeSalePrice;
    /**
     * the whole saler's cost price批发商进价
     */
    private double recCost;
    /**
     * the discount, 折扣
     * 
     */
    private double discount;
    /**
     * the selected sale price 
     */
    private double salePriceSelected;
    
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public double getWholeSalePrice() {
		return wholeSalePrice;
	}
	public void setWholeSalePrice(double wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}
	public double getRecCost() {
		return recCost;
	}
	public void setRecCost(double recCost) {
		this.recCost = recCost;
	}

	public boolean getMoreThanTwoHan() {
		return moreThanTwoHan;
	}
	public void setMoreThanTwoHan(boolean moreThanTwoHan) {
		this.moreThanTwoHan = moreThanTwoHan;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public InventoryOrder getOrder() {
		return order;
	}
	public void setOrder(InventoryOrder order) {
		this.order = order;
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getSalePriceSelected() {
		return salePriceSelected;
	}
	public void setSalePriceSelected(double salePriceSelected) {
		this.salePriceSelected = salePriceSelected;
	}
	
	
}
