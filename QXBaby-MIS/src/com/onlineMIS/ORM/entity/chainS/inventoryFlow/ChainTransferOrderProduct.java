package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;


public class ChainTransferOrderProduct  extends BaseProduct  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5036399563895853707L;
	private ProductBarcode productBarcode;
	private ChainTransferOrder transferOrder = new ChainTransferOrder();

	private int quantity;
	private double salesPrice = 0;
	private double wholeSalesPrice = 0;
	private double totalSalesPrice = 0;
	private double totalWholeSalesPrice = 0;
	private int productTypeFlag;

	
	
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public ChainTransferOrder getTransferOrder() {
		return transferOrder;
	}
	public void setTransferOrder(ChainTransferOrder transferOrder) {
		this.transferOrder = transferOrder;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public double getWholeSalesPrice() {
		return wholeSalesPrice;
	}
	public void setWholeSalesPrice(double wholeSalesPrice) {
		this.wholeSalesPrice = wholeSalesPrice;
	}
	public double getTotalSalesPrice() {
		return totalSalesPrice;
	}
	public void setTotalSalesPrice(double totalSalesPrice) {
		this.totalSalesPrice = totalSalesPrice;
	}
	public double getTotalWholeSalesPrice() {
		return totalWholeSalesPrice;
	}
	public void setTotalWholeSalesPrice(double totalWholeSalesPrice) {
		this.totalWholeSalesPrice = totalWholeSalesPrice;
	}
	public int getProductTypeFlag() {
		return productTypeFlag;
	}
	public void setProductTypeFlag(int productTypeFlag) {
		this.productTypeFlag = productTypeFlag;
	}
}
