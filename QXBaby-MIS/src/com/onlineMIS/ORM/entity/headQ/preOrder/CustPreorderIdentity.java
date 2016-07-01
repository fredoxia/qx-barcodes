package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class CustPreorderIdentity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6385842852094944665L;
	private String orderIdentity;
	private String brands;
	
	
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getOrderIdentity() {
		return orderIdentity;
	}
	public void setOrderIdentity(String orderIdentity) {
		this.orderIdentity = orderIdentity;
	}
	
	
}
