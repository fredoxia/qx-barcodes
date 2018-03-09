package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ChainRoleType implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4595888084252900421L;
	public static final int CHAIN_ADMIN= 1;
    public static final int CHAIN_MGMT= 2;
    public static final int CHAIN_OWNER= 3;
    public static final int CHAIN_LEAD= 4;
    public static final int CHAIN_STAFF= 5;
    public static final int SELF_CHAIN_OWNER= 6;
    public static final int SELE_CHAIN_STAFF= 7;
	
	private int chainRoleTypeId;
	private String chainRoleTypeName;
	
	private Set<ChainUserFunctionality> chainUserFunctionalities =new HashSet<ChainUserFunctionality>();
	
	public Set<ChainUserFunctionality> getChainUserFunctionalities() {
		return chainUserFunctionalities;
	}
	public void setChainUserFunctionalities(
			Set<ChainUserFunctionality> chainUserFunctionalities) {
		this.chainUserFunctionalities = chainUserFunctionalities;
	}
	public int getChainRoleTypeId() {
		return chainRoleTypeId;
	}
	public void setChainRoleTypeId(int chainRoleTypeId) {
		this.chainRoleTypeId = chainRoleTypeId;
	}
	public String getChainRoleTypeName() {
		return chainRoleTypeName;
	}
	public void setChainRoleTypeName(String chainRoleTypeName) {
		this.chainRoleTypeName = chainRoleTypeName;
	}
	
	public boolean isAdmin(){
		if (getChainRoleTypeId() == CHAIN_ADMIN)
			return true;
		else 
			return false;
	}
	
	public boolean isMgmt(){
		if (getChainRoleTypeId() == CHAIN_MGMT)
			return true;
		else 
			return false;
	}
	
	public boolean isOwner(){
		if (getChainRoleTypeId() == CHAIN_OWNER)
			return true;
		else 
			return false;
	}

}
