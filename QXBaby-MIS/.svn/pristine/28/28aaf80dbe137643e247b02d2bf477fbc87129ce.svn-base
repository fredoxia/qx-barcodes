package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainSalesPrice implements Serializable{
	private ChainSalesPriceId id;
	private ProductBarcode pb = new ProductBarcode();
    private double chainSalesPrice;
    private Date lastUpdtDate;
    
    public ChainSalesPrice(){
    	
    }
    
    public ChainSalesPrice(String barcode, int chainId, ProductBarcode pb, double chainSalesPrice){
    	id = new ChainSalesPriceId(chainId, barcode);
    	this.pb = pb;
    	this.chainSalesPrice = chainSalesPrice;
    	lastUpdtDate = new Date();
    }
    
    
	public ChainSalesPriceId getId() {
		return id;
	}
	public void setId(ChainSalesPriceId id) {
		this.id = id;
	}

	public ProductBarcode getPb() {
		return pb;
	}

	public void setPb(ProductBarcode pb) {
		this.pb = pb;
	}

	public double getChainSalesPrice() {
		return chainSalesPrice;
	}
	public void setChainSalesPrice(double chainSalesPrice) {
		this.chainSalesPrice = chainSalesPrice;
	}
	public Date getLastUpdtDate() {
		return lastUpdtDate;
	}
	public void setLastUpdtDate(Date lastUpdtDate) {
		this.lastUpdtDate = lastUpdtDate;
	}
    
    
}
