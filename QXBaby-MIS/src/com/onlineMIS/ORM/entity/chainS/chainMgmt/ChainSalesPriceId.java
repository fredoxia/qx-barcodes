package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainSalesPriceId implements Serializable{
	private int chainId;
	private String barcode;
	
	public ChainSalesPriceId(){
		
	}
	
	public ChainSalesPriceId(int chainId, String barcode){
		this.chainId = chainId;
		this.barcode = barcode;
	}
	
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	

}
