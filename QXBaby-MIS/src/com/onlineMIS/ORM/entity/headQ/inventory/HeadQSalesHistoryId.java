package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.Serializable;

public class HeadQSalesHistoryId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4033701225202404916L;
	private int productId;
	private int clientId; 
	
	public HeadQSalesHistoryId(){
		
	}
	public HeadQSalesHistoryId(int productId, int clientId){
		this.productId = productId;
		this.clientId = clientId;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clientId;
		result = prime * result + productId;
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
		HeadQSalesHistoryId other = (HeadQSalesHistoryId) obj;
		if (clientId != other.clientId)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "[productId=" + productId + ", clientId="
				+ clientId + "]";
	}
	
	
}
