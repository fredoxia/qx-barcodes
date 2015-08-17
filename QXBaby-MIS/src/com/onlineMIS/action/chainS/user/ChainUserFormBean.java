package com.onlineMIS.action.chainS.user;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;

/**
 * this bean is for the chain user action's form parameter
 * @author fredo
 *
 */
public class ChainUserFormBean {
	private ChainUserInfor chainUserInfor = new ChainUserInfor();
    private ChainRoleType roleType = new ChainRoleType();
    private List<Integer> functions = new ArrayList<Integer>();
    private Pager pager = new Pager();
    private String password ="";

	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}  

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Integer> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Integer> functions) {
		this.functions = functions;
	}

	public ChainRoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(ChainRoleType roleType) {
		this.roleType = roleType;
	}

	public ChainUserInfor getChainUserInfor() {
		return chainUserInfor;
	}

	public void setChainUserInfor(ChainUserInfor chainUserInfor) {
		this.chainUserInfor = chainUserInfor;
	}


	
	
}
