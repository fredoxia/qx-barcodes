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
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelOneInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelFourInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelThreeInventoryItem;
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
	private List<ChainLevelTwoInventoryItem> levelTwoInventoryItem = new ArrayList<ChainLevelTwoInventoryItem>();
	private List<ChainLevelThreeInventoryItem> levelThreeInventoryItem = new ArrayList<ChainLevelThreeInventoryItem>();
	private List<ChainLevelFourInventoryItem> levelFourInventoryItem = new ArrayList<ChainLevelFourInventoryItem>();
	private List<ChainLevelOneInventoryItem> levelOneInventoryItem = new ArrayList<ChainLevelOneInventoryItem>();
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

	public List<ChainLevelTwoInventoryItem> getLevelTwoInventoryItem() {
		return levelTwoInventoryItem;
	}

	public void setLevelTwoInventoryItem(
			List<ChainLevelTwoInventoryItem> levelTwoInventoryItem) {
		this.levelTwoInventoryItem = levelTwoInventoryItem;
	}

	public List<ChainLevelThreeInventoryItem> getLevelThreeInventoryItem() {
		return levelThreeInventoryItem;
	}

	public void setLevelThreeInventoryItem(
			List<ChainLevelThreeInventoryItem> levelThreeInventoryItem) {
		this.levelThreeInventoryItem = levelThreeInventoryItem;
	}

	public List<ChainLevelFourInventoryItem> getLevelFourInventoryItem() {
		return levelFourInventoryItem;
	}

	public void setLevelFourInventoryItem(
			List<ChainLevelFourInventoryItem> levelFourInventoryItem) {
		this.levelFourInventoryItem = levelFourInventoryItem;
	}

	public List<ChainLevelOneInventoryItem> getLevelOneInventoryItem() {
		return levelOneInventoryItem;
	}

	public void setLevelOneInventoryItem(
			List<ChainLevelOneInventoryItem> levelOneInventoryItem) {
		this.levelOneInventoryItem = levelOneInventoryItem;
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
