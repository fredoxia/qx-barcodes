package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;
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
public class ChainTransferLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659566872235652808L;
	private int orderId;
	private Date logTime;
	private String log;
	
	public ChainTransferLog(int orderId, String log){
		this.log = log;
		this.orderId = orderId;
		this.logTime = Common_util.getToday();
	}
	
	public ChainTransferLog(){
		
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}

	
}
