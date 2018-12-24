package com.onlineMIS.action.chainS.sales;

import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;


public class ChainSalesActionFormBean extends ChainActionFormBaseBean{
	private ChainStore chainStore ;
	private ChainStoreSalesOrder chainSalesOrder = new ChainStoreSalesOrder();
	private ChainStoreConf chainStoreConf = new ChainStoreConf();
	private int productId = 0;
	private int chainOrderPay = -1;
		
	/**
	 * the three are used to do the product scanning
	 */
	private String barcode;
	private String productCode;
	private int index;
	private String vipCardNo;
	private double discount;
	private double maxVipCash;
	private int chainId;
	/**
	 * used to indicate it is Return barcode or not
	 * for sales bacode is ""
	 * for return barcode is "R"
	 * for free barcode is "F"
	 */
	private String suffix ="";
	
	/**
	 * 
	 * the date are used to do the search order
	 */
	private Date search_Start_Time = new Date(new java.util.Date().getTime());
    private Date search_End_Time = new Date(new java.util.Date().getTime());
    private boolean initialSearch = false;
    private Pager pager = new Pager();
    private String token = "";
    
	public int getChainOrderPay() {
		return chainOrderPay;
	}
	public void setChainOrderPay(int chaiOrderPay) {
		this.chainOrderPay = chaiOrderPay;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVipCardNo() {
		return vipCardNo;
	}
	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}
	public double getMaxVipCash() {
		return maxVipCash;
	}
	public void setMaxVipCash(double maxVipCash) {
		this.maxVipCash = maxVipCash;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public ChainStoreConf getChainStoreConf() {
		return chainStoreConf;
	}
	public void setChainStoreConf(ChainStoreConf chainStoreConf) {
		this.chainStoreConf = chainStoreConf;
	}
	public boolean isInitialSearch() {
		return initialSearch;
	}
	public void setInitialSearch(boolean initialSearch) {
		this.initialSearch = initialSearch;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Date getSearch_Start_Time() {
		return search_Start_Time;
	}
	public void setSearch_Start_Time(Date search_Start_Time) {
		this.search_Start_Time = search_Start_Time;
	}
	public Date getSearch_End_Time() {
		return search_End_Time;
	}
	public void setSearch_End_Time(Date search_End_Time) {
		this.search_End_Time = search_End_Time;
	}
	public ChainStoreSalesOrder getChainSalesOrder() {
		return chainSalesOrder;
	}
	public void setChainSalesOrder(ChainStoreSalesOrder chainSalesOrder) {
		this.chainSalesOrder = chainSalesOrder;
	}

	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void finalize(){
		chainSalesOrder = new ChainStoreSalesOrder();
		index = 0;
	}
	@Override
	public String toString() {

		return "ChainSalesActionFormBean [barcode=" + barcode
				+ ", productCode=" + productCode + ", chainId=" + chainId
				+ ", search_Start_Time=" + search_Start_Time
				+ ", search_End_Time=" + search_End_Time + ", pager=" + pager
				+ "]";
	}
	
	
}
