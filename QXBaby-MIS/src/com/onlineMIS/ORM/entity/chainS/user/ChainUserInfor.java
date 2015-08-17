package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;


public class ChainUserInfor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5079784179894810789L;
	public static final int CHAIN_ADMIN_USER_ID = 1;
	
	public static final int RESIGNED = 1;
	public static final int ACTIVE =0;
	public static final String SEE_COST_FUN = "purchaseAction!seeCost";
	
	private int user_id;
	private String user_name;
	private String name;
	private String password;
	private ChainRoleType roleType = new ChainRoleType();
	private String mobilePhone;
	private ChainStore myChainStore;
	
	/**
	 * chain user's functions
	 */
	private List<String> chainUserFunctions = new ArrayList<String>();

	/**
	 * 0 means normal 
	 * 1 means resigned/retired, the account is disabled
	 */
	private int resign = 0;

	public List<String> getChainUserFunctions() {
		return chainUserFunctions;
	}
	public void setChainUserFunctions(List<String> chainUserFunctions) {
		this.chainUserFunctions = chainUserFunctions;
	}
	public ChainStore getMyChainStore() {
		return myChainStore;
	}
	public void setMyChainStore(ChainStore myChainStore) {
		this.myChainStore = myChainStore;
	}
	public int getResign() {
		return resign;
	}
	public void setResign(int resign) {
		this.resign = resign;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ChainRoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(ChainRoleType roleType) {
		this.roleType = roleType;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public boolean containFunction(String url){
		return chainUserFunctions.contains(url);
	}

	public void update(ChainUserInfor userCopy){

		this.setMobilePhone(userCopy.getMobilePhone());
		this.setName(userCopy.getName());
		this.setPassword(userCopy.getPassword());
		this.setResign(userCopy.getResign());
		this.setRoleType(userCopy.getRoleType());
		this.setUser_name(userCopy.getUser_name());
		this.setMyChainStore(userCopy.getMyChainStore());
	}
	
	@Override
	public String toString() {
		return "ChainUserInfor [user_id=" + user_id + ", user_name="
				+ user_name + "]";
	}
	
	

}
