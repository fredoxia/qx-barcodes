package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainTransferOrderVO {
	private int id;
	private String fromChain ;
	private String toChain;
	private String creator;
	private int quantity;
	private double wholePrice;
	private double salesPrice;
	private String orderDate;
	private String comment;
	private String userComment;
	private String status;
	
	public ChainTransferOrderVO(){
		
	}
	
	public ChainTransferOrderVO(ChainTransferOrder order, boolean displayWholePrice){
		ChainStore fromChainStore = order.getFromChainStore();
		ChainStore toChainStore = order.getToChainStore();
		ChainStore dummyStore = ChainStoreDaoImpl.getOutsideStore();
		if (fromChainStore == null)
			fromChain = dummyStore.getChain_name();
		else 
			fromChain = fromChainStore.getChain_name();
		if (toChainStore == null)
			toChain = dummyStore.getChain_name();
		else 
			toChain = toChainStore.getChain_name();	
		quantity = order.getTotalQuantity();
		
		if (displayWholePrice == true)
		    wholePrice = order.getTotalWholeSalesPrice();
		salesPrice = order.getTotalSalesPrice();
		orderDate = Common_util.dateFormat.format(order.getOrderDate());
		comment = order.getComment();
		userComment = order.getUserComment();
		status = order.getStatusS();
		creator = order.getCreator();
		id = order.getId();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromChain() {
		return fromChain;
	}
	public void setFromChain(String fromChain) {
		this.fromChain = fromChain;
	}
	public String getToChain() {
		return toChain;
	}
	public void setToChain(String toChain) {
		this.toChain = toChain;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getWholePrice() {
		return wholePrice;
	}
	public void setWholePrice(double wholePrice) {
		this.wholePrice = wholePrice;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserComment() {
		return userComment;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	
	

}
