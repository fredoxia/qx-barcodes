package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.headQ.user.News;

public class HeadQSalesHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6985004647601265953L;
    private HeadQSalesHistoryId id = new HeadQSalesHistoryId();
	private double recCost;
	private double wholePrice;
	private double salesPrice;
	private int quantity;
    private double discount;
    private double salePriceSelected;
	
	public HeadQSalesHistory(){	}
	
	public HeadQSalesHistory(int productId, int clientId, double recCost, double wholePrice, double salesPrice, int quantity, double salePriceSelected, double discount){
		this.id.setProductId(productId);
		this.id.setClientId(clientId);
		this.recCost = recCost;
		this.wholePrice = wholePrice;
		this.salesPrice = salesPrice;
		this.quantity = quantity;
		this.discount = discount;
		this.salePriceSelected = salePriceSelected;
	}
	

	public HeadQSalesHistoryId getId() {
		return id;
	}

	public void setId(HeadQSalesHistoryId id) {
		this.id = id;
	}

	public double getRecCost() {
		return recCost;
	}
	public void setRecCost(double recCost) {
		this.recCost = recCost;
	}
	public double getWholePrice() {
		return wholePrice;
	}
	public void setWholePrice(double wholePrice) {
		this.wholePrice = wholePrice;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
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
		HeadQSalesHistory other = (HeadQSalesHistory) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "[id=" + id + "]";
	} 
	
	
}
