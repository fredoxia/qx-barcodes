package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

public class ChainInventoryVO {
	private String chainStoreName ;
	private int quantity;
	
	public ChainInventoryVO(String chainName, int quantity){
		this.chainStoreName = chainName;
		this.quantity = quantity;
	}
	
	public String getChainStoreName() {
		return chainStoreName;
	}
	public void setChainStoreName(String chainStoreName) {
		this.chainStoreName = chainStoreName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
