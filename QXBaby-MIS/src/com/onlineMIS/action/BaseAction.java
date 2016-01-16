package com.onlineMIS.action;

import java.util.HashSet;
import java.util.Set;

import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int page;// 当前页
	protected int rows;// 每页显示记录数
	protected String sort;// 排序字段
	protected String order;// asc/desc
	public static final String TOKEN_ID = "TOKEN_ID";
	
	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}
	
	public String createToken(String token){
		Object tokenObject = ActionContext.getContext().getSession().get(TOKEN_ID);
		
		if (token == null)
		    token = Common_util.getUUID();
		
		Set<String> tokenSet = null;
		
		if (tokenObject != null){
			tokenSet = (HashSet<String>) tokenObject;			
		} else 
			tokenSet = new HashSet<String>();
		
		tokenSet.add(token);
		ActionContext.getContext().getSession().put(TOKEN_ID, tokenSet);

		return token;
	}
	
	public boolean removeToken(String token){
		Object tokenObject = ActionContext.getContext().getSession().get(TOKEN_ID);
		
		
		Set<String> tokenSet = null;
		
		if (tokenObject != null){
			tokenSet = (HashSet<String>) tokenObject;	
			tokenSet.remove(token);
			ActionContext.getContext().getSession().put(TOKEN_ID, tokenSet);

			return true;
		} else {
			loggerLocal.error("TOKEN_ID为空，无法删除TOKEN : " + token);
			return false;
		}
	}
	
	public boolean isValidToken(String token){
		Object tokenObject = ActionContext.getContext().getSession().get(TOKEN_ID);

		if (tokenObject != null){
			Set<String> tokenSet = (HashSet<String>) tokenObject;	
//			System.out.println(tokenSet);
//			System.out.println(token);
			
			if (tokenSet.contains(token))
				return true;
			else 
				return false;
		} else {
			return false;
		}
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
