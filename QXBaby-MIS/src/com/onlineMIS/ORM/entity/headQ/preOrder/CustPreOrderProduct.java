package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class CustPreOrderProduct extends BaseProduct{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6385842852094944665L;
	/**
	 * 导出单据的时候，快速查找的栏位
	 */
	private int preorderId; 
    private int pbId;   
	
	private ProductBarcode productBarcode = new ProductBarcode();
	private CustPreOrder preOrder = new CustPreOrder();

    private int totalQuantity;
    private double sumCost;
    private double sumWholePrice;
    private double sumRetailPrice;
    
    public CustPreOrderProduct(){

    }
    
	
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}


	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}


	public CustPreOrder getPreOrder() {
		return preOrder;
	}


	public void setPreOrder(CustPreOrder preOrder) {
		this.preOrder = preOrder;
	}


	public int getPreorderId() {
		return preorderId;
	}
	public void setPreorderId(int preorderId) {
		this.preorderId = preorderId;
	}


	public int getPbId() {
		return pbId;
	}
	public void setPbId(int pbId) {
		this.pbId = pbId;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public double getSumCost() {
		return sumCost;
	}
	public void setSumCost(double sumCost) {
		this.sumCost = sumCost;
	}
	public double getSumWholePrice() {
		return sumWholePrice;
	}
	public void setSumWholePrice(double sumWholePrice) {
		this.sumWholePrice = sumWholePrice;
	}
	public double getSumRetailPrice() {
		return sumRetailPrice;
	}
	public void setSumRetailPrice(double sumRetailPrice) {
		this.sumRetailPrice = sumRetailPrice;
	}
    
}
