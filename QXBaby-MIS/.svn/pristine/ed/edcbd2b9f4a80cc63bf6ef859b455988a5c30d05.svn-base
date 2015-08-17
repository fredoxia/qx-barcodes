package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainInitialStock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6640678575636160970L;

	private ChainInitialStockId id = new ChainInitialStockId();
    private double cost;
    private double costTotal;
    private double salePrice;
    private double salePriceTotal;
    private int quantity;
    private Date date;
    private ProductBarcode productBarcode = new ProductBarcode();
    
	public ChainInitialStockId getId() {
		return id;
	}
	public void setId(ChainInitialStockId id) {
		this.id = id;
	}
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainInitialStock other = (ChainInitialStock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChainInitialStock [id=" + id + ", cost=" + cost
				+ ", costTotal=" + costTotal + ", salePrice=" + salePrice
				+ ", salePriceTotal=" + salePriceTotal + ", quantity="
				+ quantity + ", productBarcode=" + productBarcode.getId() + "," + productBarcode.getBarcode() + "]";
	}

    
}
