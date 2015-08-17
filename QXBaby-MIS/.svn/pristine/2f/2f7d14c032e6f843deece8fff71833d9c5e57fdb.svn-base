package com.onlineMIS.ORM.entity.chainS.sales;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainStoreSalesOrderProduct extends BaseProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2014155412210076945L;
	
	/**
	 * for both sale-out product the type is 0
	 */
	public static final int SALES_OUT = 1;
    /**
     * for the return-back product is 2
     */
	public static final int RETURN_BACK = 2;
	/**
	 * for the 赠品
	 */
	public static final int FREE = 3;
	
    private int id;
    private int type;
	private ChainStoreSalesOrder chainSalesOrder;
	private ProductBarcode productBarcode = new ProductBarcode();
	private String memo;
	private double costPrice;
	private double retailPrice;
	private double discountRate = 1;
	private int quantity;
    /**
     * the product inventory level by calculation
     */
    private int inventoryLevel;
    private int normalSale;
    

	public int getNormalSale() {
		return normalSale;
	}
	public void setNormalSale(int normalSale) {
		this.normalSale = normalSale;
	}
	public int getInventoryLevel() {
		return inventoryLevel;
	}
	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChainStoreSalesOrder getChainSalesOrder() {
		return chainSalesOrder;
	}
	public void setChainSalesOrder(ChainStoreSalesOrder chainSalesOrder) {
		this.chainSalesOrder = chainSalesOrder;
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	
}
