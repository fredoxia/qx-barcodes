package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;


public class ChainInOutStockArchive implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3535346499179201903L;

	private String barcode;
    private int clientId;
    private String orderId;
    private int type;
    private double cost;
    private double costTotal;
    private double salePrice;
    private double salePriceTotal;
    private double chainSalePriceTotal;
    private int quantity;
    private Date date;
    private ProductBarcode productBarcode;
    private Date archiveDate;

   
	public ChainInOutStockArchive(){
    	
    }
    
    public ChainInOutStockArchive(ChainInOutStock c){
    	this.barcode = c.getBarcode();
    	this.clientId = c.getClientId();
    	this.orderId = c.getOrderId();
    	this.type = c.getType();
    	this.cost = c.getCost();
    	this.costTotal = Common_util.roundDouble(c.getCostTotal(),2);

    	this.salePrice = Common_util.roundDouble(c.getSalePrice(),2);
    	this.salePriceTotal = Common_util.roundDouble(c.getSalePriceTotal(),2);
    	this.quantity = c.getQuantity();
    	this.chainSalePriceTotal = Common_util.roundDouble(c.getChainSalePriceTotal(), 2);
    	this.date = c.getDate();
    	this.productBarcode = c.getProductBarcode();
    	archiveDate = new Date();
    	
    }
    

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(double costTotal) {
		this.costTotal = costTotal;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getSalePriceTotal() {
		return salePriceTotal;
	}

	public void setSalePriceTotal(double salePriceTotal) {
		this.salePriceTotal = salePriceTotal;
	}

	public double getChainSalePriceTotal() {
		return chainSalePriceTotal;
	}

	public void setChainSalePriceTotal(double chainSalePriceTotal) {
		this.chainSalePriceTotal = chainSalePriceTotal;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
 
}
