package com.onlineMIS.ORM.entity.headQ.user;

import java.io.Serializable;

public class News implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1266675985792250208L;
	public static final int TYPE_ALL = 0;
	public static final int TYPE_HEAD_Q = 1;
	public static final int TYPE_CHAIN_S = 2;
	
	private int id;
	private int type = TYPE_ALL;
	private String title;
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}