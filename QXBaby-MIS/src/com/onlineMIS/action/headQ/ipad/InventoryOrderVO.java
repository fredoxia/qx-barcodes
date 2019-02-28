package com.onlineMIS.action.headQ.ipad;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;

public class InventoryOrderVO {
	private String clientName;
	private int totalQ;
	private double totalW;
	private int orderId;
	private String createDate;

	public static List<InventoryOrderVO> parse(List<InventoryOrder> orders){
		List<InventoryOrderVO> productVOs = new ArrayList<InventoryOrderVO>();
		
		for (InventoryOrder product : orders){
			InventoryOrderVO productVO = new InventoryOrderVO(product);
			productVOs.add(productVO);
		}
		
		return productVOs;
	}
	
	public InventoryOrderVO(InventoryOrder order) {
		this.setClientName(order.getClient_name());
		this.setTotalQ(order.getTotalQuantity());
		this.setTotalW(order.getTotalWholePrice());
		this.setOrderId(order.getOrder_ID());
		
		this.setCreateDate(Common_util.dateFormat.format(order.getOrder_StartTime()));
	}
	
    public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public InventoryOrderVO() {
	}
	
	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public int getTotalQ() {
		return totalQ;
	}


	public void setTotalQ(int totalQ) {
		this.totalQ = totalQ;
	}


	public double getTotalW() {
		return totalW;
	}


	public void setTotalW(double totalW) {
		this.totalW = totalW;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
