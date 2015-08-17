package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;


public class ChainUserStoreRelationship implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8178782333458375227L;
	private int id;
	private ChainUserInfor chainUser;
	private ChainStore chainStore;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public ChainUserInfor getChainUser() {
		return chainUser;
	}
	public void setChainUser(ChainUserInfor chainUser) {
		this.chainUser = chainUser;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}

	
	
}
