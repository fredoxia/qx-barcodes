package com.onlineMIS.action.chainS.chainTransfer;

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
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferLog;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;


public class ChainTransferUIBean{
	private ChainTransferOrder order = new ChainTransferOrder();
	private List<ChainTransferLog> orderLogs = new ArrayList<ChainTransferLog>();
	
	private List<ChainTransferOrder> transferOrders = new ArrayList<ChainTransferOrder>();
	private List<ChainStore> fromChainStores = new ArrayList<ChainStore>();
	private List<ChainStore> toChainStores = new ArrayList<ChainStore>();
	
	private Map<Integer, String> orderStatus = new HashMap<Integer, String>();
	
	
	public Map<Integer, String> getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Map<Integer, String> orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<ChainTransferOrder> getTransferOrders() {
		return transferOrders;
	}
	public void setTransferOrders(List<ChainTransferOrder> transferOrders) {
		this.transferOrders = transferOrders;
	}
	public List<ChainStore> getFromChainStores() {
		return fromChainStores;
	}
	public void setFromChainStores(List<ChainStore> fromChainStores) {
		this.fromChainStores = fromChainStores;
	}
	public List<ChainStore> getToChainStores() {
		return toChainStores;
	}
	public void setToChainStores(List<ChainStore> toChainStores) {
		this.toChainStores = toChainStores;
	}
	public ChainTransferOrder getOrder() {
		return order;
	}
	public void setOrder(ChainTransferOrder order) {
		this.order = order;
	}
	public List<ChainTransferLog> getOrderLogs() {
		return orderLogs;
	}
	public void setOrderLogs(List<ChainTransferLog> orderLogs) {
		this.orderLogs = orderLogs;
	}
	
	
	
}
