package com.onlineMIS.ORM.entity.headQ.SQLServer;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ColorGroup;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.SizeGroup;

public class ProductsMS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8172088840331546037L;

	public static final int AVAILABLE_STATUS = 0;
	
	private int productID;
	private String serial_number;
	private String productCode;
	private PriceMS price;
	private int deleted;
	private double discount;
	private int unit_id;
	private int sizeGroupId;
	private int colorGroupId;
		
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public PriceMS getPrice() {
		return price;
	}
	public void setPrice(PriceMS price) {
		this.price = price;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public int getSizeGroupId() {
		return sizeGroupId;
	}
	public void setSizeGroupId(int sizeGroupId) {
		this.sizeGroupId = sizeGroupId;
	}
	public int getColorGroupId() {
		return colorGroupId;
	}
	public void setColorGroupId(int colorGroupId) {
		this.colorGroupId = colorGroupId;
	}

}
