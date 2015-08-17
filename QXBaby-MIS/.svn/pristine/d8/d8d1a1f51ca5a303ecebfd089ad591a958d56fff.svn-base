package com.onlineMIS.ORM.entity.headQ.user;

import java.io.Serializable;

public class UserFunctionality implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6828068776892529503L;

	/**
	 * 管理所有员工的绩效考核
	 */
	public static final int EVALUATION_MANAGE_ALL_EVALUATER = 13;
	public static final int CREATE_EVALUATION  =12;
	
	public static final int PDA_FUNCTION = 24;
	public static final int CONFIRM_DELETE_ORDER = 26;
	
	/**
	 * the user function in chain
	 */
	public static final int CHAIN_ADMIN_FUNCTION_TYPE = 91;
	public static final int CHAIN_MGMT_FUNCTION_TYPE  = 92;
	
	
	/**
	 * to view all peoson's evaluation summary
	 */
	public static final int EVALUATION_VIEW_SUMMARY = 15;
	
	private int id;
	private int user_id;
	private int function_id;
	
	public UserFunctionality(){
		
	}
	
	public UserFunctionality(int user_id, int function_id){
		this.user_id = user_id;
		this.function_id = function_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFunction_id() {
		return function_id;
	}
	public void setFunction_id(int function_id) {
		this.function_id = function_id;
	}
	@Override
	public int hashCode() {
		final int prime = 1;
		int result = 1;
		result = prime * result + function_id;
		result = prime * result + user_id;
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
		UserFunctionality other = (UserFunctionality) obj;
		if (function_id != other.function_id)
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}
	

}
