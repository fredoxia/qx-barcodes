package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainInitialStockId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1571110781919988852L;
	private String barcode;
    private int clientId;

    
    public ChainInitialStockId(String barcode, int clientId){
    	this.barcode = barcode;
    	this.clientId = clientId;
    }
    
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public ChainInitialStockId() {
		// TODO Auto-generated constructor stub
	}

	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + clientId;
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
		ChainInitialStockId other = (ChainInitialStockId) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (clientId != other.clientId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChainInitialStockId [barcode=" + barcode + ", clientId="
				+ clientId + "]";
	}

	
	
}
