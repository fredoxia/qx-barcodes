package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;

public class ChainUserFunctionality implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6828068776892529503L;

	private int id;
	private int functionId;
	private int chainRoleTypeId;
	
	public ChainUserFunctionality(){
		
	}
	
	public ChainUserFunctionality(int chainRoleTypeId, int functionId){
		this.chainRoleTypeId = chainRoleTypeId;
		this.functionId = functionId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFunctionId() {
		return functionId;
	}
	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}
	public int getChainRoleTypeId() {
		return chainRoleTypeId;
	}
	public void setChainRoleTypeId(int chainRoleTypeId) {
		this.chainRoleTypeId = chainRoleTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chainRoleTypeId;
		result = prime * result + functionId;
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
		ChainUserFunctionality other = (ChainUserFunctionality) obj;
		if (chainRoleTypeId != other.chainRoleTypeId)
			return false;
		if (functionId != other.functionId)
			return false;
		return true;
	}

}
