package com.onlineMIS.action.headQ.inventory;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javassist.expr.NewArray;

import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;

public class InventoryOrderActionFormBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4371903022162799876L;

	private InventoryOrder order = new InventoryOrder();
	private UserInfor user = new UserInfor();
	
    //the two is for the search bean
	private Date search_Start_Time = Common_util.getToday();
    private Date search_End_Time = Common_util.getToday();
    private String sorting = "false";
    
    //file upload
    private File orderExcel = null;
    private String orderExcelContentType;
    private String orderExcelFileName;
    
    //indicator of preview
    private boolean isPreview;
    
    //client pinyin code
    private String pinyin;
    
    private Map<Integer, String> clientMap = new HashMap<Integer, String>();
    
	public UserInfor getUser() {
		return user;
	}

	public void setUser(UserInfor user) {
		this.user = user;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public boolean getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}

	public InventoryOrder getOrder() {
		return order;
	}

	public void setOrder(InventoryOrder order) {
		this.order = order;
	}
	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	public Date getSearch_Start_Time() {
		return search_Start_Time;
	}

	public void setSearch_Start_Time(Date search_Start_Time) {
		this.search_Start_Time = search_Start_Time;
	}

	public Date getSearch_End_Time() {
		return search_End_Time;
	}

	public void setSearch_End_Time(Date search_End_Time) {
		this.search_End_Time = search_End_Time;
	}

	public File getOrderExcel() {
		return orderExcel;
	}

	public void setOrderExcel(File orderExcel) {
		this.orderExcel = orderExcel;
	}

	public String getOrderExcelContentType() {
		return orderExcelContentType;
	}

	public void setOrderExcelContentType(String orderExcelContentType) {
		this.orderExcelContentType = orderExcelContentType;
	}

	public String getOrderExcelFileName() {
		return orderExcelFileName;
	}

	public void setOrderExcelFileName(String orderExcelFileName) {
		this.orderExcelFileName = orderExcelFileName;
	}

	public Map<Integer, String> getClientMap() {
		return clientMap;
	}

	public void setClientMap(Map<Integer, String> clientMap) {
		this.clientMap = clientMap;
	}
	
}
