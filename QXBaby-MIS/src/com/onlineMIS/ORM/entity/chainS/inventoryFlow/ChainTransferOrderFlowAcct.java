package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.sorter.ChainInventoryOrderProductSorter;
import com.onlineMIS.sorter.ProductSortByIndex;
/**
 * This class is 
 * @author fredo
 *
 */
public class ChainTransferOrderFlowAcct extends BaseOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659566872235652808L;
	private int orderId;
	private int acctChainStoreId;
	private int totalQuantity = 0;
	private double totalWholeSalesPrice = 0;
	private double totalSalesPrice = 0;
	private double flowAcctAmt = 0;
	private Date orderDate = new Date();
	private Date acctFlowDate = new Date();
	private String comment = "";
	private String userComment = "";
	private String creator = "";
	private String confirmedBy = "";
	private String fromChainStore = "";
	private String toChainStore = "";
	private double transportationFee ;
	
	public ChainTransferOrderFlowAcct() {
		// TODO Auto-generated constructor stub
	}
	
	public ChainTransferOrderFlowAcct(ChainTransferOrder order, double flowAcctAmt, int chainStoreId){
		this.orderId = order.getId();
		this.totalQuantity = order.getTotalQuantity();
		this.totalWholeSalesPrice = order.getTotalWholeSalesPrice();
		this.totalSalesPrice = order.getTotalSalesPrice();
		this.orderDate = order.getOrderDate();
		this.acctFlowDate = Common_util.getToday();
		this.comment = order.getComment();
		this.userComment = order.getUserComment();
		this.creator = order.getCreator();
		this.confirmedBy = order.getConfirmedBy();
		this.acctChainStoreId = chainStoreId;
		this.flowAcctAmt = flowAcctAmt;
		this.fromChainStore = order.getFromChainStore().getChain_name();
		this.toChainStore = order.getToChainStore().getChain_name();
		this.transportationFee = order.getTransportationFee();		
	}
	
	public double getTransportationFee() {
		return transportationFee;
	}

	public void setTransportationFee(double transportationFee) {
		this.transportationFee = transportationFee;
	}

	public String getFromChainStore() {
		return fromChainStore;
	}

	public void setFromChainStore(String fromChainStore) {
		this.fromChainStore = fromChainStore;
	}

	public String getToChainStore() {
		return toChainStore;
	}

	public void setToChainStore(String toChainStore) {
		this.toChainStore = toChainStore;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getAcctChainStoreId() {
		return acctChainStoreId;
	}
	public void setAcctChainStoreId(int acctChainStoreId) {
		this.acctChainStoreId = acctChainStoreId;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public double getTotalWholeSalesPrice() {
		return totalWholeSalesPrice;
	}
	public void setTotalWholeSalesPrice(double totalWholeSalesPrice) {
		this.totalWholeSalesPrice = totalWholeSalesPrice;
	}
	public double getTotalSalesPrice() {
		return totalSalesPrice;
	}
	public void setTotalSalesPrice(double totalSalesPrice) {
		this.totalSalesPrice = totalSalesPrice;
	}
	public double getFlowAcctAmt() {
		return flowAcctAmt;
	}
	public void setFlowAcctAmt(double flowAcctAmt) {
		this.flowAcctAmt = flowAcctAmt;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getAcctFlowDate() {
		return acctFlowDate;
	}
	public void setAcctFlowDate(Date acctFlowDate) {
		this.acctFlowDate = acctFlowDate;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getConfirmedBy() {
		return confirmedBy;
	}
	public void setConfirmedBy(String confirmedBy) {
		this.confirmedBy = confirmedBy;
	}
	
	
}
