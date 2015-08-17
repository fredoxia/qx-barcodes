package com.onlineMIS.action.headQ.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class InventoryOrderActionUIBean implements Serializable {
	
	private InventoryOrder order = new InventoryOrder();
	private List<UserInfor> users  = new ArrayList<UserInfor>();
	private List<InventoryOrder> orders  = new ArrayList<InventoryOrder>();
	
	//the parm for the search
	private Map<Integer, String> typesMap = new HashMap<Integer, String>();
	private Map<Integer, String> orderStatusMap = new HashMap<Integer, String>();
	private Map<Integer, String> orderTypeMap = new HashMap<Integer, String>();
	
	public List<InventoryOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<InventoryOrder> orders) {
		this.orders = orders;
	}
	public InventoryOrder getOrder() {
		return order;
	}
	public void setOrder(InventoryOrder order) {
		this.order = order;
	}
	public List<UserInfor> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfor> users) {
		this.users = users;
	}
	public Map<Integer, String> getTypesMap() {
		return typesMap;
	}
	public void setTypesMap(Map<Integer, String> typesMap) {
		this.typesMap = typesMap;
	}
	public Map<Integer, String> getOrderStatusMap() {
		return orderStatusMap;
	}
	public void setOrderStatusMap(Map<Integer, String> orderStatusMap) {
		this.orderStatusMap = orderStatusMap;
	}
	public Map<Integer, String> getOrderTypeMap() {
		return orderTypeMap;
	}
	public void setOrderTypeMap(Map<Integer, String> orderTypeMap) {
		this.orderTypeMap = orderTypeMap;
	}
	
	
}
