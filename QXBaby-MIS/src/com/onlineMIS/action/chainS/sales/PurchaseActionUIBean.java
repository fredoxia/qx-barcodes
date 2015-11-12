package com.onlineMIS.action.chainS.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;

public class PurchaseActionUIBean {
	private String order_type;
	private InventoryOrder order;
	private ChainStore chainStore = new ChainStore();
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	private List<InventoryOrder> orders = new ArrayList<InventoryOrder>();
	private Map<Integer, String> chainConfirmList = new HashMap<Integer, String>();
	
	
	public Map<Integer, String> getChainConfirmList() {
		return chainConfirmList;
	}

	public void setChainConfirmList(Map<Integer, String> chainConfirmList) {
		this.chainConfirmList = chainConfirmList;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}

	public List<ChainStore> getChainStores() {
		return chainStores;
	}

	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}

	//for search
	private Map<Integer, String> typesMap = new HashMap<Integer, String>();

	public InventoryOrder getOrder() {
		return order;
	}

	public void setOrder(InventoryOrder order) {
		this.order = order;
	}

	public List<InventoryOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<InventoryOrder> orders) {
		this.orders = orders;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public Map<Integer, String> getTypesMap() {
		return typesMap;
	}

	public void setTypesMap(Map<Integer, String> typesMap) {
		this.typesMap = typesMap;
	}
    
    

}
