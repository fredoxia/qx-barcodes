package com.onlineMIS.ORM.entity.shared.expense;

import java.io.Serializable;

public class ExpenseType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9194070545975560853L;
	private int id;
	private Integer parentId;
	private String name;
	private Integer belong;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String expenseName) {
		this.name = expenseName;
	}

	public Integer getBelong() {
		return belong;
	}


	public void setBelong(Integer belong) {
		this.belong = belong;
	}

	public enum belongE{
		HEADQ ("总部", null), 
		CHAIN ("连锁店", new Integer(-1));
		
		private Integer type;
		private String name;

		private belongE(String name, Integer type) {
			this.name = name;
			this.type = type;
		}

		public Integer getType() {
			return type;
		}

		public String getName() {
			return name;
		}
		
		
	}

}
