package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainStoreGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087394431705343252L;
	private int id;
	private String groupName;
	private Set<ChainStoreGroupElement> chainStoreGroupElementSet = new HashSet<ChainStoreGroupElement>();
	private List<ChainStoreGroupElement> chainStoreGroupElementList = new ArrayList<ChainStoreGroupElement>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Set<ChainStoreGroupElement> getChainStoreGroupElementSet() {
		return chainStoreGroupElementSet;
	}
	public void setChainStoreGroupElementSet(
			Set<ChainStoreGroupElement> chainStoreGroupElementSet) {
		this.chainStoreGroupElementSet = chainStoreGroupElementSet;
	}
	public List<ChainStoreGroupElement> getChainStoreGroupElementList() {
		return chainStoreGroupElementList;
	}
	public void setChainStoreGroupElementList(
			List<ChainStoreGroupElement> chainStoreGroupElementList) {
		this.chainStoreGroupElementList = chainStoreGroupElementList;
	}

	
}
