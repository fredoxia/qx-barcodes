package com.onlineMIS.ORM.entity.chainS.user;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.common.Common_util;

public class ChainStore implements Serializable {
	/**
	 * 
	 */
	public static final int STATUS_NOT_ACTIVE = 2;
	public static final int STATUS_ACTIVE = 0;
	public static final int STATUS_DELETE = -1;
	
	public static final int HEADQ_MGMT_ID = 0;
	public static final int CLIENT_ID_TEST_ID = 99999;
	public static final int CHAIN_ID_TEST_ID = 3;
	//允许用户修改价格不
	public static final int ALLOW_CHANGE_PRICE = 1;
	public static final int DISALLOW_CHANGE_PRICE = 0;
	
	//允许用户天骄条码否
	public static final int ALLOW_ADD_BARCODE= 1;
	public static final int DISALLOW_ADD_BARCODE = 0;
	
	private static final long serialVersionUID = 1L;
	
	private static Map<Integer, String> statusMap = new LinkedHashMap<Integer, String>();
	
	static {
		statusMap.put(STATUS_NOT_ACTIVE, "不活跃");
		statusMap.put(STATUS_ACTIVE, "活跃");
	}
	private int chain_id;
	private String chain_name;
	private String owner_name;
	private int client_id;
	/**
	 * 正常活跃:0, 停用:1, 偶尔使用:2 
	 */
	private int status;
	private double initialAcctAmt;
	private int printTemplate;
	private int printCopy;
	private int allowChangeSalesPrice = 0;
	private String pinYin;
	private ChainPriceIncrement priceIncrement;
	
	//小票上的抬头。千禧宝贝和嬉乐仓
	private String printHeader = "千禧宝贝童装连锁";
	
	/**
	 * 0: not allow
	 * 1: allow
	 */
	private int allowAddBarcode;
	private Date activeDate;
	
	private ChainStore parentStore;

	
	public String getPrintHeader() {
		return printHeader;
	}

	public void setPrintHeader(String printHeader) {
		this.printHeader = printHeader;
	}

	public int getAllowAddBarcode() {
		return allowAddBarcode;
	}

	public void setAllowAddBarcode(int allowAddBarcode) {
		this.allowAddBarcode = allowAddBarcode;
	}

	public ChainPriceIncrement getPriceIncrement() {
		return priceIncrement;
	}

	public void setPriceIncrement(ChainPriceIncrement priceIncrement) {
		this.priceIncrement = priceIncrement;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pingYin) {
		this.pinYin = pingYin;
	}



	public int getAllowChangeSalesPrice() {
		return allowChangeSalesPrice;
	}

	public void setAllowChangeSalesPrice(int allowChangeSalesPrice) {
		this.allowChangeSalesPrice = allowChangeSalesPrice;
	}
	public int getPrintTemplate() {
		return printTemplate;
	}
	public void setPrintTemplate(int printTemplate) {
		this.printTemplate = printTemplate;
	}
	public int getPrintCopy() {
		return printCopy;
	}
	public void setPrintCopy(int printCopy) {
		this.printCopy = printCopy;
	}
	public double getInitialAcctAmt() {
		return initialAcctAmt;
	}
	public void setInitialAcctAmt(double initialAcctAmt) {
		this.initialAcctAmt = initialAcctAmt;
	}
	public int getChain_id() {
		return chain_id;
	}
	public void setChain_id(int chain_id) {
		this.chain_id = chain_id;
	}
	public String getChain_name() {
		return chain_name;
	}
	public void setChain_name(String chain_name) {
		this.chain_name = chain_name;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public static Map<Integer, String> getStatusMap() {
		return statusMap;
	}
	
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public ChainStore getParentStore() {
		return parentStore;
	}

	public void setParentStore(ChainStore parentStore) {
		this.parentStore = parentStore;
	}

	@Override
	public String toString() {
		return "ChainStore [chain_id=" + chain_id + ", chain_name="
				+ chain_name + "]";
	}
	
	
	
}
