package com.onlineMIS.ORM.entity.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8620986433576197030L;
	public static final int STATUS_INITIAL = 0;
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_COMPLETE = 2;
	public static final int STATUS_CANCEL = 3;
	public static final int STATUS_DELETED = 4;
	
	/**
	 * the two should be mapping to database
	 */
	protected int type;
	protected int status;
	
	/**
	 * this is the type for headq
	 */
	protected Map<Integer, String> typeHQMap = new LinkedHashMap<Integer, String>();
	protected String typeHQS;
	/**
	 * this is the type for chain
	 */
	protected Map<Integer, String> typeChainMap = new LinkedHashMap<Integer, String>();
	protected String typeChainS;

	protected Map<Integer, String> statusMap = new LinkedHashMap<Integer, String>();
	protected String statusS;
	
	{
		statusMap.put(STATUS_DRAFT, "草稿");
		statusMap.put(STATUS_COMPLETE, "完成");
		statusMap.put(STATUS_CANCEL, "红冲");
		//statusMap.put(REVIEWED, "审核");
	}
	

    public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusS() {
		return statusMap.get(status);
	}
	
	public Map<Integer, String> getStatusMap(){
		return statusMap;
	}
	
	public String getTypeHQS(){
		return typeHQMap.get(type);
	}
	public Map<Integer, String> getTypeHQMap(){
		return typeHQMap;
	}
	
	public String getTypeChainS(){
		return typeChainMap.get(type);
	}
	public Map<Integer, String> getTypeChainMap(){
		return typeChainMap;
	}
	
	
}
