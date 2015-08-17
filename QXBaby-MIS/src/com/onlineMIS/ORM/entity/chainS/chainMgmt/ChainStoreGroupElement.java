package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainStoreGroupElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7319574456921346340L;
	
	private int id;
	private int chainId;
	private ChainStoreGroup chainGroup = new ChainStoreGroup();
	
	
	public ChainStoreGroup getChainGroup() {
		return chainGroup;
	}
	public void setChainGroup(ChainStoreGroup chainGroup) {
		this.chainGroup = chainGroup;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	

}
