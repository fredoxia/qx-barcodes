package com.onlineMIS.action.chainS.inventoryFlow;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInvenTraceInfoVO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItem;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;


public class ChainInventoryFlowUIBean{
	
	/**
	 * those UI Bean is for the sales order search page's drop down
	 */
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	private List<ChainStore> toChainStores = new ArrayList<ChainStore>();
    private List<ChainInventoryFlowOrder> invenFlowOrders = new ArrayList<ChainInventoryFlowOrder>();
    private Map<Integer, String> invenOrderTypes = new HashMap<Integer, String>();
    private Map<Integer, String> invenOrderStatus = new HashMap<Integer, String>();
	private ChainInventoryFlowOrder flowOrder = new ChainInventoryFlowOrder();

	/**
	 * the UI Bean for the current inventory
	 */
	private ChainInventoryItem inventoryItem = new ChainInventoryItem();

	private List<ChainInvenTraceInfoVO> traceItems = new ArrayList<ChainInvenTraceInfoVO>();
	private ChainInvenTraceInfoVO traceFooter = new ChainInvenTraceInfoVO();


	public List<ChainInvenTraceInfoVO> getTraceItems() {
		return traceItems;
	}

	public void setTraceItems(List<ChainInvenTraceInfoVO> traceItems) {
		this.traceItems = traceItems;
	}

	public ChainInvenTraceInfoVO getTraceFooter() {
		return traceFooter;
	}

	public void setTraceFooter(ChainInvenTraceInfoVO traceFooter) {
		this.traceFooter = traceFooter;
	}

	public List<ChainStore> getToChainStores() {
		return toChainStores;
	}

	public void setToChainStores(List<ChainStore> toChainStores) {
		this.toChainStores = toChainStores;
	}

	public ChainInventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(ChainInventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}


	public ChainInventoryFlowOrder getFlowOrder() {
		return flowOrder;
	}

	public void setFlowOrder(ChainInventoryFlowOrder flowOrder) {
		this.flowOrder = flowOrder;
	}

	public List<ChainInventoryFlowOrder> getInvenFlowOrders() {
		return invenFlowOrders;
	}

	public void setInvenFlowOrders(List<ChainInventoryFlowOrder> invenFlowOrders) {
		this.invenFlowOrders = invenFlowOrders;
	}

	public List<ChainStore> getChainStores() {
		return chainStores;
	}

	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}

	public Map<Integer, String> getInvenOrderTypes() {
		return invenOrderTypes;
	}

	public void setInvenOrderTypes(Map<Integer, String> invenOrderTypes) {
		this.invenOrderTypes = invenOrderTypes;
	}

	public Map<Integer, String> getInvenOrderStatus() {
		return invenOrderStatus;
	}

	public void setInvenOrderStatus(Map<Integer, String> invenOrderStatus) {
		this.invenOrderStatus = invenOrderStatus;
	}
	
	
}
