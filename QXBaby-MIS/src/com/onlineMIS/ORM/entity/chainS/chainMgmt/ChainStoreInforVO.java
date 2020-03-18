package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainStoreInforVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4564829998863804505L;
	private String chain_name;
	private String owner_name;
	private String pinYin;
	private String shippingAddress = "";
	public String getChain_name() {
		return chain_name;
	}
	public void setChain_name(String chain_name) {
		this.chain_name = chain_name;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	
}
