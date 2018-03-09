package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;


public class ChainInventoryFlowOrderProduct  extends BaseProduct  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5036399563895853707L;
	private int id;
	private ChainInventoryFlowOrder flowOrder = new ChainInventoryFlowOrder();
	private ProductBarcode productBarcode = new ProductBarcode();
	private int quantity;
	private int inventoryQ;
	private int quantityDiff;
	private String comment = "";
	private double totalSalesPrice = 0;
	private double wholeSalesPrice = 0;
	private double totalWholeSalesPrice = 0;

	
	public double getWholeSalesPrice() {
		return wholeSalesPrice;
	}
	public void setWholeSalesPrice(double wholeSalesPrice) {
		this.wholeSalesPrice = wholeSalesPrice;
	}
	public double getTotalWholeSalesPrice() {
		return totalWholeSalesPrice;
	}
	public void setTotalWholeSalesPrice(double totalWholeSalesPrice) {
		this.totalWholeSalesPrice = totalWholeSalesPrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInventoryQ() {
		return inventoryQ;
	}
	public void setInventoryQ(int inventoryQ) {
		this.inventoryQ = inventoryQ;
	}
	public ChainInventoryFlowOrder getFlowOrder() {
		return flowOrder;
	}
	public void setFlowOrder(ChainInventoryFlowOrder flowOrder) {
		this.flowOrder = flowOrder;
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

	public int getQuantityDiff() {
		return quantityDiff;
	}
	public void setQuantityDiff(int quantityDiff) {
		this.quantityDiff = quantityDiff;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public double getTotalSalesPrice() {
		return totalSalesPrice;
	}
	public void setTotalSalesPrice(double totalSalesPrice) {
		this.totalSalesPrice = totalSalesPrice;
	}
	@Override
	public String toString() {
		return "ChainInventoryFlowOrderProduct [id=" + id + ", orderId="
				+ flowOrder.getId() + ", product=" + productBarcode.getId() + ", quantity=" + quantity
			    + ", comment=" + comment + "]";
	}

}
